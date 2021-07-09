package com.pusiqibao.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pusiqibao.config.WXPayConfig;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

public class WxPayV3Util {

    //请求网关
    private static final String url_prex = "https://api.mch.weixin.qq.com/";
    //编码
    private static final String charset = "UTF-8";

    public static String V3PayPost(String url, String jsonStr) throws Exception {
        String body = "";
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url_prex + url);
        //装填参数
        StringEntity s = new StringEntity(jsonStr, "UTF-8");
        //设置参数到请求对象中
        httpPost.setEntity(s);
        String post = getToken("POST", HttpUrl.parse(url_prex + url), WXPayConfig.mch_id, WXPayConfig.merchantSerialNumber, "F:\\IdeaProjects\\pusiqibao\\pusiqibao-miniprogram\\src\\main\\resources\\cert\\apiclient_key.pem", jsonStr);
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Authorization",
                "WECHATPAY2-SHA256-RSA2048 " + post);
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, "UTF-8");
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        switch (url) {
            case "v3/pay/transactions/app"://返回APP支付所需的参数
                return JSONObject.parseObject(body).getString("prepay_id");
            case "v3/pay/transactions/jsapi"://返回JSAPI支付所需的参数
                return JSONObject.parseObject(body).getString("prepay_id");
            case "v3/pay/transactions/native"://返回native的请求地址
                return JSONObject.parseObject(body).getString("code_url");
            case "v3/pay/transactions/h5"://返回h5支付的链接
                return JSONObject.parseObject(body).getString("h5_url");
        }
        return null;
    }


    public static String V3PayGet(String url, String jsonStr) throws Exception {
        String result = "";
        String get = getToken("GET", HttpUrl.parse(url_prex + url), WXPayConfig.mch_id, WXPayConfig.merchantSerialNumber, "F:\\IdeaProjects\\pusiqibao\\pusiqibao-miniprogram\\src\\main\\resources\\cert\\apiclient_key.pem", jsonStr);

        //创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建实例方法
        HttpGet httpget = new HttpGet(url);
        httpget.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpget.addHeader("Accept", "application/json");
        httpget.addHeader("Authorization", "WECHATPAY2-SHA256-RSA2048 " + get);
        HttpResponse response = null;

        if(response.getStatusLine().getStatusCode()==200){
            try {
                result= EntityUtils.toString(response.getEntity(),"UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        httpClient.close();
        return result;
    }



    public static JSONObject WxTuneUp(String prepayId, String appId) throws Exception {
        String time = System.currentTimeMillis() / 1000 + "";
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        String packageStr = "prepay_id=" + prepayId;
        ArrayList<String> list = new ArrayList<>();
        list.add(appId);
        list.add(time);
        list.add(nonceStr);
        list.add(packageStr);
        //加载签名
        String packageSign = sign(buildSignMessage(list).getBytes());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appid", appId);
        jsonObject.put("timeStamp", time);
        jsonObject.put("nonceStr", nonceStr);
        jsonObject.put("packages", packageStr);
        jsonObject.put("signType", "RSA");
        jsonObject.put("paySign", packageSign);
        return jsonObject;
    }

    /**
     * 处理返回对象
     *
     * @param request
     * @return
     */
    static String readData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder result = new StringBuilder();
            br = request.getReader();
            for (String line; (line = br.readLine()) != null; ) {
                if (result.length() > 0) {
                    result.append("\n");
                }
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String notify(HttpServletRequest request, HttpServletResponse response, String privateKey) throws Exception {
        Map<String, String> map = new HashMap<>(12);
        String result = readData(request);
        // 需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
        String plainText = verifyNotify(result, privateKey);
        if (StringUtils.isNotEmpty(plainText)) {
            response.setStatus(200);
            map.put("code", "SUCCESS");
            map.put("message", "SUCCESS");
        } else {
            response.setStatus(500);
            map.put("code", "ERROR");
            map.put("message", "签名错误");
        }
        response.setHeader("Content-type", "application/json");
        response.getOutputStream().write(JSONObject.toJSONString(map).getBytes(StandardCharsets.UTF_8));
        response.flushBuffer();
        String out_trade_no = JSONObject.parseObject(plainText).getString("out_trade_no");
        return out_trade_no;
    }

    public static String verifyNotify(String body, String key) throws Exception {
        // 获取平台证书序列号
        JSONObject resultObject = JSON.parseObject(body);
        JSONObject resource = resultObject.getJSONObject("resource");
        String cipherText = resource.getString("ciphertext");
        String nonceStr = resource.getString("nonce");
        String associatedData = resource.getString("associated_data");
        AesUtil aesUtil = new AesUtil(key.getBytes(StandardCharsets.UTF_8));
        // 密文解密
        return aesUtil.decryptToString(
                associatedData.getBytes(StandardCharsets.UTF_8),
                nonceStr.getBytes(StandardCharsets.UTF_8),
                cipherText
        );
    }


    static String buildSignMessage(ArrayList<String> signMessage) {
        if (signMessage == null || signMessage.size() <= 0) {
            return null;
        }
        StringBuilder sbf = new StringBuilder();
        for (String str : signMessage) {
            sbf.append(str).append("\n");
        }
        return sbf.toString();
    }

    public static String getToken(String method, HttpUrl url, String mercId, String serial_no, String privateKeyFilePath, String body) throws Exception {
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        long timestamp = System.currentTimeMillis() / 1000;
        String message = buildMessage(method, url, timestamp, nonceStr, body);
        String signature = sign(message.getBytes("UTF-8"));
        return "mchid=\"" + mercId + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + serial_no + "\","
                + "signature=\"" + signature + "\"";
    }

    static String sign(byte[] message) throws Exception {
        Signature sign = Signature.getInstance("SHA256withRSA");
        PrivateKey merchantPrivateKey = PemUtil
                .loadPrivateKey(new ByteArrayInputStream(WXPayConfig.privateKey.getBytes("utf-8")));
        sign.initSign(merchantPrivateKey);
        sign.update(message);
//        sign.initSign(getPrivateKey(privateKeyFilePath));
//        sign.update(message);
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    static String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }
        return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

    /**
     * 获取私钥。
     *
     * @param filename 私钥文件路径  (required)
     * @return 私钥对象
     */
    static PrivateKey getPrivateKey(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)), "UTF-8");
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

}
