package com.pusiqibao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.pusiqibao.config.WXPayConfig;
import com.pusiqibao.config.core.domain.AjaxResult;
import com.pusiqibao.entity.Msg;
import com.pusiqibao.service.WeiXinService;
import com.pusiqibao.util.*;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import com.wechat.pay.contrib.apache.httpclient.util.RsaCryptoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import okhttp3.HttpUrl;

@RequestMapping("weixin")
@RestController
@Api("微信平台接口管理")
public class WeiXinController {


    @PostMapping("/wxpay")
    @ApiOperation("用户发起微信支付")
    public JSONObject WXPay(String openId, HttpServletRequest request) throws Exception {
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
                .put("openid", openId);

        System.out.println("订单号：" + orderId);

        objectMapper.writeValue(bos, rootNode);
        //第一步获取prepay_id
        String prepayId = WxPayV3Util.V3PayPost("v3/pay/transactions/jsapi", bos.toString("UTF-8"));

        //第二步获取调起支付的参数
        JSONObject object = WxPayV3Util.WxTuneUp(prepayId, WXPayConfig.appid);
        return object;

    }


    @RequestMapping(value = "/wxnoty", method = {org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
    public void wxnoty(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println("小程序支付异步消息");
        String out_trade_no = WxPayV3Util.notify(request, response, WXPayConfig.merchantSerialNumber);
        System.out.println(out_trade_no);
    }


    @GetMapping("/checkOrder")
    @ApiOperation("查询订单API")
    public JSONObject getOrderStatus(String out_trade_no) throws IOException, URISyntaxException, CertificateException {

        String url = "https://api.mch.weixin.qq.com/v3/pay/transactions/id/4200001169202107089330505622?mchid=1611630545";

        PrivateKey merchantPrivateKey = PemUtil
                .loadPrivateKey(new ByteArrayInputStream(WXPayConfig.privateKey.getBytes("utf-8")));

        //不需要传入微信支付证书了
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(WXPayConfig.mch_id, new PrivateKeySigner(WXPayConfig.merchantSerialNumber, merchantPrivateKey)),
                WXPayConfig.apiV3Key.getBytes("utf-8"));

        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(WXPayConfig.mch_id, WXPayConfig.merchantSerialNumber, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier));

        //创建httpClient实例
        HttpClient httpClient = builder.build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", "application/json");

        //执行请求操作，并拿到结果（同步阻塞）
        HttpResponse response = httpClient.execute(httpGet);

        String bodyAsString = EntityUtils.toString(response.getEntity());

        JSONObject jsonObject1 = JSONObject.parseObject(bodyAsString);
        return jsonObject1;
    }


