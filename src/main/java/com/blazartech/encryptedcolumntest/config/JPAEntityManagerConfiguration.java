/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.encryptedcolumntest.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 *
 * @author AAR1069
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.blazartech.encryptedcolumntest.data.repos",
                       entityManagerFactoryRef = "entityManagerFactory",
                       transactionManagerRef = "transactionManager"
                       )
public class JPAEntityManagerConfiguration {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JpaVendorAdapter jpaVendorAdaptor;
    
    private static final String PERSISTENCE_UNIT_NAME = "com.blazartech_EncryptedColumnTest_jar_1.0-SNAPSHOTPU";
    
    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean f = new LocalContainerEntityManagerFactoryBean();
        f.setDataSource(dataSource);
        f.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
        f.setJpaVendorAdapter(jpaVendorAdaptor);
        f.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        
        // set the hibernate.hbm2ddl.auto property.  Hibernate desperately wants to do
        // schema changes, which is obviously not right.  I've tried setting this
        // property in the persistence.xml file, but hibernate doesn't seem to pick 
        // it up.  So set it here.  And set it to an invalid date so that it doesn't
        // do a thing.
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "none");
        f.setJpaProperties(props);
        
        return f;

    }
}
