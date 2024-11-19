package com.soares.notificacao.listener;

import com.soares.notificacao.constant.MensagemConstante;
import com.soares.notificacao.domain.Proposta;
import com.soares.notificacao.service.NotificacaoSNSService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaPendenteListener {

    @Autowired
    private NotificacaoSNSService notificacaoSNSService;

    @RabbitListener(queues = "proposta-pendente.ms-analise-credito")
    public void propostaPendente(Proposta proposta){
        String mensagem = String.format(MensagemConstante.PROPOSTA_EM_ANALISE, proposta.getUsuario().getNome());
        notificacaoSNSService.notificar(proposta.getUsuario().getTelefone(), mensagem);
    }
}
