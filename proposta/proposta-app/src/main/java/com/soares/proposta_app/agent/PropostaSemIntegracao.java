package com.soares.proposta_app.agent;

import com.soares.proposta_app.entity.Proposta;
import com.soares.proposta_app.repository.PropostaRepository;
import com.soares.proposta_app.service.NotificacaoRabbitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PropostaSemIntegracao {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private NotificacaoRabbitService notificacaoRabbitService;

    private final Logger logger = LoggerFactory.getLogger(PropostaSemIntegracao.class);

    @Scheduled(fixedDelay = 45, timeUnit = TimeUnit.SECONDS)
    public void buscarPropostasSemIntegracao(){
        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try{
                notificacaoRabbitService.notificar(proposta, "proposta-pendente.ex");
                atualizarProposta(proposta);
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
            }
        });
    }
    public void atualizarProposta(Proposta proposta){
        proposta.setIntegrada(true);
        propostaRepository.save(proposta);
    }
}
