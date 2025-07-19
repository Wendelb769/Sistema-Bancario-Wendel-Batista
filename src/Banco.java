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
    Usuario usuario = new Usuario();
    Scanner sc = new Scanner(System.in);

    public void depositar(){
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
        System.out.print("Em andamento...");
        Menu.menuPosLogin();

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
        try{
            Connection conn = DriverManager.getConnection(ConexaoMySQL.url, ConexaoMySQL.usuario, ConexaoMySQL.senha);
            String sql = "SELECT cpf, senha, rg, nomeCompleto, dataNascimento, numero FROM usuario WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            cb.buscarIdPorCpf();

            ps.setInt(1, cb.getId());

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                System.out.print("\nDados pessoais do usuário\n");
                System.out.print("\nNome: " + rs.getString("nomeCompleto"));
                System.out.print("\nCPF: " + rs.getString("cpf"));
                System.out.print("\nSenha: " + rs.getString("senha"));
                System.out.print("\nRG: " + rs.getString("rg"));
                System.out.print("\nData de nascimento: " + rs.getString("dataNascimento"));
                System.out.print("\nNúmero: " + rs.getString("numero") + "\n");
                Menu.menuPosLogin();

            } else{
                System.out.print("\nDados pessoais não encontrados.");
                Menu.menuPosLogin();
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void atualizarDados(){
        try {
            Connection conn = DriverManager.getConnection(ConexaoMySQL.url, ConexaoMySQL.usuario, ConexaoMySQL.senha);
            String sqlNome = "UPDATE usuario SET nomeCompleto = ? WHERE id = ?";
            String sqlSenha = "UPDATE usuario SET senha = ? WHERE id = ?";
            String sqlRg = "UPDATE usuario SET rg = ? WHERE id = ?";
            String sqlDataNascimento = "UPDATE usuario SET dataNascimento = ? WHERE id = ?";
            String sqlNumero = "UPDATE usuario SET numero = ? WHERE id = ?";
            
            PreparedStatement psNome = conn.prepareStatement(sqlNome);
            PreparedStatement psSenha = conn.prepareStatement(sqlSenha);
            PreparedStatement psRg = conn.prepareStatement(sqlRg);
            PreparedStatement psDataNacimento = conn.prepareStatement(sqlDataNascimento);
            PreparedStatement psNumero = conn.prepareStatement(sqlNumero);

            cb.buscarIdPorCpf();

            String escolha = "0";
            System.out.print("\n[1] - Alterar o nome");
            System.out.print("\n[2] - Alterar a senha");
            System.out.print("\n[3] - Alterar o RG");
            System.out.print("\n[4] - Alterar a data de nascimento");
            System.out.print("\n[5] - Alterar o número");
            System.out.print("\n[6] - Voltar\n");

            System.out.print("\nInsira sua opção conforme os números acima: ");

            escolha = sc.nextLine();

            switch (escolha) {
                case "1":

                    System.out.print("\nInforme seu nome completo: ");
                    usuario.setNomeCompleto(sc.nextLine());

                    if(!usuario.getNomeCompleto().matches("[a-zA-Z ]+")){
                        System.out.print("\nInsira seu nome completo, sem números!");
                        System.out.print("\n1 - Alterar dados pessoais");
                        System.out.print("\n2 - Sair");
                        System.out.print("\nDigite o que deseja fazer: ");

                        escolha = sc.nextLine();

                        if (escolha.equals("1")){
                            atualizarDados();

                        } else if(escolha.equals("2")){
                            Menu.menuPosLogin();

                        } else{
                            System.out.print("\nEssa opção não existe, portanto o programa será encerrado.");
                            System.exit(0);
                        }

                    } else {
                        
                        psNome.setString(1, usuario.getNomeCompleto());
                        psNome.setInt(2, cb.getId());

                        psNome.executeUpdate();

                        System.out.println("\nSeu nome foi alterado com sucesso.");
                        atualizarDados();
                    }
                    
                    break;

                case "2":

                    System.out.print("\nCrie uma SENHA de exatamente 8 digitos que será utilizada para acessar sua conta (letras ou números): ");
                    usuario.setSenha(sc.nextLine());

                    if(usuario.getSenha().length() != 8){
                        System.out.print("\nInsira exatamente 8 digitos para sua senha, conforme o exemplo a seguir: 'abcdfghi' ou '12345678'");
                        System.out.print("\n1 - Alterar dados pessoais");
                        System.out.print("\n2 - Sair");
                        System.out.print("\nDigite o que deseja fazer: ");
                        escolha = sc.nextLine();

                        if (escolha.equals("1")){
                            atualizarDados();

                        } else if(escolha.equals("2")){
                            Menu.menuPosLogin();

                        } else{
                            System.out.print("\nEssa opção não existe, portanto o programa será encerrado.");
                            System.exit(0);
                        }

                    } else {
                        
                        psSenha.setString(1, usuario.getSenha());
                        psSenha.setInt(2, cb.getId());

                        psSenha.executeUpdate();

                        SessãoLogin.setSenha(usuario.getSenha());

                        System.out.println("\nSua senha foi alterada com sucesso.");
                        atualizarDados();
                    }
                    
                    break;

                case "3":

                    System.out.print("\nInforme seu RG com exatamente 10 digitos, (apenas números): ");
                    usuario.setRg(sc.nextLine());

                    if(!usuario.getRg().matches("\\d{10}")){
                        System.out.print("\nInsira exatamente os 10 digitos do seu RG, conforme o exemplo a seguir: '0123456789'");
                        System.out.print("\n1 - Alterar dados pessoais");
                        System.out.print("\n2 - Sair");
                        System.out.print("\nDigite o que deseja fazer: ");
                        escolha = sc.nextLine();

                        if (escolha.equals("1")){
                            atualizarDados();

                        } else if(escolha.equals("2")){
                            Menu.menuPosLogin();

                        } else{
                            System.out.print("\nEssa opção não existe, portanto o programa será encerrado.");
                            System.exit(0);
                        }

                    } else {
                        
                        psRg.setString(1, usuario.getRg());
                        psRg.setInt(2, cb.getId());

                        psRg.executeUpdate();

                        System.out.println("\nSeu RG foi alterado com sucesso.");
                        atualizarDados();
                    }
                    
                    break;

                case "4":

                    System.out.print("\nInsira sua data de nascimento: ");
                    usuario.setDataNascimento(sc.nextLine());

                    if (!usuario.getDataNascimento().matches("\\d{2}/\\d{2}/\\d{4}")){
                        System.out.print("\nInsira a data de nascimento de acordo com esse exemplo: DD/MM/AAAA");

                        System.out.print("\n1 - Alterar dados pessoais");
                        System.out.print("\n2 - Sair");
                        System.out.print("\nDigite o que deseja fazer: ");
                        escolha = sc.nextLine();

                            if (escolha.equals("1")){
                                atualizarDados();

                            } else if(escolha.equals("2")){
                                Menu.menuPosLogin();

                            } else{
                                System.out.print("\nEssa opção não existe, portanto o programa será encerrado.");
                                System.exit(0);
                            }

                        } else {
                            
                            psDataNacimento.setString(1, usuario.getDataNascimento());
                            psDataNacimento.setInt(2, cb.getId());

                            psDataNacimento.executeUpdate();

                            System.out.println("\nSua data de nascimento foi alterada com sucesso.");
                            atualizarDados();
                        }

                        break;

                case "5":

                    System.out.print("\nInsira o seu número de celular com 11 digitos (DDD incluído): ");
                    usuario.setNumero(sc.nextLine());

                    if (!usuario.getNumero().matches("\\d{11}")){
                        System.out.print("\nInsira seu número de telefone conforme o exemplo: '71912345678' (com um total de 11 digitos incluindo o DDD)");
                        System.out.print("\n1 - Alterar dados pessoais");
                        System.out.print("\n2 - Sair");
                        System.out.print("\nDigite o que deseja fazer: ");

                        escolha = sc.nextLine();

                            if (escolha.equals("1")){
                                atualizarDados();

                            } else if(escolha.equals("2")){
                                Menu.menuPosLogin();

                            } else{
                                System.out.print("\nEssa opção não existe, portanto o programa será encerrado.");
                                System.exit(0);
                            }

                        } else {
                            
                            psNumero.setString(1, usuario.getNumero());
                            psNumero.setInt(2, cb.getId());

                            psNumero.executeUpdate();

                            System.out.println("\nSeu número foi alterada com sucesso.");
                            atualizarDados();
                        }

                        break;

                    case "6":
                        Menu.menuPosLogin();
                        break;
            
                default:
                    System.out.print("\nEssa opção não está disponível.\n");
                    atualizarDados();
                    break;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirConta(){

        String excluirCpf = "";
        String excluirSenha = "";
        try{
            Connection conn = DriverManager.getConnection(ConexaoMySQL.url, ConexaoMySQL.usuario, ConexaoMySQL.senha);
            String sql = "DELETE FROM usuario WHERE cpf = ? AND senha = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

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