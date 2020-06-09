package com.autumn.word;

import com.autumn.evaluator.*;
import com.autumn.evaluator.annotation.FunctionRegister;
import com.autumn.evaluator.annotation.ParamRegister;
import com.autumn.evaluator.functions.AbstractFunctionBase;
import com.autumn.util.StringUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.util.Map;

/**
 * Word 函数
 * 
 * @author 老码农 2019-04-21 02:00:43
 */
public class WordFunction {

	/**
	 * 根据条件获取数组成员值
	 */
	@FunctionRegister(name = "ArrayMemberIfValue", category = "数组", caption = "根据条件获取数组成员值 。", minParamCount = 4)
	@ParamRegister(order = 1, name = "arrayName", caption = "数组名称。", paramType = VariantType.STRING)
	@ParamRegister(order = 2, name = "conditionMemberName", caption = "条件成员名称。", paramType = VariantType.STRING)
	@ParamRegister(order = 3, name = "conditionValue", caption = "条件值。", paramType = VariantType.STRING
			| VariantType.NUMBER)
	@ParamRegister(order = 4, name = "targetMemberName", caption = "目标成员名称。", paramType = VariantType.STRING)
	public static class ArrayMemberIfValue extends AbstractFunctionBase implements Callable {
		@Override
		public Variant call(String name, FunctionParamContext paramContext, Context context) {
			Variant arrayName = paramContext.getParam(1).getValue();
			Variant conditionMemberName = paramContext.getParam(2).getValue();
			Variant conditionValue = paramContext.getParam(3).getValue();
			Variant targetMemberName = paramContext.getParam(4).getValue();

			Variant array = context.getVariable(arrayName.toString());
			if (array == null || array.isNull()) {
				return Variant.DEFAULT;
			}
			if (!array.isArray()) {
				throw new WordEvaluateException(
						String.format("函数名称[%s]的数组名称[%s]对应的值不是数组类型。", name, arrayName.toString()));
			}
			Variant[] arrays = (Variant[]) array.getValue();
			if (arrays.length == 0) {
				return Variant.DEFAULT;
			}
			Map<String, Variant[]> arrayMap = getVariantMap(arrays);
			Variant[] conditions = arrayMap.get(conditionMemberName.toString());
			int index = getConditionIndex(conditions, conditionValue);
			if (index < 0) {
				return Variant.DEFAULT;
			}
			if (conditionMemberName.equals(targetMemberName)) {
				return conditions[index];
			}
			Variant[] values = arrayMap.get(targetMemberName.toString());
			if (values == null || values.length != conditions.length) {
				return Variant.DEFAULT;
			}
			return values[index];			
		}
	}

	private static int getConditionIndex(Variant[] conditions, Variant conditionValue) {
		for (int i = 0; i < conditions.length; i++) {
			Variant variant = conditions[i];
			if (conditionValue.equals(variant)) {
				return i;
			}
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Variant[]> getVariantMap(Variant[] arrays) {
		Map<String, Variant[]> map = new CaseInsensitiveMap();
		for (Variant array : arrays) {
			if (array.isArray() && !StringUtils.isNullOrBlank(array.getName())) {
				map.put(array.getName(), (Variant[]) array.getValue());
			}
		}
		return map;
	}
}
