package com.autumn.zero.common.library.entities.personal;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.audited.annotation.LogMessage;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.mybatis.mapper.annotation.TableDocument;
import com.autumn.validation.annotation.MobilePhone;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 用户收货地址
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 16:11
 **/
@ToString(callSuper = true)
@Getter
@Setter
@Table(name = "user_receiving_address")
@TableDocument(value = "用户收货地址", group = "用户个人", groupOrder = Integer.MAX_VALUE - 500)
public class UserPersonalReceivingAddress extends AbstractUserPersonalDefaultEntity {

    private static final long serialVersionUID = 5680620823856320241L;

    /**
     * 字段 contactsName (联系人)
     */
    public static final String FIELD_CONTACTS_NAME = "contactsName";

    /**
     * 字段 mobilePhone (手机号)
     */
    public static final String FIELD_MOBILE_PHONE = "mobilePhone";

    /**
     * 字段 regionId (行政区id)
     */
    public static final String FIELD_REGION_ID = "regionId";

    /**
     * 字段 detailsAddress (详细地址)
     */
    public static final String FIELD_DETAILS_ADDRESS = "detailsAddress";

    /**
     * 字段 label (标签)
     */
    public static final String FIELD_LABEL = "label";

    /**
     * 字段 contactsName (联系人) 最大长度
     */
    public static final int MAX_LENGTH_CONTACTS_NAME = 50;

    /**
     * 字段 mobilePhone (手机号) 最大长度
     */
    public static final int MAX_LENGTH_MOBILE_PHONE = 20;

    /**
     * 字段 detailsAddress (详细地址) 最大长度
     */
    public static final int MAX_LENGTH_DETAILS_ADDRESS = 255;

    /**
     * 字段 label (标签) 最大长度
     */
    public static final int MAX_LENGTH_LABEL = 50;

    /**
     * 联系人
     */
    @NotNullOrBlank(message = "联系人不能为空。")
    @Length(max = MAX_LENGTH_CONTACTS_NAME, message = "联系人 不能超过 " + MAX_LENGTH_CONTACTS_NAME + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_CONTACTS_NAME)
    @ColumnOrder(1)
    @ColumnDocument("联系人")
    @FriendlyProperty(value = "联系人")
    @LogMessage(order = 1)
    private String contactsName;

    /**
     * 手机号
     */
    @NotNullOrBlank(message = "手机号不能为空。")
    @MobilePhone(message = "手机号 不是有效的手机号码。")
    @Length(max = MAX_LENGTH_MOBILE_PHONE, message = "手机号 不能超过 " + MAX_LENGTH_MOBILE_PHONE + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_MOBILE_PHONE)
    @ColumnOrder(2)
    @ColumnDocument("手机号")
    @FriendlyProperty(value = "手机号")
    @LogMessage(order = 2)
    private String mobilePhone;

    /**
     * 行政区id
     */
    @NotNull(message = "行政区id不能为空。")
    @Column(nullable = false)
    @ColumnOrder(3)
    @Index(unique = false)
    @ColumnDocument("行政区id")
    @FriendlyProperty(value = "行政区id")
    private Long regionId;

    /**
     * 详细地址
     */
    @NotNullOrBlank(message = "详细地址不能为空。")
    @Length(max = MAX_LENGTH_DETAILS_ADDRESS, message = "详细地址 不能超过 " + MAX_LENGTH_DETAILS_ADDRESS + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_DETAILS_ADDRESS)
    @ColumnOrder(4)
    @ColumnDocument("详细地址")
    @FriendlyProperty(value = "详细地址")
    @LogMessage(order = 4)
    private String detailsAddress;

    /**
     * 标签
     */
    @Length(max = MAX_LENGTH_LABEL, message = "标签 不能超过 " + MAX_LENGTH_LABEL + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_LABEL)
    @ColumnOrder(5)
    @ColumnDocument("标签")
    @FriendlyProperty(value = "标签")
    @LogMessage(order = 5)
    private String label;

}