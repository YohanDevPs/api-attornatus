package com.example.attornatus.attornatus.repositorys;

import com.example.attornatus.attornatus.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    List<Address> findByPersonId(Long id);
}
