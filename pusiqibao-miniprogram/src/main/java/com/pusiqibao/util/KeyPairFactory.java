package com.pusiqibao.util;

import org.springframework.core.io.ClassPathResource;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public class KeyPairFactory {
    private KeyStore store;
    private final Object lock = new Object();
    /**
     * 获取公私钥.
     *
     * @param keyPath  the key path
     * @param keyAlias the key alias
     * @param keyPass  password
     * @return the key pair
     */
    public KeyPair createPKCS12(String keyPath, String keyAlias, String keyPass) {
        ClassPathResource resource = new ClassPathResource(keyPath);
        char[] pem = keyPass.toCharArray();
        try {
            synchronized (lock) {
                if (store == null) {
                    synchronized (lock) {
                        store = KeyStore.getInstance("PKCS12");
                        store.load(resource.getInputStream(), pem);
                    }
                }
            }
            X509Certificate certificate = (X509Certificate) store.getCertificate(keyAlias);
            certificate.checkValidity();
            // 证书的序列号 也有用
            String serialNumber = certificate.getSerialNumber().toString(16).toUpperCase();
            // 证书的 公钥
            PublicKey publicKey = certificate.getPublicKey();
            // 证书的私钥
            PrivateKey storeKey = (PrivateKey) store.getKey(keyAlias, pem);
            return new KeyPair(publicKey, storeKey);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load keys from store: " + resource, e);
        }
    }
}
