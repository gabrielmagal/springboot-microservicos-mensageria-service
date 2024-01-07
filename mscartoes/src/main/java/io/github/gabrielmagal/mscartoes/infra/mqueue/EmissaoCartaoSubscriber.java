package io.github.gabrielmagal.mscartoes.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gabrielmagal.mscartoes.domain.Cartao;
import io.github.gabrielmagal.mscartoes.domain.ClienteCartao;
import io.github.gabrielmagal.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import io.github.gabrielmagal.mscartoes.infra.repository.CartaoRepositoryImpl;
import io.github.gabrielmagal.mscartoes.infra.repository.ClienteCartaoRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubscriber {
    private final CartaoRepositoryImpl cartaoRepositoryImpl;
    private final ClienteCartaoRepositoryImpl clienteCartaoRepositoryImpl;
    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")

    public void receberSolicitacaoEmissao(String payload) {
        try {
            DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao = new ObjectMapper().readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            Cartao cartao = cartaoRepositoryImpl.findById(dadosSolicitacaoEmissaoCartao.getIdCartao()).orElseThrow();
            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dadosSolicitacaoEmissaoCartao.getCpf());
            clienteCartao.setLimite(dadosSolicitacaoEmissaoCartao.getLimiteLiberado());

            clienteCartaoRepositoryImpl.save(clienteCartao);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
