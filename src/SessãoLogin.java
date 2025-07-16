public class SessãoLogin {

    // DADOS DE CPF E SENHA SÃO ARMAZENADOS SEMPRE AQUI !!
    
    private static String cpf;
    private static String senha;

    public static void setCpf(String cpfLogin) {
        cpf = cpfLogin;
    }

    public static void setSenha(String senhaLogin) {
        senha = senhaLogin;
    }

    public static String getCpf() {
        return cpf;
    }

    public static String getSenha() {
        return senha;
    }
}