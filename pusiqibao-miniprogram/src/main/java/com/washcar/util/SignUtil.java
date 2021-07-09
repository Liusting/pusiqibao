package com.pusiqibao.util;

import org.springframework.util.Assert;

import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class SignUtil {
    private final static String algorithm = "SHA256withRSA";

    public static String sign(String content, PrivateKey privateKey) throws Exception {
        Assert.notNull(content, "签名内容不能为空");
        final Signature signature = Signature.getInstance(algorithm);
        signature.initSign(privateKey);
        signature.update(content.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign());
    }

}
