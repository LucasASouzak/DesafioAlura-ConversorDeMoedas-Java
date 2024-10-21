## Conversor de Moedas com Log: Um Projeto para Estudantes

Este projeto demonstra a construção de um conversor de moedas em Java, ideal para estudantes que desejam aprender sobre APIs, manipulação de JSON, tratamento de exceções e boas práticas de programação.

## Funcionalidades e Arquitetura (como na resposta anterior)


## Implementação com Exemplos e Aprendizados

### 1. `Conversor.java` (Interação com o Usuário)

```java
// ... imports

public class Conversor {
    public static void main(String[] args) throws IOException, InterruptedException {
        // ... (código do menu e leitura da opção)

            if (opcao != 0) {
                double valor;
                try {
                    // Lê o valor a converter do usuário.
                    // Aprendizado: Uso de try-catch para tratar InputMismatchException, 
                    // garantindo que o programa não crashe se o usuário digitar algo não numérico.
                    valor = leitor.nextDouble();

                    // Validação de entrada:
                    if (valor <= 0) { /* ... */ }

                } catch (InputMismatchException e) { /* ... */ }

                // ... (código para definir moedasOrigem, moedaDestino, etc. baseado na 'opcao')

                try {
                    // Chama a API para realizar a conversão:
                    double resultado = ApiClient.converter(moedaOrigem, moedaDestino, valor);
                    // Exibe o resultado:
                    System.out.printf("%.2f %s equivalem a %.2f %s%n", valor, nomeMoedaOrigem, resultado, nomeMoedaDestino);

                } catch (Exception e) { // Trata qualquer exceção que possa ocorrer durante a conversão.
                    System.err.println("Erro na conversão: " + e.getMessage());
                }
            }
        } while (opcao != 0); // Continua até o usuário digitar 0.
    }
}
```

**Aprendizados:**

* **Tratamento de Exceções:** O uso de `try-catch` é crucial para lidar com possíveis erros de entrada do usuário (`InputMismatchException`) e erros durante a conversão.
* **Loop `do-while`:** Permite repetir o processo de conversão até que o usuário decida sair.
* **`Scanner`:**  Classe útil para ler diferentes tipos de dados da entrada do usuário.


### 2. `ApiClient.java` (Comunicação com a API e Conversão)

```java
// ... imports

public class ApiClient {
    // ... (Constantes e variáveis)

    private static void atualizarTaxasDeCambio() throws IOException, InterruptedException {
        // ... (código para construir a URL da API)

        // Cria o cliente HTTP:
        HttpClient cliente = HttpClient.newHttpClient();
        // Cria a requisição:
        HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(url)).build();

        // Envia a requisição e recebe a resposta:
        // Aprendizado: Uso da API java.net.http para realizar requisições HTTP.
        HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

        // Desserializa o JSON da resposta:
        // Aprendizado: Uso do Gson para converter JSON em objetos Java.
        Gson gson = new Gson();
        taxasDeCambio = gson.fromJson(resposta.body(), TaxasDeCambio.class);
        // ...
    }

    public static double converter(String moedaOrigem, String moedaDestino, double valor) throws IOException, InterruptedException {
        // ... (verifica o cache e atualiza as taxas se necessário)

        // Obtém as taxas de conversão do Map:
        Double taxaOrigem = taxasDeCambio.taxas.get(moedaOrigem);
        Double taxaDestino = taxasDeCambio.taxas.get(moedaDestino);

        // ... (tratamento de erros se moedas inválidas)

        // Realiza a conversão:
        double resultado = valor * (taxaDestino / taxaOrigem);

        // Registra a conversão:
        Dados.logConversor(valor, moedaOrigem, resultado, moedaDestino);
        return resultado;
    }
}
```

**Aprendizados:**

* **Requisições HTTP:** Uso da API `java.net.http` para se comunicar com APIs externas.
* **Desserialização JSON:** Uso do Gson para converter a resposta JSON da API em objetos Java.
* **Cache:** Implementação de um cache simples para reduzir o número de chamadas à API.
* **`Map`:** Uso de `Map` para armazenar e acessar as taxas de câmbio de forma eficiente.

### 3. `Dados.java` (Registro das Conversões)

```java
// ... imports

public class Dados {
    // ... (constantes)


    public static synchronized void logConversor( /* ... */ ) { // Método synchronized para thread safety
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARQUIVO_CONVERSOES, true))) { // true para adicionar ao arquivo
            // Formata a linha de log:
            String linhaSalva = String.format( /* ... */ ); 
            // Escreve a linha no arquivo:
            escritor.println(linhaSalva);

        } catch (IOException e) { /* ... */ } // Tratamento de exceções de IO
    }
}
```

**Aprendizados:**

* **Escrita em Arquivo:** Uso de `FileWriter` e `PrintWriter` para escrever dados em um arquivo texto.
* **`try-with-resources`:** Garante que os recursos (arquivo) sejam fechados corretamente, mesmo em caso de exceções.
* **`synchronized`:**  Usado para garantir que apenas uma thread acesse o método de log por vez, prevenindo problemas de concorrência.
* **Formatação de Data/Hora:** Uso de `DateTimeFormatter` para formatar a data e hora no log.




Este exemplo comentado e expandido deve auxiliar no aprendizado dos conceitos envolvidos na construção deste projeto.  Lembre-se de que este é um exemplo didático e pode ser expandido e melhorado.


