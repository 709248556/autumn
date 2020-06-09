package com.autumn.zero.file.storage;

import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.application.service.DataImportApplicationService;
import com.autumn.application.service.QueryApplicationService;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;
import com.autumn.zero.file.storage.annotation.FileOutputBinding;
import com.autumn.zero.file.storage.annotation.FileUploadBinding;
import com.autumn.zero.file.storage.application.dto.*;
import com.autumn.zero.file.storage.application.services.FileUploadAppService;
import com.autumn.zero.file.storage.application.services.FileUploadManager;
import com.autumn.zero.file.storage.services.vo.DefaultUseUploadFileRequest;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件存储工具
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-31 4:15
 */
public class FileStorageUtils {

    private static final int DEFAULT_TYPE_SIZE = 500;

    private static final Set<String> IMAGE_UPLOAD_LIMIT_EXTENSIONS;

    private final static Map<Class<?>, Map<BeanProperty, FileUploadBinding>> FILE_UPLOAD_BINDING_MAP = new ConcurrentHashMap<>(DEFAULT_TYPE_SIZE);

    private final static Map<Class<?>, Map<BeanProperty, FileOutputBinding>> FILE_OUTPUT_BINDING_MAP = new ConcurrentHashMap<>(DEFAULT_TYPE_SIZE);

    static {
        Set<String> imageSet = new HashSet<>();
        imageSet.add("png");
        imageSet.add("jpg");
        imageSet.add("jpeg");
        imageSet.add("gif");
        imageSet.add("bmp");
        IMAGE_UPLOAD_LIMIT_EXTENSIONS = Collections.unmodifiableSet(imageSet);
    }

    /**
     * 获取图片上传限制扩展名集合
     *
     * @return
     */
    public static Set<String> getImageUploadLimitExtensions() {
        return IMAGE_UPLOAD_LIMIT_EXTENSIONS;
    }

    /**
     * 创建绑定文件上传标识输入
     *
     * @param input
     * @return
     */
    public static FileUploadIdentificationInput createBindingFileUploadIdentificationInput(Object input) {
        ExceptionUtils.checkNotNull(input, "input");
        Map<BeanProperty, FileUploadBinding> bindingMap = getFileUploadBindingPropertyMap(input.getClass());
        if (bindingMap.size() == 0) {
            return null;
        }
        final List<DefaultUseUploadFileRequest> items = new ArrayList<>();
        for (Map.Entry<BeanProperty, FileUploadBinding> entry : bindingMap.entrySet()) {
            Object value = entry.getKey().getValue(input);
            if (value != null) {
                DefaultUseUploadFileRequest item = new DefaultUseUploadFileRequest();
                item.setIdentification(entry.getValue().identification());
                item.setUploadId((Long) value);
                item.setUploadExplain(entry.getValue().explain());
                items.add(item);
            }
        }
        return new DefaultFileUploadIdentificationInput(items);
    }

    /**
     * 加载上传文件输出
     *
     * @param uploadFiles 上传的文件集合
     * @param output      输出
     */
    public static void loadUploadFileOutput(List<FileAttachmentInformationResponse> uploadFiles, Object output) {
        if (uploadFiles == null) {
            return;
        }
        Set<Long> bindingUploadKey = new HashSet<>(uploadFiles.size());
        if (output != null) {
            Map<BeanProperty, FileOutputBinding> bindingMap = getFileOutputBindingPropertyMap(output.getClass());
            if (bindingMap.size() > 0) {
                for (Map.Entry<BeanProperty, FileOutputBinding> entry : bindingMap.entrySet()) {
                    FileAttachmentInformationResponse response = null;
                    for (FileAttachmentInformationResponse uploadFile : uploadFiles) {
                        if (uploadFile.getIdentification() != null && uploadFile.getIdentification().equals(entry.getValue().identification())) {
                            response = uploadFile;
                            break;
                        }
                    }
                    if (response != null) {
                        bindingUploadKey.add(response.getId());
                    }
                    entry.getKey().setValue(output, response);
                }
            }
        }
        if (output instanceof FileUploadAttachmentOutput) {
            FileUploadAttachmentOutput fileOutput = (FileUploadAttachmentOutput) output;
            if (bindingUploadKey.size() > 0) {
                fileOutput.setUploadFiles(new ArrayList<>(uploadFiles.size()));
                for (FileAttachmentInformationResponse uploadFile : uploadFiles) {
                    if (!bindingUploadKey.contains(uploadFile.getId())) {
                        fileOutput.getUploadFiles().add(uploadFile);
                    }
                }
            } else {
                fileOutput.setUploadFiles(uploadFiles);
            }
        }
    }

