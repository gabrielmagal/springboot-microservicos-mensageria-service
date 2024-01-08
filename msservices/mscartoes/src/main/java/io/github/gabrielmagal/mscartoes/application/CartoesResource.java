package io.github.gabrielmagal.mscartoes.application;

import io.github.gabrielmagal.mscartoes.application.representation.CartaoSaveRequest;
import io.github.gabrielmagal.mscartoes.application.representation.CartoesPorClienteResponse;
import io.github.gabrielmagal.mscartoes.domain.Cartao;
import io.github.gabrielmagal.mscartoes.domain.ClienteCartao;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {
    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status() {
        return "Ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest cartaoSaveRequest) {
        Cartao cartao = cartaoSaveRequest.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<Cartao> cartaoList = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(cartaoList);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf) {
        List<ClienteCartao> clienteCartaoList = clienteCartaoService.listCartoesByCpf(cpf);
        List<CartoesPorClienteResponse> cartoesPorClienteResponseList = clienteCartaoList.stream().map(CartoesPorClienteResponse::fromModel).toList();
        return ResponseEntity.ok(cartoesPorClienteResponseList);
    }

}
