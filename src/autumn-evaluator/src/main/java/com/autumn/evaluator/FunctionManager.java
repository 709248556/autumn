package com.autumn.evaluator;

import com.autumn.evaluator.annotation.FunctionRegister;
import com.autumn.evaluator.functions.*;
import com.autumn.exception.ArgumentNullException;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.PackageUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.util.*;

//
// * 提供函数注册，获取函数、反注册函数等操作
//

/**
 * 函数管理
 */
@SuppressWarnings("unchecked")
public final class FunctionManager {
    private static final Map<String, Function> FUNCTION_MAP = new CaseInsensitiveMap();
    private static final Set<String> SYSTEM_FUNCTION_SET = new HashSet();

    static {
        List<Function> funs = FunctionManager.searchFunctions(SystemFunction.class);
        for (Function fun : funs) {
            SYSTEM_FUNCTION_SET.add(fun.getName().toUpperCase());
            FunctionManager.register(fun, true);
        }
        FunctionManager.register(ArithFunction.class);
        FunctionManager.register(ArrayFunction.class);
        FunctionManager.register(DateTimeFunction.class);
        FunctionManager.register(FinancialFunction.class);
        FunctionManager.register(StatisticsFunction.class);
        FunctionManager.register(TextFunction.class);
    }

    /**
     * 注册函数
     *
     * @param call 回调
     */
    public static void register(Callable call) {
        ExceptionUtils.checkNotNull(call, "call");
        register(new Function(call));
    }

    /**
     * 注册函数(如果注册已存在的函则会覆盖,除系统函数外)
     *
     * @param function
     */
    public static void register(Function function) {
        FunctionManager.register(function, false);
    }

    /**
     * 注册函数(如果注册已存在的函则会覆盖)
     *
     * @param function 函数
     * @param isSystem
     */
    public static void register(Function function, boolean isSystem) {
        if (function == null) {
            throw new ArgumentNullException("function");
        }
        if (!isSystem) {
            if (SYSTEM_FUNCTION_SET.contains(function.getName().toUpperCase())) {
                throw new IllegalArgumentException(String.format("函数 %s 与系统内置函数相同，禁止注册。", function.getName()));
            }
        }
        synchronized (FunctionManager.FUNCTION_MAP) {
            FUNCTION_MAP.put(function.getName(), function);
        }
    }

    /**
     * 注册函数(如果注册已存在的函则会覆盖,除系统函数外)
     *
     * @param functions
     */
    public static void register(Iterable<Function> functions) {
        if (functions == null) {
            throw new ArgumentNullException("functions");
        }
        for (Function fun : functions) {
            FunctionManager.register(fun);
        }
    }

    /**
     * 注册函数(如果注册已存在的函则会覆盖,除系统函数外)
     *
     * @param functionClassType
     */
    public static void register(Class<?> functionClassType) {
        FunctionManager.register(FunctionManager.searchFunctions(functionClassType));
    }

    /**
     * 注册包(如果注册已存在的函则会覆盖)
     *
     * @param packagePath 包路径
     */
    public static void registerPackage(String packagePath) {
        ExceptionUtils.checkNotNullOrBlank(packagePath, "packagePath");
        try {
            Set<Class<?>> classSet = PackageUtils.getPackageAnnotationClass(packagePath, FunctionRegister.class, false, false, false);
            List<Function> funList = new ArrayList<>();
            for (Class<?> funClass : classSet) {
                if (Callable.class.isAssignableFrom(funClass)) {
                    funList.add(createFunction(funClass));
                }
            }
            FunctionManager.register(funList);
        } catch (Exception e) {
            throw ExceptionUtils.throwSystemException("无法读取类型包[" + packagePath + "]:" + e.getMessage(), e);
        }
    }

    /**
     * 根据特定的类型,搜索函数集合
     *
     * @param functionClassType 函数类类型
     */
    public static List<Function> searchFunctions(Class<?> functionClassType) {
        if (functionClassType == null) {
            throw new ArgumentNullException("functionClassType");
        }
        List<Function> funList = new ArrayList<>();
        if (Callable.class.isAssignableFrom(functionClassType)) {
            funList.add(createFunction(functionClassType));
        } else {
            Set<Class<?>> members = Utils.getPackageAnnotationClass(functionClassType, FunctionRegister.class);
            for (Class<?> funClass : members) {
                if (Callable.class.isAssignableFrom(funClass)) {
                    funList.add(createFunction(funClass));
                }
            }
        }
        return funList;
    }

