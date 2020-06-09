package com.autumn.application.dto.input;

import com.autumn.validation.DefaultDataValidation;
import lombok.Getter;
import lombok.Setter;

/**
 * 默认导入数据选项
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-07 17:52
 **/
@Getter
@Setter
public class DefaultDataImportOptionInput extends DefaultDataValidation implements DataImportOptionInput {

    private static final long serialVersionUID = -4601333342989191452L;
}
