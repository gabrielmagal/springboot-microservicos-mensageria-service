package io.github.gabrielmagal.mscartoes.application;

import io.github.gabrielmagal.mscartoes.domain.ClienteCartao;
import io.github.gabrielmagal.mscartoes.infra.repository.ClienteCartaoRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {
    private final ClienteCartaoRepositoryImpl clienteCartaoRepository;

    public List<ClienteCartao> listCartoesByCpf(String cpf) {
        return clienteCartaoRepository.findByCpf(cpf);
    }
}
