package io.github.gabrielmagal.mscartoes.infra.repository;

import io.github.gabrielmagal.mscartoes.domain.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteCartaoRepositoryImpl extends JpaRepository<ClienteCartao, Long>  {
    List<ClienteCartao> findByCpf(String cpf);
}
