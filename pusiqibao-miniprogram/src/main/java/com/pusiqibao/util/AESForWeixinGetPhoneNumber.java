package com.pusiqibao.util;



import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.tools.jar.Main;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
public class AESForWeixinGetPhoneNumber {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 解密
     *
     * @param sessionKey
     * @param iv
     * @param encryptedData
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidParameterSpecException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String decryptWeChatData(String sessionKey, String iv, String encryptedData) {
        final Base64.Decoder decoder = Base64.getMimeDecoder();
        byte[] sessionKeyByte = decoder.decode(sessionKey);
        byte[] encryptedDataByte = decoder.decode(encryptedData);
        byte[] ivByte = decoder.decode(iv);

        byte[] bytes;
        try {
            Key key = new SecretKeySpec(sessionKeyByte, "AES");
            AlgorithmParameters algorithmParameters = null;
            Cipher cipher = null;
            algorithmParameters = AlgorithmParameters.getInstance("AES");
            algorithmParameters.init(new IvParameterSpec(ivByte));
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, algorithmParameters);
            bytes = cipher.doFinal(encryptedDataByte);
        } catch (InvalidParameterSpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            bytes = new byte[0];
        }

        String decryptString = new String(bytes, StandardCharsets.UTF_8);
        log.info("微信解密后数据为：{}", decryptString);
        return decryptString;
    }


}
