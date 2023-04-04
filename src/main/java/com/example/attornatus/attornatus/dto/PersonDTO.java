package com.example.attornatus.attornatus.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;

public class PersonDTO implements Serializable {

    private Long id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDay;

    public PersonDTO() {
    }

    public PersonDTO(String name, LocalDate birthDay) {
        this.name = name;
        this.birthDay = birthDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDTO personDTO)) return false;

        if (getId() != null ? !getId().equals(personDTO.getId()) : personDTO.getId() != null) return false;
        if (getName() != null ? !getName().equals(personDTO.getName()) : personDTO.getName() != null) return false;
        return getBirthDay() != null ? getBirthDay().equals(personDTO.getBirthDay()) : personDTO.getBirthDay() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getBirthDay() != null ? getBirthDay().hashCode() : 0);
        return result;
    }
}
