package com.autumn.zero.common.library.entities.personal.query;

import com.autumn.mybatis.mapper.annotation.ViewTable;
import com.autumn.zero.common.library.entities.personal.UserPersonalReceivingAddress;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户收货地址查询
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 17:15
 **/
@ToString(callSuper = true)
@Getter
@Setter
@ViewTable
public class UserPersonalReceivingAddressQuery extends UserPersonalReceivingAddress {

    private static final long serialVersionUID = 2002389441197375409L;

    /**
     * 字段 regionCode (行政区代码)
     */
    public static final String FIELD_REGION_CODE = "regionCode";

    /**
     * 字段 regionName (行政区名称)
     */
    public static final String FIELD_REGION_NAME = "regionName";

    /**
     * 字段 regionFullId (完整行政区id)
     */
    public static final String FIELD_REGION_FULL_ID = "regionFullId";

    /**
     * 字段 regionFullCode (完整行政区代码)
     */
    public static final String FIELD_REGION_FULL_CODE = "regionFullCode";

    /**
     * 字段 regionFullName (完整行政区名称)
     */
    public static final String FIELD_REGION_FULL_NAME = "regionFullName";

    /**
     * 行政区代码
     */
    private String regionCode;

    /**
     * 行政区名称
     */
    private String regionName;

    /**
     * 完整行政区id
     */
    private String regionFullId;

    /**
     * 完整行政区代码
     */
    private String regionFullCode;

    /**
     * 完整行政区名称
     */
    private String regionFullName;

}