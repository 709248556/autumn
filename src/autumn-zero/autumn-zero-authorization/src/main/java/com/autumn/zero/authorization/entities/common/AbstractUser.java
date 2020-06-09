
package com.autumn.zero.authorization.entities.common;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.domain.entities.auditing.gmt.AbstractDefaultGmtModifiedAuditingEntity;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.ColumnType;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.security.constants.UserStatusConstants;
import com.autumn.util.DateUtils;
import com.autumn.validation.annotation.EmailAddress;
import com.autumn.validation.annotation.MobilePhone;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * 抽象用户信息
 *
 * @author 老码农 2018-11-24 21:05:03 主键类型
 */
@ToString(callSuper = true)
public abstract class AbstractUser extends AbstractDefaultGmtModifiedAuditingEntity {

    /**
     *
     */
    private static final long serialVersionUID = -2320468409673914227L;

    /**
     * 最大用户名称长度
     */
    public static final int MAX_USER_NAME_LENGTH = 50;

    /**
     * 最大真实名称长度
     */
    public static final int MAX_REAL_NAMEE_LENGTH = 50;

    /**
     * 最大用户昵称长度
     */
    public static final int MAX_NICK_NAME_LENGTH = 50;

    /**
     * 最大电话号码长度
     */
    public static final int MAX_PHONE_NUMBER_LENGTH = 20;

    /**
     * 最大邮件地址长度
     */
    public static final int MAX_EMAIL_ADDRESS_LENGTH = 50;

    /**
     * 最大性别长度
     */
    public static final int MAX_SEX_LENGTH = 10;

    /**
     * 最大头像路径长度
     */
    public static final int MAX_HEAD_PORTRAIT_PATH_LENGTH = 255;

    /**
     * 最大密码长度
     */
    public static final int MAX_PASSWORD_LENGTH = 255;

    /**
     * 字段 userName
     */
    public static final String FIELD_USER_NAME = "userName";

    /**
     * 字段 realName
     */
    public static final String FIELD_REAL_NAME = "realName";

    /**
     * 字段 nickName
     */
    public static final String FIELD_NICK_NAME = "nickName";

    /**
     * 字段 phoneNumber
     */
    public static final String FIELD_PHONE_NUMBER = "phoneNumber";

    /**
     * 字段 isActivatePhone
     */
    public static final String FIELD_IS_ACTIVATE_PHONE = "isActivatePhone";

    /**
     * 字段 emailAddress
     */
    public static final String FIELD_EMAIL_ADDRESS = "emailAddress";

    /**
     * 字段 isActivateEmail
     */
    public static final String FIELD_IS_ACTIVATE_EMAIL = "isActivateEmail";

    /**
     * 字段 sex
     */
    public static final String FIELD_SEX = "sex";

    /**
     * 字段 headPortraitPath
     */
    public static final String FIELD_HEAD_PORTRAIT_PATH = "headPortraitPath";

    /**
     * 字段 birthday
     */
    public static final String FIELD_BIRTHDAY = "birthday";

    /**
     * 字段 isSysUser
     */
    public static final String FIELD_IS_SYS_USER = "isSysUser";

    /**
     * 字段 status
     */
    public static final String FIELD_STATUS = "status";

    /**
     * 字段 password
     */
    public static final String FIELD_PASSWORD = "password";

    /**
     * 用户会话声明前缀
     */
    private static final String USER_SESSION_CLAIM_PREFIX = "user_session_info_claim_";

    /**
     * 用户会话声明(userName)
     */
    public static final String USER_SESSION_CLAIM_USER_NAME = USER_SESSION_CLAIM_PREFIX + "user_name";

    /**
     * 用户会话声明(realName)
     */
    public static final String USER_SESSION_CLAIM_REAL_NAME = USER_SESSION_CLAIM_PREFIX + "real_name";

    /**
     * 用户会话声明(realName)
     */
    public static final String USER_SESSION_CLAIM_NICK_NAME = USER_SESSION_CLAIM_PREFIX + "nick_name";

    /**
     * 用户会话声明(phoneNumber)
     */
    public static final String USER_SESSION_CLAIM_PHONE_NUMBER = USER_SESSION_CLAIM_PREFIX + "phone_number";

    /**
     * 用户会话声明(emailAddress)
     */
    public static final String USER_SESSION_CLAIM_EMAIL_ADDRESS = USER_SESSION_CLAIM_PREFIX + "email_address";

