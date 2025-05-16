/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.encryptedcolumntest.config;

import com.blazartech.products.crypto.BlazarCryptoFile;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author AAR1069
 */
@Configuration
public class DataSourceConfiguration {
    
    @Autowired
    private BlazarCryptoFile cryptoFile;
    
    @Value("${application.dataSource.userid}")
    private String userid;
    
    @Value("${application.dataSource.resource}")
    private String resourceID;

    @Value("${application.dataSource.url}")
    private String url;

    @Value("${application.dataSource.driverClass}")
    private String driverClass;

    @Bean(destroyMethod = "")
    @Primary
    public DataSource dataSource() {

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driverClass);
        ds.setUrl(url);
        ds.setUsername(userid);
        ds.setPassword(cryptoFile.getPassword(userid, resourceID));
        return ds;
    }

}
