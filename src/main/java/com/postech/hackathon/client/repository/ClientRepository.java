package com.postech.hackathon.client.repository;

import com.postech.hackathon.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByCpf(String cpf);

    boolean existsByPassportNumber(String passportNumber);

    boolean existsByEmail(String email);
}
