/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.encryptedcolumntest.data.repos;

import com.blazartech.encryptedcolumntest.data.Gender;
import com.blazartech.encryptedcolumntest.data.PersonData;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author AAR1069
 */
@RepositoryRestResource(collectionResourceRel = "persons", path = "persons")
public interface PersonDataRepository extends JpaRepository<PersonData, Long>, JpaSpecificationExecutor<PersonData> {
    
    public Collection<PersonData> findByGender(Gender g);
}
