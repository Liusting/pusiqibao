package com.pusiqibao.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.wxpay.sdk.WXPayUtil;
import com.pusiqibao.config.WXPayConfig;
import com.pusiqibao.util.OrderIdUtil;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import io.swagger.annotations.ApiOperation;
import okhttp3.HttpUrl;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
public class TestController {
    @GetMapping("/text")
    @ApiOperation("测试")
    public String gh() throws IOException {

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");



        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        String orderId = "XHY" + OrderIdUtil.getId();
        rootNode.put("mchid", WXPayConfig.mch_id)
                .put("appid", WXPayConfig.appid)
                .put("description", "Image形象店-深圳腾大-QQ公仔")
                .put("notify_url", "https://www.weixin.qq.com/wxpay/pay.php")
                .put("out_trade_no", orderId);
        rootNode.putObject("amount")
                .put("total", 100)
                .put("currency", "CNY");

        rootNode.putObject("payer")
                .put("openid", "orvW-5SlhPME9yc8uKsQF9J-r7Vg");

        objectMapper.writeValue(bos, rootNode);

        httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
        // 读取证书私钥
        PrivateKey privateKey = PemUtil.loadPrivateKey(new ClassPathResource("/cert/apiclient_key.pem").getInputStream());

// 读取平台证书(验签)
        X509Certificate certificate = PemUtil.loadCertificate(
                new ClassPathResource("/cert/apiclient_cert.pem").getInputStream());
        List<X509Certificate> certificates = Arrays.asList(certificate);

// 生成请求签名
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(WXPayConfig.mch_id, WXPayConfig.merchantSerialNumber, privateKey).withWechatpay(certificates);

        HttpClient httpClient = builder.build();

        HttpResponse response = httpClient.execute(httpPost);
        String prepay_id = EntityUtils.toString(response.getEntity());

        JSONObject payMap = new JSONObject();
        payMap.put("appId", WXPayConfig.appid);
        long timestamp = System.currentTimeMillis() / 1000;
        payMap.put("timeStamp", timestamp + "");
        String nonceStr = WXPayUtil.generateNonceStr();
        payMap.put("nonceStr", nonceStr);
        payMap.put("package", "prepay_id=" + prepay_id);
        payMap.put("signType", "RSA");

// 生成请求签名
        String jsonStr = new JSONObject(payMap).toJSONString();


//        String paySign = getSign(WXPayUtil.generateNonceStr(), System.currentTimeMillis() / 1000, jsonStr, "/cert/apiclient_key.pem");
        return "";
    }


    public static String getSign(String method,String nonceStr, long timestamp, String body, String serialPath) {
        // 请求路径
        HttpUrl url = HttpUrl.parse("https://api.mch.weixin.qq.com/v3/certificates");
        // 组装需要签名的数据
        String message = buildMessage(method, url, timestamp, nonceStr, body);
        // 使用私钥对数据进行签名（加密），【数据签名之后需要转换为字节数组（采用utf-8）】
        String signature = null;
        try {
            signature = sign(message.getBytes("utf-8"), serialPath);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        return signature;
        return "mchid=\"" + WXPayConfig.mch_id + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + WXPayConfig.merchantSerialNumber + "\","
                + "signature=\"" + signature + "\"";
    }

    private static String sign(byte[] message, String serialPath) {
        try {

            // 签名方式（固定SHA256withRSA）
            Signature sign = Signature.getInstance("SHA256withRSA");
            // 使用私钥进行初始化签名（私钥需要从私钥文件【证书】中读取）
            sign.initSign(getPrivateKey(serialPath));
            // 签名更新
            sign.update(message);
            // 对签名结果进行Base64编码
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PrivateKey getPrivateKey(String serialPath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(serialPath)), "UTF-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }

    }

    private static String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }

        return method + "\n" + canonicalUrl + "\n" + timestamp + "\n" + nonceStr + "\n" + body + "\n";
    }
}
