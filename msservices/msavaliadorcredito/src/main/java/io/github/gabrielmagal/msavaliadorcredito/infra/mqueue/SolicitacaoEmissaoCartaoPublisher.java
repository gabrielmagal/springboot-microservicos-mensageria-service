package io.github.gabrielmagal.msavaliadorcredito.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gabrielmagal.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartaoPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queueEmissaoCartoes;

    public void solicitarCartao(DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), convertIntToJson(dadosSolicitacaoEmissaoCartao));
    }

    private String convertIntToJson(DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(dadosSolicitacaoEmissaoCartao);
    }
}
