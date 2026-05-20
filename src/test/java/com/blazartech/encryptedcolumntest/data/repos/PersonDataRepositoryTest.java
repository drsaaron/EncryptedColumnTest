/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.blazartech.encryptedcolumntest.data.repos;

import com.blazartech.encryptedcolumntest.config.JPAEntityManagerConfiguration;
import com.blazartech.encryptedcolumntest.config.JPATransactionManagerConfiguration;
import com.blazartech.encryptedcolumntest.config.JPAVendorAdapterConfiguration;
import com.blazartech.encryptedcolumntest.data.AppContext;
import com.blazartech.encryptedcolumntest.data.CryptoConverterImpl;
import com.blazartech.encryptedcolumntest.data.Gender;
import com.blazartech.encryptedcolumntest.data.PersonData;
import com.blazartech.encryptedcolumntest.data.PrivateKeyProviderImpl;
import java.util.Collection;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author scott
 */
@ExtendWith(SpringExtension.class)
@Slf4j
@ContextConfiguration(classes = {
    JPAEntityManagerConfiguration.class,
    JPATransactionManagerConfiguration.class,
    JPAVendorAdapterConfiguration.class,
    PersonDataRepositoryTest.PersonDataRepositoryTestConfiguration.class
})
@Transactional
public class PersonDataRepositoryTest {
    
    @Configuration
    @PropertySource("classpath:application.properties")
    public static class PersonDataRepositoryTestConfiguration {
        
        @Bean(destroyMethod = "")
        public DataSource dataSource() {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("org.h2.Driver");
            ds.setUrl("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1");
            
            return ds;
        }
        
        @Autowired
        private ApplicationContext applicationContext;
        
        @Bean
        public AppContext appContext() {
            AppContext c = new AppContext();
            c.setApplicationContext(applicationContext);
            return c;
        }
        
        @Bean
        public CryptoConverterImpl cryptoConverterImpl() {
            return new CryptoConverterImpl();
        }
        
        @Bean
        public PrivateKeyProviderImpl privateKeyProvider() {
            return new PrivateKeyProviderImpl();
        }
    }
    
    @Autowired
    private PersonDataRepository instance;
    
    public PersonDataRepositoryTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        PersonData p1 = new PersonData();
        p1.setGender(Gender.Male);
        p1.setName("first person");
        
        instance.save(p1);
        
        PersonData p2 = new PersonData();
        p2.setGender(Gender.Female);
        p2.setName("second person");
        
        instance.save(p2);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of findByGender method, of class PersonDataRepository.
     */
    @Test
    public void testFindByGender() {
        log.info("findByGender");
        
        Gender g = Gender.Male;
        Collection<PersonData> result = instance.findByGender(g);
        
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    public void testFindAll() {
        log.info("findAll");
        
        Collection<PersonData> p = instance.findAll();
        assertNotNull(p);
        assertEquals(2, p.size());
    }
}
