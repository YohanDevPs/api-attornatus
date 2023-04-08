package com.example.attornatus.attornatus.models;

import com.example.attornatus.attornatus.dto.AddressDTO;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String cep;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private String city;

    @Column
    private boolean mainAddress;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Person getPerson() {
        return person;
    }

    public boolean isMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(boolean mainAddress) {
        this.mainAddress = mainAddress;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressDTO)) return false;
        AddressDTO address = (AddressDTO) o;
        return number == address.getNumber() &&
                Objects.equals(cep, address.getCep()) &&
                Objects.equals(street, address.getStreet()) &&
                Objects.equals(city, address.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(cep, street, number, city);
    }
}
