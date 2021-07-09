package com.washcar.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.washcar.config.core.domain.AjaxResult;
import com.washcar.entity.Msg;
import com.washcar.util.HttpRequest;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.text.ParseException;
import java.util.Arrays;

@RequestMapping("weixin")
@RestController
@Api("微信平台接口管理")
public class WeiXinController {

    /**
     * 获取用户凭证和信息
     * @param js_code
     * @return
     * @throws ParseException
     */
    @GetMapping("/getCode2Session")
    @ResponseBody
    @CrossOrigin
    @ApiOperation("获取用户凭证和信息")
    public AjaxResult getUserOpenid(String js_code) throws ParseException {

        String appId = "wx942fb92e06326680";
        String appSecret = "bfe6191ed80d5af4088802f699c0980f";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code="+js_code+"&grant_type=authorization_code";

        String res = HttpRequest.doGet(url,"");
        JSONObject jsonObject = JSON.parseObject(res);
        return AjaxResult.success("操作成功",jsonObject);
    }


    @PostMapping("/getUserInfoMsg")
    @ResponseBody
    @CrossOrigin
    @ApiOperation("解密用户信息")
    public AjaxResult getUserInfo(@RequestBody Msg msg) throws Base64DecodingException {

        String appId = "wx942fb92e06326680";
        String appSecret = "bfe6191ed80d5af4088802f699c0980f";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code="+msg.getCode()+"&grant_type=authorization_code";

        String res = HttpRequest.doGet(url,"");
        JSONObject jsonObject = JSON.parseObject(res);

        String sessionKey = jsonObject.getString("session_key");

        // 被加密的数据
        byte[] dataByte = Base64.decode(msg.getEncryptedData());
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(msg.getIv());
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                 return AjaxResult.success("操作成功",JSONObject.parseObject(result));
            }
        }catch (NoSuchProviderException | InvalidParameterSpecException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
