package com.oberon.monitoramento;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorRedeService {

    private final Looca looca = new Looca();
    private final RegistroRedeDAO dao = new RegistroRedeDAO();

    // Guarda o histórico da última leitura de bytes (para calcular o delta)
    private final Map<String, Long[]> historicoBytes = new HashMap<>();

    public void coletarDadosRede() {
        List<RedeInterface> interfaces = looca.getRede().getGrupoDeInterfaces().getInterfaces();

        for (RedeInterface rede : interfaces) {
            if (rede.getBytesRecebidos() > 0 || rede.getBytesEnviados() > 0) {
                String nome = rede.getNome();
                long bytesRecebidos = rede.getBytesRecebidos();
                long bytesEnviados = rede.getBytesEnviados();

                // Velocidade fixa (100 Mbps)
                long velocidadeBits = 100_000_000;

                if (historicoBytes.containsKey(nome)) {
                    Long[] anteriores = historicoBytes.get(nome);
                    long deltaRecebidos = bytesRecebidos - anteriores[0];
                    long deltaEnviados = bytesEnviados - anteriores[1];

                    // Taxa total em Mbps considerando 5s de intervalo
                    double taxaMbps = ((deltaRecebidos + deltaEnviados) * 8.0) / (5 * 1_000_000.0);
                    double usoPercentual = (taxaMbps / (velocidadeBits / 1_000_000.0)) * 100.0;

                    if (usoPercentual < 0) usoPercentual = 0;
                    if (usoPercentual > 100) usoPercentual = 100;

                    // Salva o registro no banco
                    RegistroRede registro = new RegistroRede(usoPercentual, LocalDateTime.now());
                    dao.salvar(registro);

                    LoggerOberon.registrar(String.format("""
                            Interface: %s
                            IP: %s
                            Uso de Rede: %.2f %%
                            ---------------------------
                            """,
                            nome,
                            rede.getEnderecoIpv4(),
                            usoPercentual
                    ));
                }

                // Atualiza o histórico da interface
                historicoBytes.put(nome, new Long[]{bytesRecebidos, bytesEnviados});
            }
        }
    }
}
