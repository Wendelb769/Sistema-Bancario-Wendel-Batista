import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Banco {

    ContaBancária cb = new ContaBancária();

    public void depositar(){
        Scanner sc = new Scanner(System.in);
        BigDecimal deposito = null;

        try{
            cb.buscarIdPorCpf();
            cb.buscarSaldo();

            Connection conn = DriverManager.getConnection(ConexaoMySQL.url, ConexaoMySQL.usuario, ConexaoMySQL.senha);
            String sql = "UPDATE contaBancaria SET saldo = ? WHERE id_usuario = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            System.out.print("\nInforme a quantia em R$ que deseja depositar: ");

            try{
                deposito = sc.nextBigDecimal().setScale(2, RoundingMode.HALF_UP);

            } catch(InputMismatchException e){
                System.out.print("\nInsira um valor válido para depositar.\n");
                Menu.menuPosLogin();
            }

            if(deposito.compareTo(BigDecimal.ZERO) <= 0){
                System.out.print("\nInsira um valor de depósito maior que 0.\n");
                Menu.menuPosLogin();

            } else{
                cb.setSaldo(cb.getSaldo().add(deposito));

                ps.setBigDecimal(1, cb.getSaldo());
                ps.setInt(2, cb.getId());

                ps.executeUpdate();

                cb.buscarSaldo();

                System.out.print("\nO valor de R$ " + deposito + " foi depositado na conta com sucesso.\n");
                System.out.print("\nseu saldo atual: R$ " + cb.getSaldo() + "\n");
                Menu.menuPosLogin();

                sc.close();
                conn.close();
                ps.close();
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void sacar(){
        Scanner sc = new Scanner(System.in);
        BigDecimal valorSaque = null;

        try{
            cb.buscarIdPorCpf();
            cb.buscarSaldo();

            Connection conn = DriverManager.getConnection(ConexaoMySQL.url, ConexaoMySQL.usuario, ConexaoMySQL.senha);
            String sql = "UPDATE contaBancaria SET saldo = ? WHERE id_usuario = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            System.out.print("\nInsira em R$ quanto deseja sacar: ");

            try{
                valorSaque = sc.nextBigDecimal().setScale(2, RoundingMode.HALF_UP);

            } catch(InputMismatchException e){
                System.out.print("\nInsira um valor válido para sacar.");
                Menu.menuPosLogin();

            }

            if (valorSaque.compareTo(cb.getSaldo()) > 0){
            System.out.print("\nSaldo insuficiente.\n");
            Menu.menuPosLogin();

            } else if (valorSaque.compareTo(BigDecimal.ZERO) <= 0){
            System.out.print("\nNão é possível sacar esse valor\n");
            Menu.menuPosLogin();

            } else{
                cb.setSaldo(cb.getSaldo().subtract(valorSaque));

                ps.setBigDecimal(1, cb.getSaldo());
                ps.setInt(2, cb.getId());

                ps.executeUpdate();

                cb.buscarSaldo();

                System.out.print("\nO valor de R$ " + valorSaque + " foi sacado com sucesso.\n");
                System.out.print("\nSeu saldo atual é de: R$ " + cb.getSaldo() + "\n");
                Menu.menuPosLogin();

                ps.close();
                conn.close();
                sc.close();
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void transferir(){

    }

    public void visualizarSaldo(){
    
        try{
            cb.buscarIdPorCpf();

            Connection conn = DriverManager.getConnection(ConexaoMySQL.url, ConexaoMySQL.usuario, ConexaoMySQL.senha);
            String sql = "SELECT saldo FROM contaBancaria WHERE id_usuario = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, cb.getId());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                cb.setSaldo(rs.getBigDecimal("saldo"));
                System.out.print("\nSeu saldo atual é: " + "R$ " + cb.getSaldo() + "\n");
                Menu.menuPosLogin();
                
            } else{
                System.out.print("\nSaldo indisponivel.\n");
            }
            
            
        } catch(SQLException e){
            e.printStackTrace();
        }       
    }

    public void visualizarDadosPessoais(){
        
    }

    public void atualizarDados(){

    }

    public void excluirConta(){

        String excluirCpf = "";
        String excluirSenha = "";
        try{
            Connection conn = DriverManager.getConnection(ConexaoMySQL.url, ConexaoMySQL.usuario, ConexaoMySQL.senha);
            String sql = "DELETE FROM usuario WHERE cpf = ? AND senha = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            Scanner sc = new Scanner(System.in);
            System.out.print("\nTem certeza que deseja excluir a conta?");
            System.out.print("\n[1] - Sim");
            System.out.print("\n[2] - Não");
            System.out.print("\nSua escolha: ");

            String escolha = sc.nextLine();

            switch (escolha) {
                case "1":
                    System.out.print("\nInsira o cpf da conta para excluir: ");
                    excluirCpf = sc.nextLine();

                    System.out.print("\nInsira a senha da conta para excluir: ");
                    excluirSenha = sc.nextLine();

                    if(excluirCpf.equals(SessãoLogin.getCpf()) && excluirSenha.equals(SessãoLogin.getSenha())){
                        ps.setString(1, excluirCpf);
                        ps.setString(2, excluirSenha);

                        ps.executeUpdate();

                        ps.close();
                        conn.close();

                        System.out.print("\nConta deletada com sucesso.\n");
                        Menu.menuInicial();

                    } else{
                        System.out.print("\nO CPF ou senha da conta está incorreto, portanto não é possível excluir essa conta.\n");
                        Menu.menuPosLogin();
                    }
                    break;
                
                case "2":
                    Menu.menuPosLogin();
            
                default:
                    System.out.print("\nEssa opção não existe.\n");
                    excluirConta();
                    break;
            }
            sc.close();

        } catch(SQLException e){
            e.printStackTrace();
        }

    }
}
