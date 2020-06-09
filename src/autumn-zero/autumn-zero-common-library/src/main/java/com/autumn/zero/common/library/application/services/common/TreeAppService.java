package com.autumn.zero.common.library.application.services.common;

import com.autumn.application.service.EditByStatusApplicationService;
import com.autumn.zero.common.library.application.dto.tree.input.ChildrenQueryInput;
import com.autumn.zero.common.library.application.dto.tree.input.TreeInput;
import com.autumn.zero.common.library.application.dto.tree.output.TreeOutput;
import com.autumn.zero.file.storage.application.services.FileExportAppService;

import java.util.List;

/**
 * 树形应用服务
 *
 * @param <TInput>         输入类型
 * @param <TOutputItem>    输出项目类型
 * @param <TOutputDetails> 输出详情类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 16:54
 */
public interface TreeAppService<TInput extends TreeInput, TOutputItem extends TreeOutput, TOutputDetails extends TreeOutput>
        extends EditByStatusApplicationService<Long, TInput, TInput, TOutputItem, TOutputDetails>, FileExportAppService {

    /**
     * 查询子级
     *
     * @param input 输入
     * @return
     */
    List<TOutputItem> queryChildren(ChildrenQueryInput input);
}
