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
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ListRawDataCommandLineRunner implements CommandLineRunner {
    
    @Autowired
    private DataSource dataSource;
    
    private JdbcTemplate jdbcTemplate;
    
    @Data
    private static final class PersonView {
        private long id;
        private String name;        
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
        log.info("listing all raw data");
        
        List<PersonView> rawData = jdbcTemplate.query("select * from Person", personViewRowMapper);
        rawData.forEach(p -> log.info("read raw person {}", p));
        
    }
    
    @PostConstruct
    public void initializeTemplate() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
