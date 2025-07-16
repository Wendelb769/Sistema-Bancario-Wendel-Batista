import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

    static String url = "jdbc:mysql://localhost:3306/banco";
    static String usuario = "root"; 
    static String senha = "root"; 
    
    public static void conectar() {
        try {
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("\nConexão realizada com sucesso!");
            conexao.close();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco: " + e.getMessage());
        }
    }
}
