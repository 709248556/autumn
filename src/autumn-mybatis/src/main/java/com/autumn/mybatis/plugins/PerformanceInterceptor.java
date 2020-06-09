package com.autumn.mybatis.plugins;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.*;

/**
 * 性能监视
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-07 00:47
 **/
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "query",
        args = {Statement.class, ResultHandler.class}
), @Signature(
        type = StatementHandler.class,
        method = "update",
        args = {Statement.class}
), @Signature(
        type = StatementHandler.class,
        method = "batch",
        args = {Statement.class}
)})
public class PerformanceInterceptor extends AbstractInterceptor {

    private static final Log logger = LogFactory.getLog(PerformanceInterceptor.class);

    private long maxExecuteTime = 0L;
    private boolean writeInLog = false;
    private boolean consolePrint = true;

    /**
     * 获取最大执行时间(毫秒)，超过则写入警告
     *
     * @return
     */
    public long getMaxExecuteTime() {
        return maxExecuteTime;
    }

    /**
     * 最大执行时间(毫秒)，超过则写入警告
     *
     * @param maxExecuteTime <=0表示不执行
     */
    public void setMaxExecuteTime(long maxExecuteTime) {
        this.maxExecuteTime = maxExecuteTime;
    }

    /**
     * 获取是否写入日志
     *
     * @return
     */
    public boolean isWriteInLog() {
        return writeInLog;
    }

    /**
     * 设置是否写入日志
     *
     * @param writeInLog
     */
    public void setWriteInLog(boolean writeInLog) {
        this.writeInLog = writeInLog;
    }

    /**
     * 获取是否打印到控制台
     *
     * @return
     */
    public boolean isConsolePrint() {
        return this.consolePrint;
    }

    /**
     * 设置是否打印到控制台
     *
     * @param consolePrint
     */
    public void setConsolePrint(boolean consolePrint) {
        this.consolePrint = consolePrint;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        boolean isIntercept = this.isWriteInLog() || this.isConsolePrint();
        if (!isIntercept) {
            return invocation.proceed();
        }
        Object firstArg = invocation.getArgs()[0];
        Statement statement;
        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement) firstArg;
        }
        MetaObject stmtMetaObj = SystemMetaObject.forObject(statement);
        try {
            statement = (Statement) stmtMetaObj.getValue("stmt.statement");
        } catch (Exception var20) {
        }
        if (stmtMetaObj.hasGetter("delegate")) {
            try {
                statement = (Statement) stmtMetaObj.getValue("delegate");
            } catch (Exception var19) {
            }
        }
        String originalSql = statement.toString();
        int index = this.indexOfSqlStart(originalSql);
        if (index > 0) {
            originalSql = originalSql.substring(index);
        }
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        long timing = System.currentTimeMillis() - start;
        boolean isWarn = this.getMaxExecuteTime() > 0 && timing > this.getMaxExecuteTime();
        Object target = this.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(target);
        MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        StringBuilder formatSql = (new StringBuilder())
                .append("==> Execute Time : ")
                .append(timing)
                .append(" ms; Thread Id : ")
                .append(Thread.currentThread().getId())
                .append("; Mapper-Id : ")
                .append(ms.getId())
                .append("\n")
                .append("==> Execute SQL : ")
                .append(originalSql)
                .append("\n");
        if (this.isWriteInLog()) {
            if (isWarn) {
                logger.warn(formatSql.toString());
            } else {
                logger.debug(formatSql.toString());
            }
        }
        if (this.isConsolePrint()) {
            if (isWarn) {
                System.err.println(formatSql.toString());
            } else {
                System.out.println(formatSql.toString());
            }
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof StatementHandler ? Plugin.wrap(target, this) : target;
    }

    private int indexOfSqlStart(String sql) {
        String upperCaseSql = sql.toUpperCase();
        Set<Integer> set = new HashSet(5);
        set.add(upperCaseSql.indexOf("SELECT "));
        set.add(upperCaseSql.indexOf("UPDATE "));
        set.add(upperCaseSql.indexOf("INSERT "));
        set.add(upperCaseSql.indexOf("DELETE "));
        set.remove(-1);
        if (CollectionUtils.isEmpty(set)) {
            return -1;
        } else {
            List<Integer> list = new ArrayList(set);
            list.sort(Comparator.naturalOrder());
            return list.get(0);
        }
    }
}
