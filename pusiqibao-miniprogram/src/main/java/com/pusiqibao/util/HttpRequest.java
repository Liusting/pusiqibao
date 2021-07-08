package com.pusiqibao.util;

import com.alibaba.fastjson.JSONObject;
import com.pusiqibao.config.WXPayConfig;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import okhttp3.HttpUrl;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class HttpRequest {


    /**
     * 下注
     *
     * @param URL
     * @param cookies
     * @return
     */

    public static String doBetPost(String URL, String cookies, String season, String bets) {
        String result;
        try {
            PostMethod postMethod = null;
            postMethod = new PostMethod(URL);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            postMethod.setRequestHeader("Cookie", cookies);
            //参数设置，需要注意的就是里边不能传NULL，要传空字符串
            NameValuePair[] data = {
                    new NameValuePair("lotterytype", "51"),
                    //最新下注期数
                    new NameValuePair("season", season),
                    //最新下注号码数
                    new NameValuePair("bets", bets)
            };

            postMethod.setRequestBody(data);
            org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
            int response = httpClient.executeMethod(postMethod); // 执行POST方法
            result = postMethod.getResponseBodyAsString();
        } catch (Exception e) {
            // logger.info("请求异常"+e.getMessage(),e);
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取最新一期的期数
     * @param URL
     * @param cookies
     * @return
     * @throws ParseException
     */
    public static String seasonNoPost(String URL, String cookies) throws ParseException {
        StringBuffer sb = new StringBuffer("");
        /**
         * 先用SimpleDateFormat.parse() 方法将日期字符串转化为Date格式
         * 通过Date.getTime()方法，将其转化为毫秒数
         */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        String newDate = simpleDateFormat.format(new Date());
        long time = simpleDateFormat.parse(newDate).getTime();

        String result;
        try {
            PostMethod postMethod = null;
            postMethod = new PostMethod(URL);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            postMethod.setRequestHeader("Cookie", cookies);
            //参数设置，需要注意的就是里边不能传NULL，要传空字符串

            NameValuePair[] data = {
                    new NameValuePair("ft", "51"),
                    new NameValuePair("rTm", String.valueOf(time))
            };

            postMethod.setRequestBody(data);
            org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
            int response = httpClient.executeMethod(postMethod); // 执行POST方法
            result = postMethod.getResponseBodyAsString();
        } catch (Exception e) {
            // logger.info("请求异常"+e.getMessage(),e);
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取开奖结果
     *
     * @param URL
     * @param cookies
     * @return
     */
    public static String doPost(String URL, String cookies) {
        System.out.println(cookies);
        StringBuffer sb = new StringBuffer("");
        try {
            // 创建连接
            java.net.URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            //设置请求头
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Cookie",cookies.toString());

            connection.connect();

            // POST请求,包装成json数据
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            JSONObject obj = new JSONObject();
            obj.put("p", 1);
            obj.put("lt", 51);

            out.writeBytes(obj.toString());
            out.flush();
            out.close();

            // 读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }



    /**
     * 获取cookies
     * @param jsonObject
     * @return
     */

    public static String doLoginPost(JSONObject jsonObject) throws ParseException {
        String result;
        /**
         * 先用SimpleDateFormat.parse() 方法将日期字符串转化为Date格式
         * 通过Date.getTime()方法，将其转化为毫秒数
         */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        String newDate = simpleDateFormat.format(new Date());
        long time = simpleDateFormat.parse(newDate).getTime();
        try {
            String postURL = "https://rycp158.com/d/member/login";
            PostMethod postMethod = null;
            postMethod = new PostMethod(postURL);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            postMethod.setRequestHeader("Cookie", "u=60e492; timerStamp="+time);
            //参数设置，需要注意的就是里边不能传NULL，要传空字符串

            NameValuePair[] data = {
                    new NameValuePair("data", jsonObject.toJSONString())
            };

            postMethod.setRequestBody(data);
            org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
            int response = httpClient.executeMethod(postMethod); // 执行POST方法
            result = postMethod.getResponseBodyAsString();
        } catch (Exception e) {
            // logger.info("请求异常"+e.getMessage(),e);
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取cookies
     * @param jsonObject
     * @return
     */

    public static String getCookies(JSONObject jsonObject,String SessionId,String UserName) throws ParseException {
        String result;
        /**
         * 先用SimpleDateFormat.parse() 方法将日期字符串转化为Date格式
         * 通过Date.getTime()方法，将其转化为毫秒数
         */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        String newDate = simpleDateFormat.format(new Date());
        long time = simpleDateFormat.parse(newDate).getTime();
        StringBuffer sb = new StringBuffer();
        sb.append("u=fbb445;");
        sb.append("zz="+SessionId+";");
        sb.append("LoginName="+UserName+";");
        sb.append("test=false;");
        sb.append("timerStamp="+time);
        try {
            String postURL = "https://rycp158.com/d/m/member/getgameentry";
            PostMethod postMethod = null;
            postMethod = new PostMethod(postURL);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            postMethod.setRequestHeader("Cookie", sb.toString());
            //参数设置，需要注意的就是里边不能传NULL，要传空字符串

            NameValuePair[] data = {
                    new NameValuePair("data", jsonObject.toJSONString())
            };

            postMethod.setRequestBody(data);
            org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
            int response = httpClient.executeMethod(postMethod); // 执行POST方法
            result = postMethod.getResponseBodyAsString();
        } catch (Exception e) {
            // logger.info("请求异常"+e.getMessage(),e);
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * 用户注册
     *
     * @param jsonObject
     * @return
     */

    public static String doReGisterPost(JSONObject jsonObject) throws ParseException {
        String result;
        /**
         * 先用SimpleDateFormat.parse() 方法将日期字符串转化为Date格式
         * 通过Date.getTime()方法，将其转化为毫秒数
         */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        String newDate = simpleDateFormat.format(new Date());
        long time = simpleDateFormat.parse(newDate).getTime();
        try {
            String postURL = "https://rycp158.com/d/member/register";
            PostMethod postMethod = null;
            postMethod = new PostMethod(postURL);
            postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=UTF-8");
            postMethod.addRequestHeader("Cookie", "u=7484d5; timerStamp="+time);
            //参数设置，需要注意的就是里边不能传NULL，要传空字符串
            NameValuePair[] data = {
                    new NameValuePair("data", jsonObject.toJSONString())
            };
            postMethod.setRequestBody(data);
            org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
            int response = httpClient.executeMethod(postMethod); // 执行POST方法
            result = postMethod.getResponseBodyAsString();
        } catch (Exception e) {
            // logger.info("请求异常"+e.getMessage(),e);
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }


    /**
     * 手动获取账户历史记录
     *
     * @param URL
     * @param cookies
     * @param pageNumber
     * @param date
     * @return
     */
    public static String domorePost(String URL, String cookies, String pageNumber, String date) {
        String result = null;
        try {
            PostMethod postMethod = null;
            postMethod = new PostMethod(URL);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            postMethod.setRequestHeader("Cookie", cookies);
            //参数设置，需要注意的就是里边不能传NULL，要传空字符串
            NameValuePair[] data = {
                    new NameValuePair("p", pageNumber),
                    new NameValuePair("lt", "51"),
                    new NameValuePair("date", date)
            };

            postMethod.setRequestBody(data);
            org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
            int response = httpClient.executeMethod(postMethod); // 执行POST方法
            result = postMethod.getResponseBodyAsString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }


    /**
     * 获取最新一期下单记录
     *
     * @param url
     * @param cookies
     * @return
     */
    public static String doGet(String url, String cookies) {
        try {
            //创建get请求
            HttpGet httpGet = new HttpGet(url);

            httpGet.setHeader("Connection", "keep-alive");
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            CloseableHttpClient httpClient = httpClientBuilder.build();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            // 响应状态
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }



    private static String sign(byte[] message) throws NoSuchAlgorithmException, IOException, SignatureException, InvalidKeyException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCk4BLDPQ0hdFrS\n" +
                "MBFp153UXkbPNXH+XPBrY49PlluvAesrguVoyvNKyZLiU5aW0vcaUg1LQjwsr38P\n" +
                "DkDM4qzvPF8euDukkgTMBrB1FtINJykVa4If1pFKYkcvnRzD2iQ3dha2dNlAqjwb\n" +
                "JOtnbgvAZIi/uKKQrubGDRK1bgKYfND2WCSdAdmMrObjAbIj8pJ+BKjqEml0O8Fi\n" +
                "RUxAxN+qoYYVGg3piOPsfj6SwRFdpqAVXX08y++zFVF13iHE5OdEPCFHceUkE+pC\n" +
                "lIwf2q7PW3fUokTdnnCoz52ao2NcGENudDOsgxDYeGtR6RH6V4Bqxekg51z724V7\n" +
                "cQgPsdFNAgMBAAECggEAFE/uv9LjrT1yevalDo8byLActSZ2dsnobLKFU17IyNTJ\n" +
                "rkCgFrr6IjqXN/7oTIiNHNcDAESWuUKdurMc1KEQgSDE7znvTyUWJjSkxKgdFLXA\n" +
                "X/0wuM9scueMsZb/ljlnwNVxkuTuZwMSCJ4RylpKZFd+aXBLxttlXvz5UxTMiRCU\n" +
                "28kEx5wpvwya1GQxhS7xAdRSOEHZf3W5RYRCB4KHavYFiI6xF6PjHznIqsbL5y8K\n" +
                "w8gKK5agfi7K/U6I/lzkhSHysF22J+i79HetMsEbDHtfOB5+2v2rBteI3jb/hrag\n" +
                "Zm6NqwK7w0k2u0voDJJdt1vZQ6+jf4kCyAQKMJtdGQKBgQDWEeQVwCVzOxW454Uy\n" +
                "5N8zC3WTg+m1FgBxxFJZYRU9HvPRI62OfRiXWTv2i7fpdrO/6qWnpC9DLtrtr2Wc\n" +
                "8bc7d9ROBZ5zt0Crs+GTAJnabdzGRvEV4jAIupBlU3aUZ7yQyhCtHJlUrX7EHl44\n" +
                "afCJnNPz+rvaQRngLBzh/PB63wKBgQDFK2q35yrczMqs107n1bs43iEfhuDNL0Rb\n" +
                "HnCFjm+O1xhKAnmSb+U1lZOUrTIpragMqDARV+ewfGMw2ePugbIdHhE1SMnqHOVi\n" +
                "icO5kXOA0KUx6xvw2nCPvPT4zyM9vzaAk0ziRZRrYOi4n7SYvYoWAznpfuuNaL8p\n" +
                "nwNqusBlUwKBgGNwXh4Iap0TZizFRyd0D4ZbnVtP3IEk3kH6qzIfmckRlrqgsx9M\n" +
                "Vt7/MY5+KolFfYv5yMeNyfKQUlw0rKPx2GbEbBduHsOr7VuVLISns9A2Vma4T4cV\n" +
                "0bBMUYTv91ZKtdogMwznCwa9rPQXEzdrZrPV6NMdtqNUuFtcwnHrmB3LAoGBALJQ\n" +
                "r7UeJY+Gzo0+M6hLjYTCr2YZz1kBtGpLvyuqQ44FTXwxfM1I0RyC5/OAJ2u2F9NK\n" +
                "kB4/R9Q+yl410IO1W+YleR6dc658758MRKygtLr890mL/br3cvErzMjwXEjNk3b4\n" +
                "wIGqt63c+Ntv5B523FlIFansFQ/QeYIkwyxVNQbtAoGAavVxTzGLo2lPJqLaeco6\n" +
                "wpi4oj85TNCcjbYJC78DKXknHr5qdaIp8yqgOF9JQox3wlolnAwJxTJmDIt73rpj\n" +
                "4OEFOOBTOWyAFv/Goi1jVqsXjrcbSwT+5ZkXfmaQZ04k58NJK3Ltptp+sAGA1o6e\n" +
                "Zhu/47enkH17n2NwUi3v9sQ=";
        PrivateKey merchantPrivateKey = PemUtil
                .loadPrivateKey(new ByteArrayInputStream(privateKey.getBytes("utf-8")));
        sign.initSign(merchantPrivateKey);
        sign.update(message);

        return Base64.getEncoder().encodeToString(sign.sign());
    }



    static String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }


        System.err.println(method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n");
        return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }


    public static PrivateKey getPrivateKey(String filename) throws IOException {

        String content = new String(Files.readAllBytes(Paths.get(filename)), "utf-8");
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

    public static String doGet1(String url, String body) {
        try {
            //创建get请求
            HttpGet httpGet = new HttpGet(url);

            httpGet.setHeader("Authorization",body);
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("User-Agent", "https://zh.wikipedia.org/wiki/User_agent");
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            CloseableHttpClient httpClient = httpClientBuilder.build();
            HttpResponse httpResponse = httpClient.execute(httpGet);

            System.out.println(httpResponse.getStatusLine().getStatusCode());
            // 响应状态
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
}
