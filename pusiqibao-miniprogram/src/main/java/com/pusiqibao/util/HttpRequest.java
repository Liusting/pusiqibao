package com.pusiqibao.util;

import com.alibaba.fastjson.JSONObject;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
}
