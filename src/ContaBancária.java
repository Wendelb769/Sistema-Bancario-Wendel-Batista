import java.sql.SQLException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ContaBancária {

    private BigDecimal saldo;
    private int id;

    Login login = new Login();

    public void buscarIdPorCpf() throws SQLException{
        
        try{
            Connection conn = DriverManager.getConnection(ConexaoMySQL.url, ConexaoMySQL.usuario, ConexaoMySQL.senha);
            String sql = "SELECT id FROM usuario WHERE cpf = ? AND senha = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, SessãoLogin.getCpf());
            ps.setString(2, SessãoLogin.getSenha());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                id = rs.getInt("id");
            } else{
                System.out.println("\nUsuário não encontrado!");
            }

            conn.close();
            ps.close();
            rs.close();
            
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void buscarSaldo(){
        try{
            Connection conn = DriverManager.getConnection(ConexaoMySQL.url, ConexaoMySQL.usuario, ConexaoMySQL.senha);
            String sql = "SELECT saldo FROM contaBancaria WHERE id_usuario = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, getId());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                this.saldo = rs.getBigDecimal("saldo");
            } else{
                this.saldo = BigDecimal.ZERO;
            }
            
            conn.close();
            ps.close();
            rs.close();
            
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public BigDecimal getSaldo(){
        return saldo;
    }

    public void setSaldo(BigDecimal saldo){
        this.saldo = saldo;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    
}
