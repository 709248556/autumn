package com.autumn.zero.common.library.application.services.common;

import com.autumn.zero.common.library.application.dto.tree.input.TreeCodeInput;
import com.autumn.zero.common.library.application.dto.tree.output.TreeCodeOutput;

/**
 * 具有代码的树形应用服务
 *
 * @param <TInput>         输入类型
 * @param <TOutput>        输出类型
 * @param <TOutputDetails> 详情输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 17:05
 */
public interface TreeCodeAppService<TInput extends TreeCodeInput, TOutput extends TreeCodeOutput, TOutputDetails extends TreeCodeOutput>
        extends TreeAppService<TInput, TOutput, TOutputDetails> {

}
