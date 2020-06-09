package com.autumn.logistics.configure;

import com.autumn.logistics.channel.AbstractLogisticsChannelProperties;
import com.autumn.logistics.channel.best.BestLogisticsChannelProperties;
import com.autumn.logistics.channel.cainiao.CaiNiaoLogisticsChannelProperties;
import com.autumn.logistics.channel.ems.EmsLogisticsChannelProperties;
import com.autumn.logistics.channel.jd.JDLogisticsChannelProperties;
import com.autumn.logistics.channel.kuaidi100.KuaiDi100LogisticsChannelProperties;
import com.autumn.logistics.channel.sf.SFLogisticsChannelProperties;
import com.autumn.logistics.channel.sto.STOLogisticsChannelProperties;
import com.autumn.logistics.channel.ttk.TTKLogisticsChannelProperties;
import com.autumn.logistics.channel.yto.YTOLogisticsChannelProperties;
import com.autumn.logistics.channel.yunda.YunDaLogisticsChannelProperties;
import com.autumn.logistics.channel.zto.ZTOLogisticsChannelProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 物流属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 11:03
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = AbstractLogisticsChannelProperties.PREFIX)
public class AutumnLogisticsChannelProperties implements Serializable {

    private static final long serialVersionUID = -2329951080619047086L;

    /**
     * 百世达物流通道属性
     */
    private BestLogisticsChannelProperties best = new BestLogisticsChannelProperties();

    /**
     * 菜鸟物流通道属性
     */
    private CaiNiaoLogisticsChannelProperties cainiao = new CaiNiaoLogisticsChannelProperties();

    /**
     * 邮政Ems通道属性
     */
    private EmsLogisticsChannelProperties ems = new EmsLogisticsChannelProperties();

    /**
     * 京东物流通道属性
     */
    private JDLogisticsChannelProperties jd = new JDLogisticsChannelProperties();

    /**
     * 快递100通道属性
     */
    private KuaiDi100LogisticsChannelProperties kuaidi100 = new KuaiDi100LogisticsChannelProperties();

    /**
     * 顺丰快递通道属性
     */
    private SFLogisticsChannelProperties sf = new SFLogisticsChannelProperties();

    /**
     * 申通快递通道属性
     */
    private STOLogisticsChannelProperties sto = new STOLogisticsChannelProperties();

    /**
     * 天天快递物流通道属性
     */
    private TTKLogisticsChannelProperties ttk = new TTKLogisticsChannelProperties();

    /**
     * 圆通快递通道属性
     */
    private YTOLogisticsChannelProperties yto = new YTOLogisticsChannelProperties();

    /**
     * 韵达快递通道属性
     */
    private YunDaLogisticsChannelProperties yunda = new YunDaLogisticsChannelProperties();

    /**
     * 韵达快递通道属性
     */
    private ZTOLogisticsChannelProperties zto = new ZTOLogisticsChannelProperties();

    public AutumnLogisticsChannelProperties() {

    }

}
