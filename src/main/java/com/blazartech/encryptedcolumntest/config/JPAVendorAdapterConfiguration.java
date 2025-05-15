/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.encryptedcolumntest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

/**
 *
 * @author AAR1069
 */
@Configuration
public class JPAVendorAdapterConfiguration {
    
    private static final Database DATABASE = Database.MYSQL;
    
    @Bean
    public JpaVendorAdapter jpaVendorAdaptor() {
        EclipseLinkJpaVendorAdapter va = new EclipseLinkJpaVendorAdapter();
        va.setShowSql(true);
        va.setDatabase(DATABASE);
        va.setGenerateDdl(true);
        return va;
    }
}
