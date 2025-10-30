package com.oberon.monitoramento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroRedeDAO {

    public void salvar(RegistroRede registro) {
        String sql = "INSERT INTO Registro (valor, Componente) VALUES (?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, registro.getUsoRedePorcentagem());
            stmt.setInt(2, 4);

            stmt.executeUpdate();

            LoggerOberon.registrar("[Banco] Registro de uso de rede salvo com sucesso!");

        } catch (SQLException e) {
            LoggerOberon.registrar("Erro ao salvar registro no banco: " + e.getMessage());
        }
    }
}
