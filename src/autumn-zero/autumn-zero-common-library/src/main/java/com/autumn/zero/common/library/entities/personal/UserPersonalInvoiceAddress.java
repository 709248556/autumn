package com.autumn.zero.common.library.entities.personal;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.audited.annotation.LogMessage;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.mybatis.mapper.annotation.TableDocument;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 用户发票地址
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 16:52
 **/
@ToString(callSuper = true)
@Getter
@Setter
@Table(name = "user_invoice_address")
@TableDocument(value = "用户发票地址", group = "用户个人", groupOrder = Integer.MAX_VALUE - 500)
public class UserPersonalInvoiceAddress extends AbstractUserPersonalDefaultEntity {

    private static final long serialVersionUID = 981823745432624883L;

    /**
     *  字段 name (开票名称(抬头))
     */
    public static final String FIELD_NAME = "name";

    /**
     *  字段 taxNumber (发票税号)
     */
    public static final String FIELD_TAX_NUMBER = "taxNumber";

    /**
     *  字段 bankName (开户银行)
     */
    public static final String FIELD_BANK_NAME = "bankName";

    /**
     *  字段 bankAccount (银行账号)
     */
    public static final String FIELD_BANK_ACCOUNT = "bankAccount";

    /**
     *  字段 address (单位/企业地址)
     */
    public static final String FIELD_ADDRESS = "address";

    /**
     *  字段 telephone (单位/企业电话)
     */
    public static final String FIELD_CONTACTS_TELEPHONE = "telephone";

    /**
     *  字段 name (开票名称(抬头)) 最大长度
     */
    public static final int MAX_LENGTH_NAME = 100;

    /**
     *  字段 taxNumber (发票税号) 最大长度
     */
    public static final int MAX_LENGTH_TAX_NUMBER = 50;

    /**
     *  字段 bankName (开户银行) 最大长度
     */
    public static final int MAX_LENGTH_BANK_NAME = 50;

    /**
     *  字段 bankAccount (银行账号) 最大长度
     */
    public static final int MAX_LENGTH_BANK_ACCOUNT = 50;

    /**
     *  字段 address (单位/企业地址) 最大长度
     */
    public static final int MAX_LENGTH_ADDRESS = 255;

    /**
     *  字段 telephone (单位/企业电话) 最大长度
     */
    public static final int MAX_LENGTH_TELEPHONE = 50;

    /**
     * 开票名称(抬头)
     */
    @NotNullOrBlank(message = "开票名称(抬头)不能为空。")
    @Length(max = MAX_LENGTH_NAME, message = "开票名称(抬头) 不能超过 " + MAX_LENGTH_NAME + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_NAME)
    @ColumnOrder(1)
    @Index
    @ColumnDocument("开票名称(抬头)")
    @FriendlyProperty(value = "开票名称(抬头)")
    @LogMessage(order = 1)
    private String name;

    /**
     * 发票税号
     */
    @NotNullOrBlank(message = "发票税号不能为空。")
    @Length(max = MAX_LENGTH_TAX_NUMBER, message = "发票税号 不能超过 " + MAX_LENGTH_TAX_NUMBER + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_TAX_NUMBER)
    @ColumnOrder(2)
    @ColumnDocument("发票税号")
    @FriendlyProperty(value = "发票税号")
    @LogMessage(order = 2)
    private String taxNumber;

    /**
     * 开户银行
     */
    @Length(max = MAX_LENGTH_BANK_NAME, message = "开户银行 不能超过 " + MAX_LENGTH_BANK_NAME + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_BANK_NAME)
    @ColumnOrder(3)
    @ColumnDocument("开户银行")
    @FriendlyProperty(value = "开户银行")
    @LogMessage(order = 3)
    private String bankName;

    /**
     * 银行账号
     */
    @Length(max = MAX_LENGTH_BANK_ACCOUNT, message = "银行账号 不能超过 " + MAX_LENGTH_BANK_ACCOUNT + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_BANK_ACCOUNT)
    @ColumnOrder(4)
    @ColumnDocument("银行账号")
    @FriendlyProperty(value = "银行账号")
    @LogMessage(order = 4)
    private String bankAccount;

    /**
     * 企业/单位地址
     */
    @Length(max = MAX_LENGTH_ADDRESS, message = "企业/单位地址 不能超过 " + MAX_LENGTH_ADDRESS + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_ADDRESS)
    @ColumnOrder(5)
    @ColumnDocument("企业/单位地址")
    @FriendlyProperty(value = "企业/单位地址")
    @LogMessage(order = 5)
    private String address;

    /**
     * 企业/单位电话
     */
    @Length(max = MAX_LENGTH_TELEPHONE, message = "企业/单位电话 不能超过 " + MAX_LENGTH_TELEPHONE + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_TELEPHONE)
    @ColumnOrder(6)
    @ColumnDocument("企业/单位电话")
    @FriendlyProperty(value = "企业/单位电话")
    @LogMessage(order = 6)
    private String telephone;

}