package io.github.gabrielmagal.mscartoes.application.representation;

import io.github.gabrielmagal.mscartoes.domain.BandeiraCartao;
import io.github.gabrielmagal.mscartoes.domain.Cartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {
    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel() {
        return new Cartao(nome, bandeira, renda, limite);
    }
}
