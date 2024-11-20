package com.soares.analisecredito.listener;

import com.soares.analisecredito.domain.Proposta;
import com.soares.analisecredito.service.AnaliseCreditoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropostaEmAnaliseListener {

    @Autowired
    private AnaliseCreditoService analiseCreditoService;

    @RabbitListener(queues = "proposta-pendente.ms-analise-credito")
    public void propostaEmAnalise(Proposta proposta){
        analiseCreditoService.analisar(proposta);
    }
}
