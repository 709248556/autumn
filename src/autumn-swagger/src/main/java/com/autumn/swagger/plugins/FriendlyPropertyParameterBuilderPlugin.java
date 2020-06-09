package com.autumn.swagger.plugins;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import static springfox.documentation.swagger.common.SwaggerPluginSupport.pluginDoesApply;

/**
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2020-01-10 04:07
 **/
public class FriendlyPropertyParameterBuilderPlugin implements ParameterBuilderPlugin {

    @Override
    public void apply(ParameterContext parameterContext) {

    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return pluginDoesApply(delimiter);
    }
}