    /**
     * 创建函数
     *
     * @param funClassType 函数类型
     * @return
     */
    private static Function createFunction(Class<?> funClassType) {
        try {
            Callable call = (Callable) funClassType.newInstance();
            return new Function(call);
        } catch (InstantiationException | IllegalAccessException e) {
            throw ExceptionUtils.throwSystemException("类型 " + funClassType.getName() + " 无默认构造函数。", e);
        }
    }

    /**
     * 反注册所有函数,除系统函数外
     */
    public static void notRegistrationAll() {
        synchronized (FunctionManager.FUNCTION_MAP) {
            if (FUNCTION_MAP.size() > 0) {
                String[] keys = FUNCTION_MAP.keySet().toArray(new String[0]);
                for (String key : keys) {
                    if (!SYSTEM_FUNCTION_SET.contains(key.toUpperCase())) {
                        FUNCTION_MAP.remove(key);
                    }
                }
            }
        }
    }

    /**
     * 反注册函数,除系统函数外
     *
     * @param name
     */
    public static boolean notRegistration(String name) {
        Utils.checkNotNullOrEmpty("name", name);
        if (!SYSTEM_FUNCTION_SET.contains(name.toUpperCase())) {
            synchronized (FunctionManager.FUNCTION_MAP) {
                boolean isSuccess = true;
                try {
                    FUNCTION_MAP.remove(name);
                } catch (Exception e) {
                    isSuccess = false;
                }
                return isSuccess;
            }
        }
        return false;
    }

    /**
     * 获取函数名是否存在
     *
     * @param name 函数名称
     */
    public static boolean existFunction(String name) {
        Utils.checkNotNullOrEmpty("name", name);
        synchronized (FunctionManager.FUNCTION_MAP) {
            return FUNCTION_MAP.containsKey(name);
        }
    }

    /**
     * 获取函数
     *
     * @param name 名称
     */
    public static Function getFunction(String name) {
        return FUNCTION_MAP.get(name);
    }

    /**
     * 获取函数集合
     *
     * @return
     */
    public static ArrayList<Function> getFunctions() {
        synchronized (FunctionManager.FUNCTION_MAP) {
            ArrayList<Function> funs = new ArrayList<>();
            funs.addAll(FUNCTION_MAP.values());
            return funs;
        }
    }

    /**
     * 获取函数集合
     *
     * @param category 类别
     * @return
     * @throws ArgumentNullException
     */
    public static ArrayList<Function> getFunctions(String category) {
        if (category == null) {
            throw new ArgumentNullException("Category");
        }
        synchronized (FunctionManager.FUNCTION_MAP) {
            ArrayList<Function> funs = new ArrayList<>();
            for (Function fun : FUNCTION_MAP.values()) {
                if (fun.getCategory().equals(category)) {
                    funs.add(fun);
                }
            }
            return funs;
        }
    }

    /**
     * 获取函数类别集合
     *
     * @return
     */
    public static ArrayList<String> getCategorys() {
        synchronized (FunctionManager.FUNCTION_MAP) {
            ArrayList<String> ls = new ArrayList<>();
            for (Function fun : FUNCTION_MAP.values()) {
                if (!ls.contains(fun.getCategory())) {
                    ls.add(fun.getCategory());
                }
            }
            return ls;
        }
    }

    /**
     * 搜索函数
     *
     * @param likeCaption 包含说明
     */
    public static ArrayList<Function> findFunctions(String likeCaption) {
        if (likeCaption == null) {
            throw new ArgumentNullException("LikeCaption");
        }
        synchronized (FunctionManager.FUNCTION_MAP) {
            ArrayList<Function> funs = new ArrayList<>();
            for (Function fun : FUNCTION_MAP.values()) {
                if (fun.getCaption().toUpperCase().contains(likeCaption.toUpperCase())
                        || fun.getName().toUpperCase().contains(likeCaption.toUpperCase())) {
                    funs.add(fun);
                }
            }
            return funs;
        }
    }
}