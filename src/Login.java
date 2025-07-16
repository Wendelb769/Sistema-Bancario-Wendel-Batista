import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    Scanner sc = new Scanner(System.in);

    private String cpfLogin;
    private String senhaLogin;

    public String getCpfLogin(){
        return cpfLogin;
    }

    public void setCpfLogin(String cpfLogin){
        this.cpfLogin = cpfLogin;
    }

    public String getSenhaLogin(){
        return senhaLogin;
    }

    public void setSenhaLogin(String senhaLogin){
        this.senhaLogin = senhaLogin;
    }
    

    public void fazerLogin(){

        try{
            Connection conn = DriverManager.getConnection(ConexaoMySQL.url, ConexaoMySQL.usuario, ConexaoMySQL.senha);
            String sql = "SELECT * FROM usuario WHERE cpf = ? AND senha = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            System.out.print("\nInsira seu CPF: ");
            cpfLogin = sc.nextLine(); 
            ps.setString(1, cpfLogin);
            
            System.out.print("\nInsira sua senha: ");
            senhaLogin = sc.nextLine();
            ps.setString(2, senhaLogin);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                SessãoLogin.setCpf(cpfLogin);
                SessãoLogin.setSenha(senhaLogin);

                Menu.menuPosLogin();

            } else{
                System.out.print("\nCPF ou senha inválidos!\n");
                Menu.menuInicial();
            }

            conn.close();
            ps.close();
            
        } catch(SQLException e){
            e.printStackTrace();
        }
        
        sc.close();
    }
}
