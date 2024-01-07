package io.github.gabrielmagal.mscartoes.infra.mqueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gabrielmagal.mscartoes.domain.Cartao;
import io.github.gabrielmagal.mscartoes.domain.ClienteCartao;
import io.github.gabrielmagal.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import io.github.gabrielmagal.mscartoes.infra.repository.CartaoRepositoryImpl;
import io.github.gabrielmagal.mscartoes.infra.repository.ClienteCartaoRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubscriber {
    private static final Logger log = LoggerFactory.getLogger(EmissaoCartaoSubscriber.class);
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
            log.error("Erro ao receber solicitação de emissão de cartão: {}", e.getMessage());
        }
    }
}
