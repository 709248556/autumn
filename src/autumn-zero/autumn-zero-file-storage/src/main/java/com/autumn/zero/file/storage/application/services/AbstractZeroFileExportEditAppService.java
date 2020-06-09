package com.autumn.zero.file.storage.application.services;

import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.application.service.AbstractEditApplicationService;
import com.autumn.domain.entities.Entity;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 具有文件导出编辑提抽象
 *
 * @param <TEntity>          实体类型
 * @param <TRepository>      仓储类型
 * @param <TQueryEntity>     查询实体类型
 * @param <TQueryRepository> 查询仓储类型
 * @param <TAddInput>        添加输入类型
 * @param <TUpdateInput>     更新输入类型
 * @param <TOutputItem>      输出项目类型
 * @param <TOutputDetails>   详情输出类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-24 18:42
 */
public abstract class AbstractZeroFileExportEditAppService<TEntity extends Entity<Long>,
        TRepository extends EntityRepository<TEntity, Long>,
        TQueryEntity extends Entity<Long>,
        TQueryRepository extends EntityRepository<TQueryEntity, Long>,
        TAddInput,
        TUpdateInput extends Entity<Long>,
        TOutputItem,
        TOutputDetails>
        extends AbstractEditApplicationService<Long, TEntity, TRepository, TQueryEntity, TQueryRepository, TAddInput, TUpdateInput, TOutputItem, TOutputDetails>
        implements FileExportAppService {

    /**
     * 文件上传管理
     */
    @Autowired
    protected FileUploadManager fileUploadManager;

    /**
     *
     */
    public AbstractZeroFileExportEditAppService() {
        super();
    }

    /**
     * @param entityArgName
     * @param queryEntityArgName
     * @param outputItemArgName
     * @param outputDetailsArgName
     */
    public AbstractZeroFileExportEditAppService(String entityArgName, String queryEntityArgName, String outputItemArgName, String outputDetailsArgName) {
        super(entityArgName, queryEntityArgName, outputItemArgName, outputDetailsArgName);
    }

    /**
     * @param entityClass
     * @param queryEntityClass
     * @param outputItemClass
     * @param outputDetailsClass
     */
    public AbstractZeroFileExportEditAppService(Class<TEntity> entityClass,
                                                   Class<TQueryEntity> queryEntityClass,
                                                   Class<TOutputItem> outputItemClass,
                                                   Class<TOutputDetails> outputDetailsClass) {
        super(entityClass, queryEntityClass, outputItemClass, outputDetailsClass);
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
