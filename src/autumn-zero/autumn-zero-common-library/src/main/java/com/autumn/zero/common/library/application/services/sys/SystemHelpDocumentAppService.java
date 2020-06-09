package com.autumn.zero.common.library.application.services.sys;

import com.autumn.zero.common.library.application.dto.sys.help.*;
import com.autumn.zero.common.library.application.services.common.TreeAppService;

/**
 * 系统帮助文档服务
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 10:38
 **/
public interface SystemHelpDocumentAppService extends TreeAppService<AbstractSystemHelpDocumentInput, SystemHelpDocumentListOutput, SystemHelpDocumentOutput> {

    /**
     * 生成html
     *
     * @param input 输入
     */
    void generateHtml(SystemHelpDocumentGenerateInput input);

    /**
     * 查询访问
     *
     * @param id 主键
     * @return
     */
    SystemHelpDocumentVisitOutput queryVisit(Long id);
}
