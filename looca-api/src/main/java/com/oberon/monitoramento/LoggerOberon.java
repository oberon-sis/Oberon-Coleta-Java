package com.oberon.monitoramento;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerOberon {

    private static final String CAMINHO_LOG = "log_oberon.txt";

    public static void registrar(String mensagem) {
        String dataHora = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String linha = "[" + dataHora + "] " + mensagem;

        System.out.println(linha); // 👈 Mostra no console

        try (FileWriter writer = new FileWriter(CAMINHO_LOG, true)) {
            writer.write(linha + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao gravar log: " + e.getMessage());
        }
    }

}
