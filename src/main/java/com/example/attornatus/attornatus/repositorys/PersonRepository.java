package com.example.attornatus.attornatus.repositorys;

import com.example.attornatus.attornatus.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
