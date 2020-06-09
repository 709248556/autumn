package com.autumn.web.handlers;

import com.autumn.exception.ExceptionUtils;
import com.autumn.redis.AutumnRedisTemplate;
import com.autumn.util.StringUtils;
import com.autumn.web.annotation.NoRepeatSubmit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * 防止重复提交处理器
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-14 21:09
 **/
public class NoRepeatSubmitHandler implements WebMvcConfigurer, HandlerInterceptor, Ordered {

    /**
     * 日志
     */
    private final static Log logger = LogFactory.getLog(NoRepeatSubmitHandler.class);

    private final static String REPEAT_SUBMIT_LOCK_KEY_PREFIX = "autumn_repeat_submit_lock_";

    private final NoRepeatSubmitRequestMappingInfoHandler noRepeatSubmitRequestMappingInfoHandler;
    private final AutumnRedisTemplate autumnRedisTemplate;


    /**
     * @param noRepeatSubmitRequestMappingInfoHandler 处理器
     * @param autumnRedisTemplate                     Redis模板
     */
    public NoRepeatSubmitHandler(NoRepeatSubmitRequestMappingInfoHandler noRepeatSubmitRequestMappingInfoHandler, AutumnRedisTemplate autumnRedisTemplate) {
        this.noRepeatSubmitRequestMappingInfoHandler = noRepeatSubmitRequestMappingInfoHandler;
        this.autumnRedisTemplate = autumnRedisTemplate;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        NoRepeatSubmit noRepeatSubmit = this.noRepeatSubmitRequestMappingInfoHandler.getNoRepeatSubmit(request);
        if (noRepeatSubmit != null && noRepeatSubmit.lockMillisecond() > 0) {
            String urlKey = this.noRepeatSubmitRequestMappingInfoHandler.getRepeatSubmitUrlKey(request);
            HttpSession session = request.getSession();
            if (session == null || session.getId() == null) {
                throw ExceptionUtils.throwUserFriendlyException(urlKey + " 指定 NoRepeatSubmit 操作，但无法读取会话信息，无效的请求。");
            }
            String key = REPEAT_SUBMIT_LOCK_KEY_PREFIX + session.getId() + "_" + urlKey;
            if (noRepeatSubmit.compareContent()) {
                BodyReaderHttpServletRequestWrapper wrapper = this.noRepeatSubmitRequestMappingInfoHandler.getBodyReaderHttpServletRequest(request);
                if (wrapper == null) {
                    wrapper = new BodyReaderHttpServletRequestWrapper(request);
                }
                String value = wrapper.getRequestFullString();
                String lockValue = this.autumnRedisTemplate.opsForCustomValue().get(key);
                if (StringUtils.isNotNullOrBlank(lockValue) && value.equals(lockValue)) {
                    String msg = "会话[" + request.getSession().getId() + "]在[" + noRepeatSubmit.lockMillisecond() + "]毫秒内" + noRepeatSubmit.errorMessage() + "相同的内容。路径[" + urlKey + "]";
                    logger.error(msg);
                    throw ExceptionUtils.throwUserFriendlyException(noRepeatSubmit.errorMessage());
                } else {
                    this.autumnRedisTemplate.opsForValue().set(key, value, noRepeatSubmit.lockMillisecond(), TimeUnit.MILLISECONDS);
                }
            } else {
                String lockValue = this.autumnRedisTemplate.opsForCustomValue().get(key);
                if (StringUtils.isNotNullOrBlank(lockValue)) {
                    String msg = "会话[" + request.getSession().getId() + "]在[" + noRepeatSubmit.lockMillisecond() + "]毫秒内" + noRepeatSubmit.errorMessage() + "。路径[" + urlKey + "]";
                    logger.error(msg);
                    throw ExceptionUtils.throwUserFriendlyException(noRepeatSubmit.errorMessage());
                } else {
                    this.autumnRedisTemplate.opsForValue().set(key, urlKey, noRepeatSubmit.lockMillisecond(), TimeUnit.MILLISECONDS);
                }
            }
        }
        return true;
    }
}
