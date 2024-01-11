package com.catpaw.catpawcore.common.handler.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class EncryptManager {


    private static final Charset ENCODING_TYPE = StandardCharsets.UTF_8;
    private static final String INSTANCE_TYPE = "AES/CBC/PKCS5Padding";

    @Value("${encrypt-key}")
    private String secretKey;
    private IvParameterSpec ivParameterSpec;
    private SecretKeySpec secretKeySpec;
    private Cipher cipher;

    @PostConstruct
    public void init() throws NoSuchPaddingException, NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        ivParameterSpec = new IvParameterSpec(iv);
        byte[] bytes = secretKey.getBytes(ENCODING_TYPE);
        secretKeySpec = new SecretKeySpec(bytes, "AES");
        cipher = Cipher.getInstance(INSTANCE_TYPE);
    }

    public String encrypt(String plain) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(plain.getBytes(ENCODING_TYPE));

            return new String(Base64.getEncoder().encode(encrypted), ENCODING_TYPE);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String plain) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decoded = Base64.getDecoder().decode(plain.getBytes(ENCODING_TYPE));

            return new String(cipher.doFinal(decoded), ENCODING_TYPE);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
