package com.autumn.zero.file.storage.application.services;

import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.application.service.AbstractQueryApplicationService;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文件导出查询应用服务抽象
 *
 * @param <TQueryEntity>     查询类型
 * @param <TQueryRepository> 查询仓储
 * @param <TOutputItem>      输出项目类型(列表)
 * @param <TOutputDetails>   输出详情类型(单条详情项目)
 */
public abstract class AbstractZeroFileExportQueryAppService<
        TQueryEntity extends Entity<Long>,
        TQueryRepository extends EntityRepository<TQueryEntity, Long>,
        TOutputItem,
        TOutputDetails>
        extends AbstractQueryApplicationService<Long, TQueryEntity, TQueryRepository, TOutputItem, TOutputDetails> implements FileExportAppService {

    /**
     * 文件上传管理
     */
    @Autowired
    protected FileUploadManager fileUploadManager;

    /**
     *
     */
    public AbstractZeroFileExportQueryAppService() {
        super();
    }

    /**
     * @param queryEntityClass
     * @param outputItemClass
     * @param outputDetailsClass
     */
    public AbstractZeroFileExportQueryAppService(Class<TQueryEntity> queryEntityClass,
                                                 Class<TOutputItem> outputItemClass,
                                                 Class<TOutputDetails> outputDetailsClass) {
        super(queryEntityClass, outputItemClass, outputDetailsClass);
    }

    /**
     * @param queryEntityArgName
     * @param outputItemArgName
     * @param outputDetailsArgName
     */
    public AbstractZeroFileExportQueryAppService(String queryEntityArgName, String outputItemArgName, String outputDetailsArgName) {
        super(queryEntityArgName, outputItemArgName, outputDetailsArgName);
    }

    @Override
    public TemporaryFileInformationDto exportFileByExcel(AdvancedQueryInput input) {
        Workbook workbook = this.exportByExcel(input);
        try {
            return fileUploadManager.saveTemporaryFileByWorkbook(this.getModuleName() + ExcelUtils.EXCEL_JOIN_EXTENSION_NAME, workbook);
        } catch (Exception e) {
            throw ExceptionUtils.throwApplicationException(e.getMessage(), e);
        }
    }

}
