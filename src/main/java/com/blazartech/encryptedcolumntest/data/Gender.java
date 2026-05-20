/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.blazartech.encryptedcolumntest.data;

import com.blazartech.enumwithdbvaluebacking.EnumWithDBValueBacking;

/**
 *
 * @author scott
 */
public enum Gender implements EnumWithDBValueBacking<String> {

    Male("M"), Female("F"), NotSpecified("N");
        
    private final String dbValue;

    private Gender(String dbValue) {
        this.dbValue = dbValue;
    }
    
    @Override
    public String getDBValue() {
        return dbValue;
    }
    
}