    /**
     * 用户会话声明(sex)
     */
    public static final String USER_SESSION_CLAIM_SEX = USER_SESSION_CLAIM_PREFIX + "sex";

    /**
     * 用户会话声明(headPortraitPath)
     */
    public static final String USER_SESSION_CLAIM_HEAD_PORTRAIT_PATH  = USER_SESSION_CLAIM_PREFIX + "head_portrait_path";

    /**
     * 用户会话声明(birthday)
     */
    public static final String USER_SESSION_CLAIM_BIRTHDAY  = USER_SESSION_CLAIM_PREFIX + "birthday";

    /**
     * 用户名称
     */
    @NotNullOrBlank(message = "用户名称不能为空")
    @Length(max = MAX_USER_NAME_LENGTH, message = "用户名称长度不能超过" + MAX_USER_NAME_LENGTH + "个字。")
    @Column(name = "user_name", nullable = false, length = MAX_USER_NAME_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @Index(unique = true)
    @ColumnOrder(1)
    @LogMessage(name = "用户名称", order = 1)
    @ColumnDocument("用户名称")
    private String userName;

    /**
     * 真实名称
     */
    @Length(max = MAX_REAL_NAMEE_LENGTH, message = "真实名称长度不能超过" + MAX_REAL_NAMEE_LENGTH + "个字。")
    @Column(name = "real_name", nullable = false, length = MAX_REAL_NAMEE_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @Index
    @ColumnOrder(2)
    @LogMessage(name = "真实名称", order = 2)
    @ColumnDocument("真实名称")
    private String realName;

    /**
     * 用户昵称
     */
    @Length(max = MAX_NICK_NAME_LENGTH, message = "用户昵称长度不能超过" + MAX_NICK_NAME_LENGTH + "个字。")
    @Column(name = "nick_name", nullable = false, length = MAX_NICK_NAME_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(3)
    @LogMessage(name = "用户昵称", order = 3)
    @ColumnDocument("用户昵称")
    private String nickName;

    /**
     * 电话号码
     */
    @Length(max = MAX_PHONE_NUMBER_LENGTH, message = "手机电话不能超过" + MAX_PHONE_NUMBER_LENGTH + "个字。")
    @MobilePhone
    @Column(name = "phone_number", nullable = false, length = MAX_PHONE_NUMBER_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @Index
    @ColumnOrder(4)
    @LogMessage(name = "电话号码", order = 4)
    @ColumnDocument("电话号码")
    private String phoneNumber;

    /**
     * 是否激活电话
     */
    @Column(name = "is_activate_phone", nullable = false)
    @ColumnOrder(5)
    @ColumnDocument("是否已激活电话")
    private boolean isActivatePhone;

    /**
     * 邮件地址
     */
    @Length(max = MAX_EMAIL_ADDRESS_LENGTH, message = "邮件地址不能超过" + MAX_EMAIL_ADDRESS_LENGTH + "个字。")
    @EmailAddress
    @Column(name = "email_address", nullable = false, length = MAX_EMAIL_ADDRESS_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @Index
    @ColumnOrder(6)
    @LogMessage(name = "邮件地址", order = 5)
    @ColumnDocument("邮件地址")
    private String emailAddress;

    /**
     * 是否激活邮件
     */
    @ColumnOrder(7)
    @Column(name = "is_activate_email", nullable = false)
    @ColumnDocument("是否已激活邮件")
    private boolean isActivateEmail;

    /**
     * 性别
     */
    @Length(max = MAX_SEX_LENGTH, message = "性别不能超过" + MAX_SEX_LENGTH + "个字。")
    @Column(name = "sex", nullable = false, length = MAX_SEX_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(8)
    @LogMessage(name = "性别", order = 6)
    @ColumnDocument("性别")
    private String sex;

    /**
     * 头像路径
     */
    @Length(max = MAX_HEAD_PORTRAIT_PATH_LENGTH, message = "头像路径不能超过" + MAX_HEAD_PORTRAIT_PATH_LENGTH + "个字。")
    @Column(name = "head_portrait_path", nullable = false, length = MAX_HEAD_PORTRAIT_PATH_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(9)
    @ColumnDocument("头像路径")
    private String headPortraitPath;

    /**
     * 出生日期
     */
    @Column(name = "birthday")
    @ColumnType(jdbcType = JdbcType.DATE)
    @Index
    @ColumnOrder(10)
    @LogMessage(name = "出生日期", order = 7)
    @ColumnDocument("出生日期")
    private Date birthday;

    /**
     * 是否系统用户
     */
    @ColumnOrder(200)
    @Column(name = "is_sys_user", nullable = false)
    @ColumnType(jdbcType = JdbcType.BOOLEAN)
    @LogMessage(name = "系统用户", order = 8)
    @ColumnDocument("系统用户")
    private boolean isSysUser;

    /**
     * 状态 {@link com.autumn.security.constants.UserStatusConstants}
     */
    @NotNull(message = "状态不能为空。")
    @Column(name = "status", nullable = false)
    @ColumnType(jdbcType = JdbcType.INTEGER)
    @ColumnOrder(201)
    @ColumnDocument("状态")
    private Integer status;

    /**
     * 密码
     */
    @Length(max = MAX_PASSWORD_LENGTH, message = "密码不能超过" + MAX_PASSWORD_LENGTH + "个字。")
    @Column(name = "password", nullable = false, length = MAX_PASSWORD_LENGTH, updatable = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(202)
    @ColumnDocument("密码")
    private String password;

    /**
     * 用户集合
     */
    private List<UserRole> roles;

    /**
     * 实例化 AbstractUser 新实例
     */
    public AbstractUser() {
        this.setRoles(new ArrayList<>());
    }

    /**
     * 获取用户名称
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名称
     *
     * @param userName 用户名称
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取真实名称
     *
     * @return
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实名称
     *
     * @param realName 真实名称
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取昵称
     *
     * @return
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 用户昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取手机号码
     *
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置手机号码
     *
     * @param phoneNumber 手机号码
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 获取是否激活电话号码
     *
     * @return
     */
    public boolean getIsActivatePhone() {
        return isActivatePhone;
    }

    /**
     * 设置是否激活电话号码
     *
     * @param isActivatePhone 激活电话号码
     */
    public void setIsActivatePhone(boolean isActivatePhone) {
        this.isActivatePhone = isActivatePhone;
    }

    /**
     * 获取邮件地址
     *
     * @return
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * 设置邮件地址
     *
     * @param emailAddress 邮件地址
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * 获取是否激活邮件
     *
     * @return
     */
    public boolean getIsActivateEmail() {
        return isActivateEmail;
    }

    /**
     * 设置是否激活邮件
     *
     * @param isActivateEmail 激活邮件
     */
    public void setIsActivateEmail(boolean isActivateEmail) {
        this.isActivateEmail = isActivateEmail;
    }

    /**
     * 获取性别
     *
     * @return
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取头像路径
     *
     * @return
     */
    public String getHeadPortraitPath() {
        return headPortraitPath;
    }

    /**
     * 设置头像路径
     *
     * @param headPortraitPath
     */
    public void setHeadPortraitPath(String headPortraitPath) {
        this.headPortraitPath = headPortraitPath;
    }

    /**
     * 获取出生日期
     *
     * @return
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置出生日期
     *
     * @param birthday 出生日期
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取是否是系统
     *
     * @return
     */
    public boolean getIsSysUser() {
        return isSysUser;
    }

    /**
     * 设置是否是系统
     *
     * @param isSysUser 是否是系统
     */
    public void setIsSysUser(boolean isSysUser) {
        this.isSysUser = isSysUser;
    }

    /**
     * 获取状态
     *
     * @return {@link com.autumn.security.constants.UserStatusConstants}
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态 {@link com.autumn.security.constants.UserStatusConstants}
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取密码
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    @Override
    public void valid() {
        super.valid();
        if (!UserStatusConstants.exist(this.getStatus())) {
            ExceptionUtils.throwValidationException("无效的用户状态。");
        }
        if (this.getRoles() == null) {
            this.setRoles(new ArrayList<>());
        }
        Set<Long> roleSet = new HashSet<>();
        for (UserRole role : this.getRoles()) {
            role.valid();
            if (!roleSet.add(role.getRoleId())) {
                ExceptionUtils.throwValidationException("同一用户角色不能重复。");
            }
        }
        if (this.getBirthday() != null) {
            this.setBirthday(DateUtils.getDate(this.getBirthday()));
        }
    }

}
