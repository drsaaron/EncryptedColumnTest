/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.encryptedcolumntest;

import com.blazartech.encryptedcolumntest.data.PersonData;
import com.blazartech.encryptedcolumntest.data.repos.PersonDataRepository;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author AAR1069
 */
@Component
@Order(1)
public class TestEncryptedColumnCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestEncryptedColumnCommandLineRunner.class);

    @Autowired
    private PersonDataRepository personRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        logger.info("starting test");

        // start by clearing the table.
        personRepo.deleteAll();

        // create a fake person 
        PersonData p = new PersonData();
        p.setName("Scott Aaron");

        // save.  use flush to ensure data are saved and ID populated as it will
        // be used later. hibernate seems to do that even with save, but eclipselink
        // doesn't unless we use saveAndFlush.  Without a transaction save is fine 
        // in both.  So it's because it's executing within a transaction.
        // see https://www.baeldung.com/spring-data-jpa-save-saveandflush#saveAndFlush
        personRepo.saveAndFlush(p);
        logger.info("saved {}", p);

        // save a second person, but since we're not using within this transaction
        // just use save
        PersonData p2 = new PersonData();
        p2.setName("Henrietta Schmoop");
        personRepo.save(p2);

        // read.
        Optional<PersonData> readPerson = personRepo.findById(p.getPersonId());
        logger.info("read person {} ", readPerson.get());

        // read all data
        Iterable<PersonData> people = personRepo.findAll();
        StreamSupport.stream(people.spliterator(), false).forEach(person -> logger.info("found existing person {}", person));
    }

}
