/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.encryptedcolumntest.data;

import com.blazartech.enumwithdbvaluebacking.EnumWithDBValueBacking;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author scott
 */
@Converter(autoApply = true)
@Slf4j
public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender x) {
        log.info("getting DB value for {}", x);
        return x.getDBValue();
    }

    @Override
    public Gender convertToEntityAttribute(String y) {
        log.info("getting enum value for DB {}", y);
        return EnumWithDBValueBacking.getFromDBValue(Gender.class, y);
    }
    
}
