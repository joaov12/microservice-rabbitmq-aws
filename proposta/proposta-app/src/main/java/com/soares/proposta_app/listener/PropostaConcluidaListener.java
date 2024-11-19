package com.soares.proposta_app.listener;

import com.soares.proposta_app.dto.PropostaResponseDto;
import com.soares.proposta_app.entity.Proposta;
import com.soares.proposta_app.mapper.PropostaMapper;
import com.soares.proposta_app.repository.PropostaRepository;
import com.soares.proposta_app.service.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private WebSocketService webSocketService;

    @RabbitListener(queues = "proposta-concluida.ms-proposta")
    public void propostaConcluida(Proposta proposta){
        propostaRepository.save(proposta);
        PropostaResponseDto responseDto = PropostaMapper.INSTANCE.convertEntityToDto(proposta);
        webSocketService.notificar(responseDto);
    }
}
