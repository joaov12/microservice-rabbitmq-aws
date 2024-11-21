package com.soares.analisecredito.service;

import com.soares.analisecredito.domain.Proposta;
import com.soares.analisecredito.exceptions.StrategyException;
import com.soares.analisecredito.service.strategy.CalculoPonto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnaliseCreditoService {


    @Autowired
    private List<CalculoPonto> calculoPontoList;

    @Autowired
    private NotificacaoRabbitService notificacaoRabbitService;

    private String exchangePropostaConcluida = "proposta-concluida.ex";

    public AnaliseCreditoService(List<CalculoPonto> calculoPontoList) {
        this.calculoPontoList = calculoPontoList;
    }

    public void analisar(Proposta proposta){
        try {
            int pontos = calculoPontoList.stream().mapToInt(impl -> impl.calcular(proposta)).sum();
            proposta.setAprovada(pontos > 350);
        } catch (StrategyException e) {
            proposta.setAprovada(false);
            proposta.setObservacao(e.getMessage());

        }
        notificacaoRabbitService.notificar(exchangePropostaConcluida, proposta);
    }
}
