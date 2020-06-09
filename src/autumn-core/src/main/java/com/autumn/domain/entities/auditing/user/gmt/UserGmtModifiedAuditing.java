package com.autumn.domain.entities.auditing.user.gmt;

import com.autumn.domain.entities.auditing.gmt.GmtModifiedAuditing;
import com.autumn.domain.entities.auditing.user.UserModifiedAuditing;

/**
 * 用户修改审计
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 20:36
 **/
public interface UserGmtModifiedAuditing extends UserModifiedAuditing, UserGmtCreateAuditing, GmtModifiedAuditing {

}
