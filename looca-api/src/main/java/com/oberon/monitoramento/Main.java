package com.oberon.monitoramento;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MonitorRedeService monitor = new MonitorRedeService();

        LoggerOberon.registrar("Monitoramento de rede iniciado...");

        while (true) {
            monitor.coletarDadosRede();
            Thread.sleep(5000); // espera 5 segundos entre as leituras
        }
    }
}
