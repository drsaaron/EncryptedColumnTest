/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.encryptedcolumntest.data;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 *
 * @author AAR1069
 */
@Entity
@Data
@Table(name = "Person")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonData.findAll", query = "SELECT p FROM PersonData p")
    , @NamedQuery(name = "PersonData.findByPersonId", query = "SELECT p FROM PersonData p WHERE p.personId = :personId")
    , @NamedQuery(name = "PersonData.findByName", query = "SELECT p FROM PersonData p WHERE p.name = :name")})
public class PersonData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PersonId")
    private Long personId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "Name")
    @Convert(converter = CryptoConverterProxy.class) // see https://www.baeldung.com/jpa-attribute-converters
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Gender")
    @Convert(converter = GenderConverter.class)  // using the generic converter for some reason I have to explicitly assign
    private Gender gender;
    
}
