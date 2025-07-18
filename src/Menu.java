import java.util.Scanner;

public class Menu {

    public static void menuInicial(){
        Login login = new Login();
        Scanner sc = new Scanner(System.in);
        String escolha = "0";
        System.out.print("\n[1] - Criar conta");
        System.out.print("\n[2] - Entrar na conta");
        System.out.print("\n[3] - Sair\n");

        System.out.print("\nInsira sua opção conforme os números acima: ");
        escolha = sc.nextLine();

        switch(escolha){
            case "1":{
                Cadastro.criarCpf();
                break;
            }

            case "2":{
                login.fazerLogin();
                break;
            }

            case "3":{
                System.out.print("\nEncerrando programa...");
                sc.close();
                System.exit(0);
                break;
            }

            default:{
                System.out.print("\nDigite uma opção válida!");
                menuInicial();
            }
        }
    }

    public static void menuPosLogin(){
        String escolha = "0";
        Banco banco = new Banco();
        Scanner sc = new Scanner(System.in);

        System.out.print("\n[1] - Depositar");
        System.out.print("\n[2] - Sacar");
        System.out.print("\n[3] - Transferir");
        System.out.print("\n[4] - Visualizar saldo");
        System.out.print("\n[5] - Visualizar dados pessoais");
        System.out.print("\n[6] - Atualizar dados pessoais");
        System.out.print("\n[7] - Excluir conta");
        System.out.print("\n[8] - Sair\n");

        System.out.print("\nInsira sua opção conforme os números acima: ");

        escolha = sc.nextLine();

        switch(escolha){
            case "1":{
                banco.depositar();
                break;
            }

            case "2":{
                banco.sacar();
                break;
            }

            case "3":{
                banco.transferir();
                break;
            }

            case "4":{
                banco.visualizarSaldo();
                break;
            }

            case "5":{
                banco.atualizarDados();
                break;
            }

            case "6":{
                banco.atualizarDados();
                break;
            }

            case "7":{
                banco.excluirConta();
                break;
            }

            case "8":{
                System.out.print("\nVoltando para o menu\n");
                menuInicial();
            }

            default:{
                System.out.print("\nInsira uma opção válida!");
                menuPosLogin();
            }
            sc.close();
        }
    }
}