    /**
     * 获取上传绑定属性Map
     *
     * @param beanClass bean类型
     * @return
     */
    public static Map<BeanProperty, FileUploadBinding> getFileUploadBindingPropertyMap(Class<?> beanClass) {
        return FILE_UPLOAD_BINDING_MAP.computeIfAbsent(beanClass,
                type -> getPropertyAnnotationMap(type, FileUploadBinding.class, Long.class));
    }

    /**
     * 获取输出绑定属性Map
     *
     * @param beanClass bean类型
     * @return
     */
    public static Map<BeanProperty, FileOutputBinding> getFileOutputBindingPropertyMap(Class<?> beanClass) {
        return FILE_OUTPUT_BINDING_MAP.computeIfAbsent(beanClass,
                type -> getPropertyAnnotationMap(type, FileOutputBinding.class, FileAttachmentInformationResponse.class));
    }


    private static <TAnnotation extends Annotation> Map<BeanProperty, TAnnotation>
    getPropertyAnnotationMap(Class<?> beanClass, Class<TAnnotation> annotationClass, Class<?> propertyClass) {
        ExceptionUtils.checkNotNull(beanClass, "beanClass");
        Map<String, BeanProperty> beanPropertyMap = ReflectUtils.getBeanPropertyMap(beanClass);
        Map<BeanProperty, TAnnotation> map = new HashMap<>(beanPropertyMap.size());
        for (BeanProperty property : beanPropertyMap.values()) {
            if (property.canReadWrite()) {
                TAnnotation binding = property.getAnnotation(annotationClass);
                if (binding != null) {
                    if (!property.getType().equals(propertyClass)) {
                        ExceptionUtils.throwSystemException("属性[" + property.getName() + "]使用注解[" + annotationClass.getName() + "]时，属性类型必须为[" + propertyClass.getName() + "]。");
                    }
                    map.put(property, binding);
                }
            }
        }
        return Collections.unmodifiableMap(map);
    }

    /**
     * 保存上传文件
     *
     * @param service 服务
     * @param file    文件
     * @return
     * @throws Exception
     */
    public static FileAttachmentInformationResponse saveUploadFile(FileUploadAppService service, MultipartFile file)
            throws Exception {
        FileInput input = new FileInput();
        input.setInputStream(file.getInputStream());
        input.setOriginalFilename(file.getOriginalFilename());
        input.setOriginalFileSize(file.getSize());
        return service.saveUploadFile(input);
    }

    /**
     * 创建 Excel 临时文件
     *
     * @param service
     * @param fileUploadManager
     * @param input
     * @param <TOutputItem>     输出项目类型
     * @return
     * @throws Exception
     */
    public static <TKey extends Serializable, TOutputItem, TOutputDetails> TemporaryFileInformationDto
    createExcelTemporaryFile(QueryApplicationService<TKey, TOutputItem, TOutputDetails> service,
                             FileUploadManager fileUploadManager,
                             AdvancedQueryInput input)
            throws Exception {
        Workbook workbook = service.exportByExcel(input);
        return fileUploadManager.saveTemporaryFileByWorkbook(service.getModuleName() + ExcelUtils.EXCEL_JOIN_EXTENSION_NAME, workbook);
    }

    /**
     * 创建导入模板临时文件
     *
     * @param service           服务
     * @param fileUploadManager 文件管理
     * @param <ImportTemplate>
     * @return
     */
    public static <ImportTemplate> TemporaryFileInformationDto
    createExcelImportTemplateForFileInformation(DataImportApplicationService<ImportTemplate> service, FileUploadManager fileUploadManager) {
        Workbook workbook = service.excelImportTemplate();
        try {
            return fileUploadManager.saveTemporaryFileByWorkbook(service.getModuleName() + "Excel导入模板" + ExcelUtils.EXCEL_JOIN_EXTENSION_NAME, workbook);
        } catch (Exception e) {
            throw ExceptionUtils.throwApplicationException(e.getMessage(), e);
        }
    }

}
