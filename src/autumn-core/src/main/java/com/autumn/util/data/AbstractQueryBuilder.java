package com.autumn.util.data;

import com.autumn.application.dto.input.QueryCriteriaItem;
import com.autumn.mybatis.wrapper.QueryWrapper;
import com.autumn.mybatis.wrapper.commands.CriteriaBean;
import com.autumn.util.StringUtils;
import com.autumn.util.function.FunctionOneAction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 查询抽象
 * 
 * @author 老码农
 *
 *         2018-04-11 10:16:54
 */
public abstract class AbstractQueryBuilder<TQueryBuilder extends AbstractQueryBuilder<TQueryBuilder>> {

	/**
	 * 获取查询
	 * 
	 * @return
	 *
	 */
	public abstract QueryWrapper getQuery();

	/**
	 * 返回自身 this
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected TQueryBuilder returnThis() {
		return (TQueryBuilder) this;
	}

	/**
	 * 应用
	 * 
	 * @param applyAction
	 *            应用抽象
	 * @return
	 */
	public TQueryBuilder apply(FunctionOneAction<QueryWrapper> applyAction) {
		if (applyAction != null) {
			applyAction.apply(this.getQuery());
		}
		return this.returnThis();
	}

	/**
	 * 条件
	 * 
	 * @param criteriaBeans
	 *            条件 Bean 集合
	 * @return
	 */
	public <E extends CriteriaBean> TQueryBuilder criteria(List<E> criteriaBeans) {
		if (criteriaBeans != null) {
			this.getQuery().where().criteria(criteriaBeans);
		}
		return this.returnThis();
	}

	/**
	 * 条件
	 * 
	 * @param criteriaBeans
	 *            条件项目集合
	 * @return
	 */
	public TQueryBuilder criteriaByItem(List<QueryCriteriaItem> criteriaBeans) {
		if (criteriaBeans != null) {
			this.getQuery().where().criteria(criteriaBeans);
		}
		return this.returnThis();
	}

	/**
	 * 搜索成员集合
	 * 
	 * @param searchKeyword
	 *            搜索关键字
	 * @param members
	 *            成员集合
	 * @return
	 */
	public TQueryBuilder searchMembers(String searchKeyword, String... members) {
		if (!StringUtils.isNullOrBlank(searchKeyword) && members != null && members.length > 0) {
			Set<String> memberSet = new HashSet<>(Arrays.asList(members));
			return this.searchMembers(searchKeyword, memberSet);
		}
		return this.returnThis();
	}

	/**
	 * 搜索成员集合
	 * 
	 * @param searchKeyword
	 *            搜索关键字
	 * @param members
	 *            成员集合
	 * @return
	 */
	public TQueryBuilder searchMembers(String searchKeyword, Set<String> members) {
		if (!StringUtils.isNullOrBlank(searchKeyword) && members != null && members.size() > 0) {
			String keyword = searchKeyword.trim();
			int index = 0;
			for (String name : members) {
				if (index == 0) {
					this.getQuery().where().like(name, keyword);
				} else {
					this.getQuery().where().orLike(name, keyword);
				}
				index++;
			}
		}
		return this.returnThis();
	}

	/**
	 * 重置
	 * 
	 * @return
	 */
	public TQueryBuilder reset() {
		this.getQuery().reset();
		return this.returnThis();
	}

}
