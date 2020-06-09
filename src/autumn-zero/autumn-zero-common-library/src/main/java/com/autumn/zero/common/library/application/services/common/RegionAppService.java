package com.autumn.zero.common.library.application.services.common;

import com.autumn.zero.common.library.application.dto.common.region.RegionImportTemplateDto;
import com.autumn.zero.common.library.application.dto.common.region.RegionInput;
import com.autumn.zero.common.library.application.dto.common.region.RegionOutput;
import com.autumn.zero.file.storage.application.services.DataImportFileAppService;

/**
 * 行政区应用服务
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 21:07
 */
public interface RegionAppService extends TreeCodeAppService<RegionInput, RegionOutput, RegionOutput>, DataImportFileAppService<RegionImportTemplateDto> {

}
