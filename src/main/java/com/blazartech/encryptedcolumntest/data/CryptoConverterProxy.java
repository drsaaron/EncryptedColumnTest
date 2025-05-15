/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.encryptedcolumntest.data;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see https://thoughts-on-java.org/how-to-use-jpa-type-converter-to/ This will
 * be a proxy for the actual implementation, which will be a spring bean with
 * spring wiring.
 * 
 * @author AAR1069
 */
@Converter
public class CryptoConverterProxy extends LazyInitializer<AttributeConverter<String, String>> implements AttributeConverter<String, String> {

    private static final Logger log = LoggerFactory.getLogger(CryptoConverterProxy.class);
    
    /**
     * use the lazy initializer from apache commons to get the implementation bean
     * once from the app context.
     * 
     * @return 
     */
    @Override
    protected AttributeConverter<String, String> initialize() {
        log.info("initializing implementation");
        return AppContext.getBean("cryptoConverterImpl", AttributeConverter.class);
    }

    @Override
    public String convertToDatabaseColumn(String ccNumber) {
        try {
            return get().convertToDatabaseColumn(ccNumber);
        } catch (ConcurrentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return get().convertToEntityAttribute(dbData);
        } catch (ConcurrentException e) {
            throw new RuntimeException(e);
        }
    }

}
