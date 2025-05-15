/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.encryptedcolumntest.data;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.AttributeConverter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * implement the attribute converter as a spring bean
 * 
 * @author aar1069
 */
@Named
public class CryptoConverterImpl implements AttributeConverter<String, String> {
    
    private static final Logger logger = LoggerFactory.getLogger(CryptoConverterImpl.class);

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    
    @Inject
    private PrivateKeyProvider keyProvider;
    
    @Override
    public String convertToDatabaseColumn(String ccNumber) {
        // do some encryption
        Key key = new SecretKeySpec(keyProvider.getKey(), keyProvider.getKeySpecName());
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            return new String(Base64.getEncoder().encode(c.doFinal(ccNumber.getBytes())));
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        // do some decryption
        Key key = new SecretKeySpec(keyProvider.getKey(), keyProvider.getKeySpecName());
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            return new String(c.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
