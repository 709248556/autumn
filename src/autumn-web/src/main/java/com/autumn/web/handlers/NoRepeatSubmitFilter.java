package com.autumn.web.handlers;

import com.autumn.web.annotation.NoRepeatSubmit;
import org.springframework.core.Ordered;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 防止重复提交过滤
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-15 01:24
 **/
public class NoRepeatSubmitFilter implements Filter, Ordered {

    private final NoRepeatSubmitRequestMappingInfoHandler noRepeatSubmitRequestMappingInfoHandler;

    /**
     * @param noRepeatSubmitRequestMappingInfoHandler
     */
    public NoRepeatSubmitFilter(NoRepeatSubmitRequestMappingInfoHandler noRepeatSubmitRequestMappingInfoHandler) {
        this.noRepeatSubmitRequestMappingInfoHandler = noRepeatSubmitRequestMappingInfoHandler;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        NoRepeatSubmit noRepeatSubmit = this.noRepeatSubmitRequestMappingInfoHandler.getNoRepeatSubmit(request);
        if (noRepeatSubmit != null && noRepeatSubmit.compareContent()) {
            requestWrapper = this.noRepeatSubmitRequestMappingInfoHandler.getBodyReaderHttpServletRequest(request);
            if (requestWrapper == null) {
                requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
            }
        }
        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
