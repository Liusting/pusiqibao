package com.pusiqibao.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pusiqibao.config.WXPayConfig;
import com.pusiqibao.config.WxH5Config;
import com.pusiqibao.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

@Api("微信公众号接口")
@RestController
@ResponseBody
@RequestMapping("/weixinh5")
public class WeiXinH5Controller {
    private static final Logger log = LoggerFactory.getLogger(WeiXinController.class);

    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("/")
    public String JsSdk(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:MP_verify_tXAwXbIzOvKuImm3.txt");
        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(isr);//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }


    @GetMapping("/getSign")
    @ApiOperation("验证js-sdk")
    @CrossOrigin
    public JSONObject getSign(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
        //先获取access_token
        String res = HttpRequest.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx644f5dd37896957e&secret=0c638b3d5675c932e9faa2ef51e4d8a7","");
        JSONObject jsonObject1 = JSONObject.parseObject(res);
        String access_token = jsonObject1.getString("access_token");
        //获取jsapi_ticket
        String jsapiTicketRes = HttpRequest.doGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi","");
        String jsToken = "";
        JSONObject jsTokenJsonObject = JSONObject.parseObject(jsapiTicketRes);
        if (jsTokenJsonObject != null) {
            int errcode = jsTokenJsonObject.getInteger("errcode");
            if (errcode == 0) {
                jsToken = jsTokenJsonObject.getString("ticket");
            }
        }
        //生成时间戳和随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);


        String pathUrl = "http://weixin123.gz2vip.idcfengye.com/weixinh5/getSign";
        //将参数排序并拼接字符串
        String str1 = "noncestr=" + noncestr;
        String str2 = "jsapi_ticket=" + jsToken;
        String str3 = "timestamp=" + timestamp;
        String str4 = "url=" + pathUrl;

        String[] paramArr = new String[] { str1, str2, str3, str4 };
        Arrays.sort(paramArr);

        // 将排序后的结果拼接成一个字符串
        String content = paramArr[0].concat("&").concat(paramArr[1]).concat("&").concat(paramArr[2]).concat("&").concat(paramArr[3]);
        String signature = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 对接后的字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            signature = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        JSONObject resjson = new JSONObject();
        resjson.put("appId", WxH5Config.appid);
        resjson.put("timestamp",timestamp);
        resjson.put("nonceStr",noncestr);
        resjson.put("signature",signature);

        return resjson;
    }

    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }



    @PostMapping("/wxpay")
    @ApiOperation("用户发起微信支付")
    public JSONObject WXPay(String openId, HttpServletRequest request) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        String orderId = "XHY" + OrderIdUtil.getId();
        rootNode.put("mchid", WXPayConfig.mch_id)
                .put("appid", WxH5Config.appid)
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
        JSONObject object = WxPayV3Util.WxTuneUp(prepayId, WxH5Config.appid);
        return object;

    }


    @GetMapping("/checkToken")
    @CrossOrigin
    @ApiOperation("接口配置信息，需要正确响应微信发送的Token验证")
    public String test(HttpServletRequest request, HttpServletResponse response){
        try {

            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            System.out.println("signature："+signature+",timestamp："+timestamp+",nonce="+nonce+",echostr="+echostr);
            log.info("本身" + signature);
            if (WeiXinH5SignUtil.checkSignature(signature, timestamp, nonce)) {
                return echostr;
            }
        } catch (Exception e) {
            log.error("验证公众号token失败", e);
        }
        return null;
    }


    /**
     * 获取接口凭证access_token
     * @return
     */

    @GetMapping("/getAccessToken")
    @ApiOperation("获取接口凭证access_token")
    @CrossOrigin
    public JSONObject getAccessToken(){
        String res = HttpRequest.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WxH5Config.appid+"&secret="+WxH5Config.appsecret,"");
        JSONObject jsonObject1 = JSONObject.parseObject(res);
        return  jsonObject1;
    }


    @PostMapping("/menuCreate")
    @ApiOperation("自定义菜单创建")
    @CrossOrigin
    public JSONObject menuCreate(@RequestParam("access_token") String access_token,@RequestBody JSONObject jsonObject){
        String Url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
        String res = HttpRequest.domorePost(Url,jsonObject);

        JSONObject jsonObject1 = JSONObject.parseObject(res);
        return  jsonObject1;
    }


    @GetMapping("/getCurrentSelfMenuInfo")
    @ApiOperation("查询自定义菜单")
    @CrossOrigin
    public JSONObject getCurrentSelfMenuInfo(@RequestParam("access_token") String access_token){
        String Url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token="+access_token;
        String res = HttpRequest.doGet(Url,"");
        JSONObject jsonObject1 = JSONObject.parseObject(res);
        return  jsonObject1;
    }


    @GetMapping("/getUserList")
    @ApiOperation("获取用户列表")
    @CrossOrigin
    public JSONObject getUserList(@RequestParam("access_token") String access_token){
        String Url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+access_token+"&next_openid="+"";
        String res = HttpRequest.doGet(Url,"");
        JSONObject jsonObject1 = JSONObject.parseObject(res);
        return  jsonObject1;
    }


    @GetMapping("/getUserInfo")
    @ApiOperation("获取用户信息")
    @CrossOrigin
    public JSONObject getUserList(@RequestParam("access_token") String access_token,@RequestParam("openid") String openid){
        String Url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
        String res = HttpRequest.doGet(Url,"");
        JSONObject jsonObject1 = JSONObject.parseObject(res);
        return  jsonObject1;
    }

    @GetMapping("/getProvince")
    @ApiOperation("获取省份信息")
    @CrossOrigin
    public JSONObject getProvince(){
        String Url = "http://mock-api.com/PKeZpPz0.mock/getProvince";
        String res = HttpRequest.doGet(Url,"");
        JSONObject jsonObject1 = JSONObject.parseObject(res);
        return  jsonObject1;
    }

    @GetMapping("/getCityArea")
    @ApiOperation("获取区域信息")
    @CrossOrigin
    public JSONObject getCityArea(){
        String Url = "http://mock-api.com/PKeZpPz0.mock/getCityArea";
        String res = HttpRequest.doGet(Url,"");
        JSONObject jsonObject1 = JSONObject.parseObject(res);
        return  jsonObject1;
    }

    @GetMapping("/getFourAdd")
    @ApiOperation("获取区域信息")
    @CrossOrigin
    public JSONArray getFourAdd(){
        String jsonStr = "";
        try {
            Resource resource = resourceLoader.getResource("classpath:region.json");
            InputStream is = resource.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            StringBuilder result = new StringBuilder();
            File jsonFile = new File("D:\\ideaProject\\bet\\src\\main\\resources\\region.json");
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            reader.close();
            jsonStr = sb.toString();
            JSONArray jsObject = JSONArray.parseArray(jsonStr);
            return jsObject;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
