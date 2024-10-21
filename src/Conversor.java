import Utilitario.ApiClient;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Conversor {

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner leitor = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("""
                ***********************************

                Conversor de moedas...
                Selecione a opção no menu Abaixo:
                1-Real para Dolar
                2-Dolar para Real
                3-Real para Euro
                4-Euro para Real
                5-Real para Libra
                6-Libra para Real
                7-Real Para Peso Argentino
                8-Peso Argentino para Real
                0-Sair

                ***********************************

                """);

            try {
                opcao = leitor.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Digite um número.");
                leitor.next(); // Limpa o buffer do scanner
                continue;
            }

            if (opcao != 0) {
                double valor;
                try {
                    System.out.println("Insira o valor:");
                    valor = leitor.nextDouble();

                    if (valor <= 0) {
                        System.out.println("Não é possível converter valores menores ou iguais a zero.");
                        continue;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Valor inválido. Digite um número.");
                    leitor.next(); // Limpa o buffer do scanner
                    continue;
                }

                String moedaOrigem = "";
                String moedaDestino = "";
                String nomeMoedaOrigem = "";
                String nomeMoedaDestino = "";

                switch (opcao) {
                    case 1 -> {
                        moedaOrigem = "BRL"; moedaDestino = "USD";
                        nomeMoedaOrigem = "reais"; nomeMoedaDestino = "dólares";
                    }
                    case 2 -> {
                        moedaOrigem = "USD"; moedaDestino = "BRL";
                        nomeMoedaOrigem = "dólares"; nomeMoedaDestino = "reais";
                    }
                    case 3 -> {
                        moedaOrigem = "BRL"; moedaDestino = "EUR";
                        nomeMoedaOrigem = "reais"; nomeMoedaDestino = "euros";
                    }
                    case 4 -> {
                        moedaOrigem = "EUR"; moedaDestino = "BRL";
                        nomeMoedaOrigem = "euros"; nomeMoedaDestino = "reais";
                    }
                    case 5 -> {
                        moedaOrigem = "BRL"; moedaDestino = "GBP";
                        nomeMoedaOrigem = "reais"; nomeMoedaDestino = "libras";
                    }
                    case 6 -> {
                        moedaOrigem = "GBP"; moedaDestino = "BRL";
                        nomeMoedaOrigem = "libras"; nomeMoedaDestino = "reais";
                    }
                    case 7 -> {
                        moedaOrigem = "BRL"; moedaDestino = "ARS";
                        nomeMoedaOrigem = "reais"; nomeMoedaDestino = "pesos argentinos";
                    }
                    case 8 -> {
                        moedaOrigem = "ARS"; moedaDestino = "BRL";
                        nomeMoedaOrigem = "pesos argentinos"; nomeMoedaDestino = "reais";
                    }
                    default -> {
                        System.out.println("Opção inválida!");
                        continue;
                    }
                }

                try {
                    double resultado = ApiClient.converter(moedaOrigem, moedaDestino, valor);
                    System.out.printf("%.2f %s equivalem a %.2f %s%n", valor, nomeMoedaOrigem, resultado, nomeMoedaDestino);
                } catch (Exception e) {
                    System.err.println("Erro na conversão: " + e.getMessage());
                }
            }

        } while (opcao != 0);

        System.out.println("Saindo...");
        leitor.close();
    }
}