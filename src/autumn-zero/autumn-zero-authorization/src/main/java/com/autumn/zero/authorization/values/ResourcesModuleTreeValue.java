package com.autumn.zero.authorization.values;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源模块树值类型
 * 
 * @author 老码农 2018-12-08 22:52:52
 */
public class ResourcesModuleTreeValue extends ResourcesModuleValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7467004944149598373L;

	/**
	 * 路径
	 */
	@ApiModelProperty(value = "路径")
	@JSONField(ordinal = 99)
	private List<String> path;

	@ApiModelProperty(value = "子级集合")
	@JSONField(ordinal = 100)
	private List<ResourcesModuleTreeValue> children;

	/**
	 * 
	 */
	public ResourcesModuleTreeValue() {
		// 不可初始化子级，影响前端处理
	}

	/**
	 * 获取子级集合
	 * 
	 * @return
	 */
	public List<ResourcesModuleTreeValue> getChildren() {
		return children;
	}

	/**
	 * 设置子级集合
	 * 
	 * @param children 子级集合
	 */
	public void setChildren(List<ResourcesModuleTreeValue> children) {
		this.children = children;
	}

	/**
	 * 获取路径
	 * 
	 * @return
	 */
	public List<String> getPath() {
		return path;
	}

	/**
	 * 设置路径
	 * 
	 * @param path 路径
	 */
	public void setPath(List<String> path) {
		this.path = path;
	}

	/**
	 * 子级空值初始化
	 */
	public void childrenNullValueInit() {
		if (this.getChildren() == null) {
			this.setChildren(new ArrayList<>());
		}
	}
}
