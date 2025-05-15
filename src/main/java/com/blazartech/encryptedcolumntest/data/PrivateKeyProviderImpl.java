/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.encryptedcolumntest.data;

import jakarta.inject.Named;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author aar1069
 */
@Named
public class PrivateKeyProviderImpl implements PrivateKeyProvider, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(PrivateKeyProviderImpl.class);

    @Value("${keyFile.name}")
    private String keyFileName;
    
    private File keyFile;

    private static final int KEY_SIZE = 128;
    private static final String KEY_SPEC_NAME = "AES";

    private byte[] key = null;

    private void readKeyFile() {
        try {
            logger.info("creating key file {}.", keyFileName);

            if (!keyFile.exists()) {

                KeyGenerator keyGen = KeyGenerator.getInstance(KEY_SPEC_NAME);
                keyGen.init(KEY_SIZE);
                SecretKey sk = keyGen.generateKey();
                try (FileOutputStream fw = new FileOutputStream(keyFile)) {
                    fw.write(Base64.getEncoder().encode(sk.getEncoded()));
                } catch (IOException io) {
                    logger.error("error creating key file: " + io.getMessage(), io);
                    throw new RuntimeException("error creating key file: " + io.getMessage(), io);
                }
            }

            FileInputStream is = new FileInputStream(keyFile);
            key = Base64.getDecoder().decode(is.readAllBytes());
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException("error getting secret key: " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] getKey() {
        return key;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        keyFile = new File(keyFileName);
        readKeyFile();
    }

    @Override
    public String getKeySpecName() {
        return KEY_SPEC_NAME;
    }

}
