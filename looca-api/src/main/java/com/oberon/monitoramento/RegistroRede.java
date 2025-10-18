package com.oberon.monitoramento;

import java.time.LocalDateTime;

public class RegistroRede {
    private Double usoRedePorcentagem;
    private LocalDateTime dataHora;

    public RegistroRede(Double usoRedePorcentagem, LocalDateTime dataHora) {
        this.usoRedePorcentagem = usoRedePorcentagem;
        this.dataHora = dataHora;
    }

    public Double getUsoRedePorcentagem() {
        return usoRedePorcentagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
