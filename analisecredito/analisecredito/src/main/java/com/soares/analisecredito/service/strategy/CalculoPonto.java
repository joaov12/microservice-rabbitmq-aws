package com.soares.analisecredito.service.strategy;

import com.soares.analisecredito.domain.Proposta;

public interface CalculoPonto {
    int calcular(Proposta proposta);
}
