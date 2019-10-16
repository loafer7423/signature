package com.loafer.common;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @ClassName RSAUtil
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/14 14:38
 * @Version V1.0
 **/
public class RSAUtil {

    public static String publicKey; // 公钥
    public static String privateKey; // 私钥

    /**
     * 生成公钥和私钥
     */
    public static void generateKey() {
        // 1.初始化秘钥
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom sr = new SecureRandom(); // 随机数生成器
            keyPairGenerator.initialize(4096, sr); // 设置4096位长的秘钥
            KeyPair keyPair = keyPairGenerator.generateKeyPair(); // 开始创建
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 进行转码
            publicKey = Base64.encodeBase64String(rsaPublicKey.getEncoded());
            // 进行转码
            privateKey = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 私钥匙加密或解密
     *
     * @param content
     * @param privateKeyStr
     * @return
     */
    public static String encryptByprivateKey(String content, String privateKeyStr, int opmode) {
        // 私钥要用PKCS8进行处理
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
        KeyFactory keyFactory;
        PrivateKey privateKey;
        Cipher cipher;
        byte[] result;
        String text = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            // 还原Key对象
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(opmode, privateKey);
            //加密解密
            text = encryptTxt(opmode,cipher,content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public static String encryptTxt(int opmode, Cipher cipher, String content){
        byte[] result;
        String text = null;
        try{
            if (opmode == Cipher.ENCRYPT_MODE) { // 加密
                result = cipher.doFinal(content.getBytes());
                text = Base64.encodeBase64String(result);
            } else if (opmode == Cipher.DECRYPT_MODE) { // 解密
                result = cipher.doFinal(Base64.decodeBase64(content));
                text = new String(result, "UTF-8");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return text;
    }

    /**
     * 公钥匙加密或解密
     *
     * @param content
     * @return
     */
    public static String encryptByPublicKey(String content, String publicKeyStr, int opmode) {
        // 公钥要用X509进行处理
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
        KeyFactory keyFactory;
        PublicKey publicKey;
        Cipher cipher;
        byte[] result;
        String text = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            // 还原Key对象
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(opmode, publicKey);
            text = encryptTxt(opmode,cipher,content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }


    /**
     * @description: 客户端请求公钥加密，发送给服务端
     * @author wangdong
     * @date 2019/10/16 9:35
     */
    public static void main(String[] args) {
        // 1. 生成(公钥和私钥)密钥对
//        RSAUtil.generateKey();
//        System.out.println("公钥:" + RSAUtil.publicKey);
//        System.out.println("私钥:" + RSAUtil.privateKey);
        System.out.println("----------公钥加密私钥解密(推荐)，非对称加密，公钥保存在客户端，私钥保存在服务端-------------");
        String public_key="MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAgFJwqtXJViqLnl2NxZA3MGxKJLJUEDNjMGqdAT8hhu+65Hu3NU0KMRufVw++20CloGZaSt0osQLlkNRUrQVEIhX2O6TGRBektj3sk4xh+/qYybpuggJaGf+VGuac6nARpPk8umhgSjo0ejoeFsln756yO55EhjUhw8zPKa5TiUWCgNUZZ3Rp88SI5lpKm01zE5f566WFUnODpSNUkZ/aa6d2Rj6e5GmF3+eXdeC/GiNNFXrejhrGRLji7pRdsq9GwgryN2wDx6RlibP2n/5wxr9WLK1yjjhpmVmpCew3+Tv1AY1CfeZPPpCE99J+g+Du/dg+iOJMbAypY8Fbh8t89oIehsYJ9WlWHK1DL0WbHMhpeL5cHUVizOHFnZJRId26BO2hkOR/Bjod+BN56pSRNrvIYOhQpNYiCaiB4y8c9hNgzgVsoDAEmZuC/eqTD7ekn1KcrskCzcv7ZWwkeJ0mG77wDNX1vkQEPF/i3Q6YlxIWdJVRgVxVZQHwiUJrYhtJaoViJQKnCCSC1yMCGr4Nd+FJZQ4RtPUJph3kmrj4bTHMsCQCIPaTKA7HxqC+dM3u0DKr4xVlkkZyvZWS4+A+9Vilzam3QcJOUEbLPP+w0G9fjGmR2EAbGwcXyw6RVC+Elt8hIqPyKHZAmXsws6mv7PY8Jlkx+J1zAZ9B1UMsZeMCAwEAAQ==";
        // 使用 公钥加密,私钥解密
        String textsr = "{\n" +
                "    \"base\":{\n" +
                "        \"uid\":12,\n" +
                "        \"name\":\"李四2\"\n" +
                "    },\n" +
                "    \"sex\":\"男\"\n" +
                "}";
        String encryptByPublic = RSAUtil.encryptByPublicKey(textsr, public_key, Cipher.ENCRYPT_MODE);
        System.out.println("公钥加密:" + encryptByPublic);
    }


}
