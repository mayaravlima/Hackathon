package com.postech.hackathon.repository;

import com.postech.hackathon.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByCpf(String cpf);

    boolean existsByPassportNumber(String passportNumber);

    boolean existsByEmail(String email);
}
