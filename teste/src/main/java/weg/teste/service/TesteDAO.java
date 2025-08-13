// TesteDAO.java
package weg.teste.service;

import weg.teste.database.MySQLConnection;
import java.sql.*;

public class TesteDAO {

    public static void main(String[] args) {
        int totalLidos = 0;
        int totalEnviados = 0;
        try (Connection connOrigem = MySQLConnection.getConnection();
             Statement stmt = connOrigem.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM teste")) {

            while (rs.next()) {
                totalLidos++;
                String valor = rs.getString("teste");
                System.out.println("LIDO: " + valor);

                if ("ligado".equals(valor)) {
                    totalEnviados++;
                    if (Envio.enviarParaOutroBanco(valor)) {
                        System.out.println("INSERIU: " + valor);
                    } else {
                        System.out.println("FALHOU: " + valor);
                    }
                }
            }
            System.out.println("Total lidos: " + totalLidos);
            System.out.println("Total enviados: " + totalEnviados);
        } catch (SQLException e) {
            System.out.println("Erro ao consultar o banco de origem:");
            e.printStackTrace();
        }
    }

    public static class Envio {
        public static boolean enviarParaOutroBanco(String valor) {
            String url = "jdbc:mysql://mainline.proxy.rlwy.net:23089/railway";
            String user = "root";
            String password = "shfsDsPecINopjqzXmBiStpsknEjLlko";
            try (Connection connDestino = DriverManager.getConnection(url, user, password);
                 PreparedStatement ps = connDestino.prepareStatement("INSERT INTO teste_real (msg) VALUES (?)")) {
                System.out.println("Tentando inserir: " + valor);
                ps.setString(1, valor);
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println("Erro ao inserir no banco de destino: " + valor);
                e.printStackTrace();
                return false;
            }
        }
    }
}