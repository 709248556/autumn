package com.autumn.zero.file.storage.deserializer.json;

import com.autumn.util.json.JsonObjectDeserializerGenerator;
import com.autumn.zero.file.storage.application.dto.DefaultFileUploadIdInput;
import com.autumn.zero.file.storage.application.dto.DefaultFileUploadIdentificationInput;
import com.autumn.zero.file.storage.application.dto.FileUploadIdInput;
import com.autumn.zero.file.storage.application.dto.FileUploadIdentificationInput;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传序列化生成
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-04 0:20
 */
public class FileInputObjectDeserializerGenerator implements JsonObjectDeserializerGenerator {

    @Override
    public Map<Class<?>, Class<?>> generate() {
        Map<Class<?>, Class<?>> generate_map = new HashMap<>(16);
        generate_map.put(FileUploadIdInput.class, DefaultFileUploadIdInput.class);
        generate_map.put(FileUploadIdentificationInput.class, DefaultFileUploadIdentificationInput.class);
        return generate_map;
    }
}
