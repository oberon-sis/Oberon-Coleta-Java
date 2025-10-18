package com.oberon.monitoramento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroRedeDAO {

    public void salvar(RegistroRede registro) {
        String sql = "INSERT INTO Registro (valor, horario, fkMaquinaComponente) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, registro.getUsoRedePorcentagem());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(registro.getDataHora()));

            // ⚙️ Ajuste o ID da interface de rede correspondente no banco
            stmt.setInt(3, 4);

            stmt.executeUpdate();

            LoggerOberon.registrar("[Banco] Registro de uso de rede salvo com sucesso!");

        } catch (SQLException e) {
            LoggerOberon.registrar("Erro ao salvar registro no banco: " + e.getMessage());
        }
    }
}
