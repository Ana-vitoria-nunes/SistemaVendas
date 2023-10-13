package org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static Connection fazerConexao() {
        try {
            Connection conexao = DriverManager.getConnection("jdbc:postgresql://isabelle.db.elephantsql.com:5432/zkaavvok",
                    "zkaavvok", "0XA3cRnVL1GYiaVdk6NzmMZ_ynPEG5PQ");
            return conexao;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
