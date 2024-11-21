package com.soares.analisecredito.service.strategy.impl;

import com.soares.analisecredito.domain.Proposta;
import com.soares.analisecredito.service.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

@Component
public class RendaMaiorValorSolicitado implements CalculoPonto {
    @Override
    public int calcular(Proposta proposta) {
        return rendaMaiorValorSolicitado(proposta) ? 100 : 0;
    }

    private boolean rendaMaiorValorSolicitado(Proposta proposta){
        return  proposta.getUsuario().getRenda() > proposta.getValorSolicitado();
    }
}
