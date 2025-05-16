/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.encryptedcolumntest;

import jakarta.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * dump the raw data to show the encrypted values
 * @author aar1069
 */
@Component
@Order(2)
public class ListRawDataCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ListRawDataCommandLineRunner.class);
    
    @Autowired
    private DataSource dataSource;
    
    private JdbcTemplate jdbcTemplate;
    
    private static final class PersonView {
        
        private long id;
        private String name;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "PersonView{" + "id=" + id + ", name=" + name + '}';
        }
        
    }
    
    private PersonView mapRow(ResultSet rs, int rowId) throws SQLException {
        PersonView v = new PersonView();
        v.setId(rs.getLong("PersonId"));
        v.setName(rs.getString("Name"));
        return v;
    }
    
    private final RowMapper<PersonView> personViewRowMapper = (rs, rowId) -> mapRow(rs, rowId);
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("listing all raw data");
        
        List<PersonView> rawData = jdbcTemplate.query("select * from Person", personViewRowMapper);
        rawData.forEach(p -> logger.info("read raw person {}", p));
        
    }
    
    @PostConstruct
    public void initializeTemplate() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
