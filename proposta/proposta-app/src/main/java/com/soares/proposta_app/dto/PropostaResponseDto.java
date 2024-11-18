package com.soares.proposta_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropostaResponseDto {

    private Long id;

    private String nome;

    private String sobrenome;

    private String telefone;

    private String cpf;

    private String renda;

    private String valorSolicitadoFmt;

    private String prazoPagamento;

    private Boolean aprovada;

    private String observacao;

    public Long getId() {
        return id;
    }
}
