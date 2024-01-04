package io.github.gabrielmagal.msclientes.infra.repository;

import io.github.gabrielmagal.msclientes.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepositoryImpl extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCpf(String cpf);
}
