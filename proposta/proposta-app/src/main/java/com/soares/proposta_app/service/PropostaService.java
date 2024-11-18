package com.soares.proposta_app.service;

import com.soares.proposta_app.dto.PropostaRequestDto;
import com.soares.proposta_app.dto.PropostaResponseDto;
import com.soares.proposta_app.entity.Proposta;
import com.soares.proposta_app.mapper.PropostaMapper;
import com.soares.proposta_app.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private NotificacaoRabbitService notificacaoService;


    public PropostaResponseDto criar(PropostaRequestDto requestDto){
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDto);
        propostaRepository.save(proposta);

        notificarRabbitMQ(proposta);

        return PropostaMapper.INSTANCE.convertEntityToDto(proposta);
    }

    public List<PropostaResponseDto> obterProposta() {
        return PropostaMapper.INSTANCE.convertListEntityToListDto(propostaRepository.findAll());
    }

    private void notificarRabbitMQ(Proposta proposta){
        try{
            notificacaoService.notificar(proposta, "proposta-pendente.ex");
        } catch (RuntimeException e) {
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }

    }
}
