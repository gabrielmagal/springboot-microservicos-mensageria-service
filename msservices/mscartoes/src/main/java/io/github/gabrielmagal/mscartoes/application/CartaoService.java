package io.github.gabrielmagal.mscartoes.application;

import io.github.gabrielmagal.mscartoes.domain.Cartao;
import io.github.gabrielmagal.mscartoes.infra.repository.CartaoRepositoryImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {
    private final CartaoRepositoryImpl cartaoRepositoryImpl;

    @Transactional
    public Cartao save(Cartao cartao) {
        return cartaoRepositoryImpl.save(cartao);
    }

    public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        return cartaoRepositoryImpl.findByRendaLessThanEqual(rendaBigDecimal);
    }
}
