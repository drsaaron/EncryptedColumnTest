/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.encryptedcolumntest.data;

import com.blazartech.enumwithdbvaluebacking.EnumWithDBValueBackingConverter;
import jakarta.persistence.Converter;

/**
 *
 * @author scott
 */
@Converter(autoApply = true)
public class GenderConverter extends EnumWithDBValueBackingConverter<Gender, String> {

    public GenderConverter() {
        super(Gender.class);
    }

}
