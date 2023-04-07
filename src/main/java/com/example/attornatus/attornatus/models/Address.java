package com.example.attornatus.attornatus.models;

import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String CEP;

    @Column
    @NotBlank
    private String street;

    @NotBlank
    @Column
    @NotBlank
    private int number;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String city;

    @NotBlank
    @Column
    private boolean mainAddress;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Address() {
    }

    public Address(String CEP, String street, int number, String city) {
        this.CEP = CEP;
        this.street = street;
        this.number = number;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
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
        if (!(o instanceof Address address)) return false;

        if (getNumber() != address.getNumber()) return false;
        if (isMainAddress() != address.isMainAddress()) return false;
        if (getCEP() != null ? !getCEP().equals(address.getCEP()) : address.getCEP() != null) return false;
        if (getStreet() != null ? !getStreet().equals(address.getStreet()) : address.getStreet() != null) return false;
        if (getCity() != null ? !getCity().equals(address.getCity()) : address.getCity() != null) return false;
        return getPerson() != null ? getPerson().equals(address.getPerson()) : address.getPerson() == null;
    }

    @Override
    public int hashCode() {
        int result = getCEP() != null ? getCEP().hashCode() : 0;
        result = 31 * result + (getStreet() != null ? getStreet().hashCode() : 0);
        result = 31 * result + getNumber();
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (isMainAddress() ? 1 : 0);
        result = 31 * result + (getPerson() != null ? getPerson().hashCode() : 0);
        return result;
    }
}
