package com.soares.notificacao.listener;

import com.soares.notificacao.domain.Proposta;
import com.soares.notificacao.service.NotificacaoSNSService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class PropostaConcluidaListener {

    @Autowired
    private NotificacaoSNSService notificacaoSnsService;

    @RabbitListener(queues = "proposta-concluida.ms-proposta")
    public void propostaPendente(Proposta proposta) {
        String nome = proposta.getUsuario().getNome();

        String mensagem = proposta.getAprovada()
                ? String.format("MensagemConstante.PROPOSTA_APROVADA", nome)
                : (Objects.nonNull(proposta.getObservacao())
                ? String.format("MensagemConstante.PROPOSTA_NEGADA_POR_STRATEGY", nome, proposta.getObservacao())
                : String.format("MensagemConstante.PROPOSTA_NEGADA", nome));

        notificacaoSnsService.notificar(proposta.getUsuario().getTelefone(), mensagem);
    }
}
