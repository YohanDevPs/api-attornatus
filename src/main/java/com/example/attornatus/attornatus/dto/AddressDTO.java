package com.example.attornatus.attornatus.dto;

public class AddressDTO {

    private Long id;
    private String cep;
    private String street;
    private int number;
    private String city;
    private boolean mainAddress;

    public AddressDTO() {
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(boolean mainAddress) {
        this.mainAddress = mainAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressDTO that)) return false;

        if (getNumber() != that.getNumber()) return false;
        if (getCep() != null ? !getCep().equals(that.getCep()) : that.getCep() != null) return false;
        if (getStreet() != null ? !getStreet().equals(that.getStreet()) : that.getStreet() != null) return false;
        return getCity() != null ? getCity().equals(that.getCity()) : that.getCity() == null;
    }

    @Override
    public int hashCode() {
        int result = getCep() != null ? getCep().hashCode() : 0;
        result = 31 * result + (getStreet() != null ? getStreet().hashCode() : 0);
        result = 31 * result + getNumber();
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "cep='" + cep + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", city='" + city + '\'' +
                '}';
    }
}
