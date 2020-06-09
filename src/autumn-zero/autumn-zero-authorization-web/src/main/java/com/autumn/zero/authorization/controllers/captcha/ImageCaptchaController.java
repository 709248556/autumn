package com.autumn.zero.authorization.controllers.captcha;

import com.autumn.web.annotation.IgnoreApiResponseBody;
import com.autumn.zero.authorization.application.dto.captcha.ImageCaptchaInput;
import com.autumn.zero.authorization.application.dto.captcha.ImageCaptchaOutput;
import com.autumn.zero.authorization.application.services.captcha.ImageCaptchaAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * 图形验证码
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-31 01:00
 **/
@RestController
@RequestMapping("/captcha/image")
@Api(tags = "图形验证码")
public class ImageCaptchaController {

    private final ImageCaptchaAppService service;

    public ImageCaptchaController(ImageCaptchaAppService service) {
        this.service = service;
    }

    /**
     * 基于流的图片验证码
     *
     * @param input    输入
     * @param response 响应
     * @return
     */
    @RequestMapping(path = "/stream", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "基于流的图片验证码", produces = "application/octet-stream")
    @IgnoreApiResponseBody
    public StreamingResponseBody imageCaptchaForStream(ImageCaptchaInput input, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        //禁止图像缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        BufferedImage image = service.captchaByImage(input);
        return outputStream -> {
            try {
                ImageIO.write(image, "jpg", outputStream);
            } finally {
                outputStream.flush();
                outputStream.close();
            }
        };
    }

    /**
     * 生成base64编码图片验证码
     *
     * @param input 输入
     * @return
     */
    @RequestMapping(path = "/base64", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "生成base64编码图片验证码")
    public ImageCaptchaOutput imageCaptcha(ImageCaptchaInput input) {
        return service.captcha(input);
    }
}
