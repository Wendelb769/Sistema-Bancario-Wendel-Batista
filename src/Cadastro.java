import java.util.Scanner;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Cadastro {
    static Scanner sc = new Scanner(System.in);
    static Usuario usuario = new Usuario();
    static ContaBancária cb = new ContaBancária();
    
    public static void criarCpf(){
        String escolha = "0";
        System.out.print("\nInforme seu CPF, (apenas números): ");
        usuario.setCpf(sc.nextLine());

        if (!usuario.getCpf().matches("\\d{11}")){
            System.out.print("\nInsira exatamente os 11 digitos do seu CPF, conforme o exemplo a seguir: '11111111111' ");
            System.out.print("\n1 - Criar conta");
            System.out.print("\n2 - Sair");
            System.out.print("\nInsira o que deseja fazer: ");

           escolha = sc.nextLine();
        
            if (escolha.equals("1")){
                criarCpf();

            } else if(escolha.equals("2")){
                System.out.print("\nPrograma encerrado.");
                System.exit(0);

            } else{
                System.out.print("\nEssa opção não existe, portanto o programa será encerrado.");
                System.exit(0);
            }
        } else{
            criarSenha();
        }
    }

    public static void criarSenha(){
        String escolha = "0";
        System.out.print("\nCrie uma SENHA de exatamente 8 digitos que será utilizada para acessar sua conta (letras ou números): ");

        usuario.setSenha(sc.nextLine());

        if(usuario.getSenha().length() != 8){
            System.out.print("\nInsira exatamente 8 digitos para sua senha, conforme o exemplo a seguir: 'abcdfghi' ou '12345678'");
            System.out.print("\n1 - Criar senha");
            System.out.print("\n2 - Sair");
            System.out.print("\nDigite o que deseja fazer: ");

            escolha = sc.nextLine();

            if (escolha.equals("1")){
                criarSenha();

            } else if(escolha.equals("2")){
                System.out.print("\nPrograma encerrado.");
                System.exit(0);

            } else{
                System.out.print("\nEssa opção não existe, portanto o programa será encerrado.");
                System.exit(0);
            }
        } else{
            criarRg();
        }
    }

    public static void criarRg(){
        String escolha = "0";
        System.out.print("\nInforme seu RG com exatamente 10 digitos, (apenas números): ");

        usuario.setRg(sc.nextLine());

        if(!usuario.getRg().matches("\\d{10}")){
            System.out.print("\nInsira exatamente os 10 digitos do seu RG, conforme o exemplo a seguir: '0123456789'");
            System.out.print("\n1 - Inserir RG");
            System.out.print("\n2 - Sair");
            System.out.print("\nDigite o que deseja fazer: ");

            escolha = sc.nextLine();

            if (escolha.equals("1")){
                criarRg();

            } else if(escolha.equals("2")){
                System.out.print("\nPrograma encerrado.");
                System.exit(0);

            } else{
                System.out.print("\nEssa opção não existe, portanto o programa será encerrado.");
                System.exit(0);
            }

        } else {
            criarNomeCompleto();
        }
    }

    public static void criarNomeCompleto(){
        String escolha = "0";
        System.out.print("\nInforme seu nome completo: ");
        usuario.setNomeCompleto(sc.nextLine());

        if(!usuario.getNomeCompleto().matches("[a-zA-Z ]+")){
            System.out.print("\nInsira seu nome completo, sem números!");
            System.out.print("\n1 - Inserir nome completo");
            System.out.print("\n2 - Sair");
            System.out.print("\nDigite o que deseja fazer: ");

            escolha = sc.nextLine();

            if (escolha.equals("1")){
                criarNomeCompleto();

            } else if(escolha.equals("2")){
                System.out.print("\nPrograma encerrado.");
                System.exit(0);

            } else{
                System.out.print("\nEssa opção não existe, portanto o programa será encerrado.");
                System.exit(0);
            }

        } else {
            criarDataNascimento();
        }
    }

    public static void criarDataNascimento(){
        String escolha = "0";
        System.out.print("\nInsira sua data de nascimento: ");
        usuario.setDataNascimento(sc.nextLine());

        if (!usuario.getDataNascimento().matches("\\d{2}/\\d{2}/\\d{4}")){
            System.out.print("\nInsira a data de nascimento de acordo com esse exemplo: DD/MM/AAAA");

            System.out.print("\n1 - Inserir a data de nascimento");
            System.out.print("\n2 - Sair");
            System.out.print("\nDigite o que deseja fazer: ");

            escolha = sc.nextLine();

            if (escolha.equals("1")){
                criarDataNascimento();

            } else if(escolha.equals("2")){
                System.out.print("\nPrograma encerrado.");
                System.exit(0);

            } else{
                System.out.print("\nEssa opção não existe, portanto o programa será encerrado.");
                System.exit(0);
            }

        } else {
            criarNumero();
        }
    }

    public static void criarNumero(){
        String escolha = "0";
        System.out.print("\nInsira o seu número de celular com 11 digitos (DDD incluído): ");
        usuario.setNumero(sc.nextLine());

        if (!usuario.getNumero().matches("\\d{11}")){
            System.out.print("\nInsira seu número de telefone conforme o exemplo: '71912345678' (com um total de 11 digitos incluindo o DDD)");
            System.out.print("\n1 - Inserir o número do celular");
            System.out.print("\n2 - Sair");
            System.out.print("\nDigite o que deseja fazer: ");

            escolha = sc.nextLine();

            if (escolha.equals("1")){
                criarNumero();

            } else if(escolha.equals("2")){
                System.out.print("\nPrograma encerrado.");
                System.exit(0);

            } else{
                System.out.print("\nEssa opção não existe, portanto o programa será encerrado.");
                System.exit(0);
            }

        } else {
            cadastroUsuario();
        }
    }

    public static void cadastroUsuario(){
        try{
            Connection conn = DriverManager.getConnection(ConexaoMySQL.url, ConexaoMySQL.usuario, ConexaoMySQL.senha);

            String sql;

            sql = "INSERT INTO usuario (cpf, senha, rg, nomeCompleto, dataNascimento, numero) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, usuario.getCpf());
            ps.setString(2, usuario.getSenha());
            ps.setString(3, usuario.getRg());
            ps.setString(4, usuario.getNomeCompleto());
            ps.setString(5, usuario.getDataNascimento());
            ps.setString(6, usuario.getNumero());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()){
                cb.setId(rs.getInt(1));

            } else{
                throw new SQLException("\nErro ao obter ID do usuário!");
            }

            rs.close();
            conn.close();
            ps.close();

            cadastroContaBancaria();

        } catch(SQLException e){
            if(e.getMessage().contains("Duplicate entry") && e.getMessage().contains("cpf")){
                System.out.println("\nExiste uma conta cadastrada com esse CPF!\n");
                Menu.menuInicial();

            } else{System.out.println("\nErro ao cadastrar conta: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void cadastroContaBancaria(){
        try{
            Connection conn = DriverManager.getConnection(ConexaoMySQL.url, ConexaoMySQL.usuario, ConexaoMySQL.senha);

            String sql;

            sql = "INSERT INTO contaBancaria (id_usuario, saldo) VALUES (?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, cb.getId());
            ps.setBigDecimal(2, new BigDecimal("0.00"));

            ps.executeUpdate();

            System.out.println("\nConta Bancaria cadastrada com sucesso!");

            conn.close();
            ps.close();

            Menu.menuInicial();

        } catch(SQLException e){
            System.out.println("\nErro ao cadastrar conta: " + e.getMessage());
            e.printStackTrace();
        }
    }
}