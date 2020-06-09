package com.autumn.pay.channel.weixin.util;

import com.autumn.exception.SignException;
import com.autumn.pay.channel.AutumnPayException;
import com.autumn.util.StringUtils;
import com.autumn.util.security.HashUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


/**
 * 微信支付帮助
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-30 10:32
 */
public abstract class WeiXinPayUtils {

    /**
     * 日志
     */
    private static final Log logger = LogFactory.getLog(WeiXinPayUtils.class);

    /**
     * 生成随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 处理 HTTPS API返回数据，转换成Map对象。return_code为SUCCESS时，验证签名。
     *
     * @param xmlStr API返回的XML格式数据
     * @return Map类型数据
     * @throws Exception
     */
    public static Map<String, String> processResponseXml(String xmlStr, String signType, String signkey) {
        Map<String, String> respData = WeiXinXmlUtils.xmlToMap(xmlStr);
        String return_code = respData.get(WeixinPayConstants.RETURN_CODE);
        if (StringUtils.isNullOrBlank(return_code)) {
            throw new AutumnPayException(String.format("No `return_code` in XML: %s", xmlStr));
        }
        if (StringUtils.isNullOrBlank(signType)) {
            signType = respData.get(WeixinPayConstants.FIELD_SIGN_TYPE);
            if (StringUtils.isNullOrBlank(signType)) {
                signType = WeixinPayConstants.MD5;
                if(logger.isDebugEnabled()){
                    logger.debug("无签名类型[signType],将采用默认[" + WeixinPayConstants.MD5 + "],XML：" + xmlStr);
                }
            }
        }
        if (return_code.equalsIgnoreCase(WeixinPayConstants.SUCCESS)) {
            if (isSignatureValid(respData, signType, signkey)) {
                return respData;
            } else {
                throw new SignException(String.format("Invalid sign value in XML: %s", xmlStr));
            }
        } else {
            throw new AutumnPayException(String.format("return_code value %s is invalid in XML: %s", return_code, xmlStr));
        }
    }

    /**
     * 生成签名
     *
     * @param data     数据
     * @param signType 签名类型
     * @param signkey  签名键
     * @return
     */
    public static String generateSignature(Map<String, String> data, String signType, String signkey) {
        String sign = data.get(WeixinPayConstants.FIELD_SIGN);
        if (StringUtils.isNullOrBlank(signType)) {
            signType = WeixinPayConstants.MD5;
        }
        try {
            data.remove(WeixinPayConstants.FIELD_SIGN);
            Set<String> keySet = data.keySet();
            String[] keyArray = keySet.toArray(new String[0]);
            Arrays.sort(keyArray);
            StringBuilder sb = new StringBuilder();
            for (String key : keyArray) {
                String value = data.get(key);
                if (StringUtils.isNotNullOrBlank(value)) {
                    sb.append(key).append("=").append(value).append("&");
                }
            }
            sb.append("key=").append(signkey);
            if (signType.equals(WeixinPayConstants.MD5)) {
                return HashUtils.md5(sb.toString()).toUpperCase();
            } else if (signType.equals(WeixinPayConstants.HMACSHA256)) {
                try {
                    return HashUtils.hmacSHA256(sb.toString(), signkey).toUpperCase();
                } catch (Exception e) {
                    throw new AutumnPayException(e.getMessage(), e);
                }
            } else {
                throw new AutumnPayException(String.format("不支持的签名类型: %s", signType));
            }
        } finally {
            if (StringUtils.isNotNullOrBlank(sign)) {
                data.put(WeixinPayConstants.FIELD_SIGN, sign);
            }
        }
    }

    /**
     * 验签
     *
     * @param data     数据
     * @param signType 签名类型
     * @param signkey  签名键
     * @return
     */
    public static boolean isSignatureValid(Map<String, String> data, String signType, String signkey) {
        if (!data.containsKey(WeixinPayConstants.FIELD_SIGN)) {
            return false;
        }
        String sign = data.get(WeixinPayConstants.FIELD_SIGN);
        return generateSignature(data, signType, signkey).equals(sign);
    }

    /**
     * 结果是否成功
     *
     * @param resultMap
     * @return
     */
    public static boolean isResultSuccess(Map<String, String> resultMap) {
        return WeixinPayConstants.SUCCESS.equalsIgnoreCase(resultMap.get(WeixinPayConstants.RESULT_CODE));
    }

    /**
     * 获取错误代码
     *
     * @param resultMap
     * @return
     */
    public static String getErrorCode(Map<String, String> resultMap) {
        String code = resultMap.get(WeixinPayConstants.ERR_CODE);
        if (StringUtils.isNullOrBlank(code)) {
            code = "";
        }
        return code;
    }

    /**
     * 获取错误代码详情
     *
     * @param resultMap
     * @return
     */
    public static String getErrorCodeDes(Map<String, String> resultMap) {
        String msg = resultMap.get(WeixinPayConstants.ERR_CODE_DES);
        if (StringUtils.isNullOrBlank(msg)) {
            msg = "未知错误";
        }
        return msg;
    }

}