    @GetMapping("/refunds")
    @ApiOperation("申请交易账单API")
    public JSONObject getRefunds(String out_trade_no) throws Exception {
        String date = "2021-07-08";
        String url = "https://api.mch.weixin.qq.com/v3/bill/fundflowbill?bill_date=2021-07-08";

        PrivateKey merchantPrivateKey = PemUtil
                .loadPrivateKey(new ByteArrayInputStream(WXPayConfig.privateKey.getBytes("utf-8")));

        //不需要传入微信支付证书了
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(WXPayConfig.mch_id, new PrivateKeySigner(WXPayConfig.merchantSerialNumber, merchantPrivateKey)),
                WXPayConfig.apiV3Key.getBytes("utf-8"));

        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(WXPayConfig.mch_id, WXPayConfig.merchantSerialNumber, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier));

        //创建httpClient实例
        HttpClient httpClient = builder.build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", "application/json");

        //执行请求操作，并拿到结果（同步阻塞）
        HttpResponse response = httpClient.execute(httpGet);

        String bodyAsString = EntityUtils.toString(response.getEntity());

        JSONObject jsonObject1 = JSONObject.parseObject(bodyAsString);
        String download_url = jsonObject1.getString("download_url");


        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(download_url);

        String post = WxPayV3Util.getToken("POST", HttpUrl.parse(download_url), WXPayConfig.mch_id, WXPayConfig.merchantSerialNumber, "F:\\IdeaProjects\\pusiqibao\\pusiqibao-miniprogram\\src\\main\\resources\\cert\\apiclient_key.pem", "");
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Authorization",
                "WECHATPAY2-SHA256-RSA2048 " + post);

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response1 = client.execute(httpPost);

        HttpEntity entity = response1.getEntity();
        //按指定编码转换结果实体为String类型
        String body = EntityUtils.toString(entity, "UTF-8");

        System.out.println(body);

        return jsonObject1;
    }


    @GetMapping("/getTradeBill")
    @ApiOperation("申请交易账单API")
    public JSONObject getTradeBill(String out_trade_no,HttpServletRequest request,HttpServletResponse servletResponse) throws Exception {
        String date = "2021-07-08";
        String url = "https://api.mch.weixin.qq.com/v3/bill/fundflowbill?bill_date=2021-07-08";

        PrivateKey merchantPrivateKey = PemUtil
                .loadPrivateKey(new ByteArrayInputStream(WXPayConfig.privateKey.getBytes("utf-8")));

        //不需要传入微信支付证书了
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(WXPayConfig.mch_id, new PrivateKeySigner(WXPayConfig.merchantSerialNumber, merchantPrivateKey)),
                WXPayConfig.apiV3Key.getBytes("utf-8"));

        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(WXPayConfig.mch_id, WXPayConfig.merchantSerialNumber, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier));

        //创建httpClient实例
        HttpClient httpClient = builder.build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", "application/json");

        //执行请求操作，并拿到结果（同步阻塞）
        HttpResponse response = httpClient.execute(httpGet);

        String bodyAsString = EntityUtils.toString(response.getEntity());

        JSONObject jsonObject1 = JSONObject.parseObject(bodyAsString);

        String download_url = jsonObject1.getString("download_url");


        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpGet httpGet1 = new HttpGet(download_url);

        String post = WxPayV3Util.getToken("GET", HttpUrl.parse(download_url), WXPayConfig.mch_id, WXPayConfig.merchantSerialNumber, "F:\\IdeaProjects\\pusiqibao\\pusiqibao-miniprogram\\src\\main\\resources\\cert\\apiclient_key.pem", "");
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpGet1.setHeader("Content-type", "application/json");
        httpGet1.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        httpGet1.setHeader("Accept", "application/json");
        httpGet1.setHeader("Authorization",
                "WECHATPAY2-SHA256-RSA2048 " + post);

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response1 = client.execute(httpGet1);

        HttpEntity responseEntity = response1.getEntity();
        InputStream inputStream = null;
        BufferedInputStream buf = null;
        OutputStream out = null;
        inputStream = responseEntity.getContent();
        out = servletResponse.getOutputStream();
        servletResponse.setContentType("application/x-download");
        buf = new BufferedInputStream(inputStream);



        out.close();
        buf.close();

        HttpEntity entity = response1.getEntity();
        //按指定编码转换结果实体为String类型
        String body = EntityUtils.toString(entity, "UTF-8");

        System.out.println(body);

        return jsonObject1;

    }


    @GetMapping("/code2Session")
    @ApiOperation("用户授权登录后根据code获取用户openid和会话密钥")
    public JSONObject code2Session(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + WXPayConfig.appid + "&secret=" + WXPayConfig.appsecret + "&js_code=" + code + "&grant_type=authorization_code";
        String res = HttpRequest.doGet(url, "");
        JSONObject jsonObject1 = JSONObject.parseObject(res);
        return jsonObject1;
    }


    /**
     * 获取接口凭证access_token
     *
     * @return
     */

    @GetMapping("/getAccessToken")
    @ApiOperation("获取小程序全局唯一后台接口调用凭据access_token")
    @CrossOrigin
    public JSONObject getAccessToken() {
        String appid = WXPayConfig.appid;
        String appsecret = WXPayConfig.appsecret;
        String res = HttpRequest.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + appsecret, "");
        JSONObject jsonObject1 = JSONObject.parseObject(res);
        return jsonObject1;
    }


    @PostMapping("/getUserPhoneNumber")
    @ResponseBody
    @CrossOrigin
    @ApiOperation("解密获取用户手机号码")
    public JSONObject getUserPhoneNumber(@RequestBody Msg msg) throws Base64DecodingException {
        String appId = WXPayConfig.appid;
        String appSecret = WXPayConfig.appsecret;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" +
                appId + "&secret=" + appSecret + "&js_code=" + msg.getCode() +
                "&grant_type=authorization_code";

        String res = HttpRequest.doGet(url, "");
        JSONObject jsonObject = JSON.parseObject(res);

        String sessionKey = jsonObject.getString("session_key");
        String hh = AESForWeixinGetPhoneNumber.decryptWeChatData(sessionKey, msg.getIv(), msg.getEncryptedData());
        JSONObject jsonObject1 = JSONObject.parseObject(hh);
        return jsonObject1;
    }

    @PostMapping("/getUserInfoMsg")
    @ResponseBody
    @CrossOrigin
    @ApiOperation("解密用户信息")
    public JSONObject getUserInfo(@RequestBody Msg msg) throws Base64DecodingException {

        String appId = WXPayConfig.appid;
        String appSecret = WXPayConfig.appsecret;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" +
                appId + "&secret=" + appSecret + "&js_code=" + msg.getCode() +
                "&grant_type=authorization_code";

        String res = HttpRequest.doGet(url, "");
        JSONObject jsonObject = JSON.parseObject(res);

        String sessionKey = jsonObject.getString("session_key");
        String hh = AESForWeixinGetPhoneNumber.decryptWeChatData(sessionKey, msg.getIv(), msg.getEncryptedData());
        JSONObject jsonObject1 = JSONObject.parseObject(hh);
        System.out.println(hh);
        return jsonObject1;
    }
}
