package com.autumn.zero.ueditor.web.controllers;

import com.autumn.util.StringUtils;
import com.autumn.web.annotation.IgnoreApiResponseBody;
import com.autumn.zero.ueditor.action.ConfigAction;
import com.autumn.zero.ueditor.action.UploadAction;
import com.autumn.zero.ueditor.model.ExecuteStateResult;
import com.autumn.zero.ueditor.model.UeditorActionMap;
import com.autumn.zero.ueditor.model.UeditorActionType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * Html编辑器
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-15 12:52
 */
@RestController
@RequestMapping("/common")
@Api(tags = "百度 Ueditor Api 接口")
public class UeditorController {

    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(UeditorController.class);

    /**
     * 上传执行器
     */
    private final UploadAction uploadAction;
    private final ConfigAction configAction;

    /**
     * 实例化
     *
     * @param uploadAction 上传执行器
     * @param configAction 配置执行器
     */
    public UeditorController(UploadAction uploadAction, ConfigAction configAction) {
        this.uploadAction = uploadAction;
        this.configAction = configAction;
    }

    /**
     * 刷新配置
     */
    @RequestMapping(path = "/ueditor/config/refresh", method = {RequestMethod.POST})
    @ApiOperation(value = "刷新配置")
    @ResponseBody
    public void refreshConfig() {
        this.configAction.refreshConfig();
    }

    /**
     * 请求
     *
     * @param actionType
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(path = "/ueditor", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "ueditor 统一服务接口", produces = "application/x-javascript")
    @IgnoreApiResponseBody
    @ResponseBody
    public void ueditor(@RequestParam("action") String action, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/x-javascript");
        response.setCharacterEncoding("UTF-8");
        OutputStreamWriter writer = null;
        try {
            String msg = this.exec(action, request);
            writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
            writer.write(msg);
            writer.flush();
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    /**
     * @param action
     * @param request
     * @return
     */
    private String exec(String action, HttpServletRequest request) {
        if (StringUtils.isNullOrBlank(action)) {
            return ExecuteStateResult.fail("action 为空。").toJson();
        }
        UeditorActionType type = UeditorActionMap.getActionType(action);
        if (type == null) {
            return ExecuteStateResult.fail("action[" + action + "]无效。").toJson();
        }
        return this.uploadAction.execute(request, type, this.configAction.readConfig());
    }

}