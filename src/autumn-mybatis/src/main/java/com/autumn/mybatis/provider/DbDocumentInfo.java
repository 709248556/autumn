package com.autumn.mybatis.provider;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 数据库文档信息
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-01-04 15:10
 **/
@ToString
@Getter
@Setter
public class DbDocumentInfo implements Serializable {

    private static final long serialVersionUID = -7972657406089001727L;

    /**
     * 方案名称
     */
    private String projectName;

    /**
     * 缓定实体类型
     */
    private boolean bindEntityClass;

    /**
     * 创建日期
     */
    private LocalDate createdDate;

    /**
     * 创建人
     */
    private String createdUserName;

    /**
     * 更新日期
     */
    private LocalDate updatedDate;

    /**
     * 更新人
     */
    private String updatedUserName;

    /**
     * 版本
     */
    private String version;

}
