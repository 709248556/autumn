package com.autumn.mybatis.wrapper.commands;

import java.util.List;

import com.autumn.mybatis.wrapper.clauses.UpdateClause;
import com.autumn.mybatis.wrapper.commands.CriteriaSection;

/**
 * 更新段
 * 
 * @author 老码农
 *
 *         2017-10-30 18:38:41
 */
public interface UpdateSection extends CriteriaSection {

	/**
	 * 获取更新集合
	 * 
	 * @return
	 */
	List<UpdateClause> getUpdateClauses();
}
