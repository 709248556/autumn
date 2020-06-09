package com.autumn.zero.common.library.entities.sys;

import com.autumn.domain.entities.AbstractSystemCommonConfig;
import com.autumn.mybatis.mapper.annotation.TableDocument;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 * 系统公共配置
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-30 21:46
 */
@ToString(callSuper = true)
@Getter
@Setter
@Table(name = "sys_common_config")
@TableDocument(value = "系统公共配置", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class SystemCommonConfig extends AbstractSystemCommonConfig {

    private static final long serialVersionUID = -4818888503817118080L;
}
