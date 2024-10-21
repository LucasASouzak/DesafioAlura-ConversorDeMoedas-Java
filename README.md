## Conversor de Moedas com Log: #Alura e #Oracle 

Este projeto demonstra a construção de um conversor de moedas em Java, desafio de codigo Alura turma 7 ONE.

## Funcionalidades e Arquitetura 


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

**Exemplos do código em funcionamento**

**Menu**
![image](https://github.com/user-attachments/assets/aa126ba0-c1fb-4596-99fa-8876011adead)

**Opção**
![image](https://github.com/user-attachments/assets/0b29287d-6335-4bea-942a-ec8031225a53)

**Saindo**
![image](https://github.com/user-attachments/assets/f11b3032-60b2-48a8-b6a4-61612ae1c9f6)

**Log Conversões**
![image](https://github.com/user-attachments/assets/b0e74b5f-1910-4e69-ad19-33d728e7c918)


**Tutorial: Importando e Testando o Projeto Conversor de Moedas do GitHub**

Este tutorial demonstra como importar e testar o projeto Conversor de Moedas a partir de um repositório GitHub.

**Pré-requisitos:**

* **Git:** Certifique-se de ter o Git instalado e configurado em sua máquina.
* **Java Development Kit (JDK):**  Você precisará de um JDK instalado (versão 8 ou superior).
* [link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* **IDE Java (recomendado):**  Uma IDE como IntelliJ IDEA ou Eclipse facilitará a importação e execução do projeto.
* [link](https://www.jetbrains.com/edu-products/download/#section=idea)
* **Gson:** Baixe a biblioteca Gson (gson-*.jar) e adicione ao projeto
* [link](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.11.0)

**Passos:**

1. **Clonar o Repositório:**

   Abra o terminal ou prompt de comando e navegue até o diretório onde deseja clonar o projeto. Em seguida, execute o seguinte comando:

   ```bash
   git clone <URL_DO_REPOSITORIO>
   ```

   Substitua `<URL_DO_REPOSITORIO>` pela URL do seu repositório GitHub.  Por exemplo:

   ```bash
   git clone https://github.com/seu_usuario/conversor-moedas.git
   ```

2. **Importar o Projeto na IDE:**

   * **IntelliJ IDEA:**
      * Abra o IntelliJ IDEA e selecione "Open".
      * Navegue até o diretório onde você clonou o repositório e selecione o arquivo `.idea` (se existir) ou a pasta do projeto.
      * O IntelliJ IDEA importará o projeto automaticamente.

   * **Eclipse:**
      * Abra o Eclipse e selecione "File" > "Import...".
      * Escolha "Existing Projects into Workspace" e clique em "Next".
      * Selecione o diretório raiz do projeto clonado e clique em "Finish".

3. **Configurar o Gson:**
   * Baixe a biblioteca Gson do site oficial.
   * No IntelliJ IDEA, clique em  `File` > `Project Structure` > `Modules` > `Dependencies` > `+` > `JARs or directories...`. Selecione o arquivo Gson que você baixou e clique em `OK`.
   * No Eclipse, clique com o botão direito no projeto > `Build Path` > `Configure Build Path...` > `Libraries` > `Add External JARs...`. Selecione o arquivo Gson e clique em `Abrir`.

4. **Configurar a Chave da API:**

   Abra o arquivo `ApiClient.java` e localize a constante `CHAVE_API`. Substitua o valor atual pela sua chave de API da ExchangeRate-API:

   ```java
   private static final String CHAVE_API = "SUA_CHAVE_AQUI"; // Substitua pela sua chave  
    
   ```
 *Api utlizada:Exchange Rate API [link](https://www.exchangerate-api.com/)

5. **Executar o Projeto:**

   * **IntelliJ IDEA:**
      * Abra a classe `Conversor.java`.
      * Clique com o botão direito do mouse dentro da classe e selecione "Run 'Conversor.main()'".

   * **Eclipse:**
      * Abra a classe `Conversor.java`.
      * Clique com o botão direito do mouse no arquivo e selecione "Run As" > "Java Application".


6. **Testar o Conversor:**

   Após executar o projeto, o programa exibirá um menu no console. Siga as instruções para selecionar as moedas e inserir o valor que deseja converter.  O resultado da conversão será exibido no console, e o registro da conversão será salvo no arquivo `convertido.txt` no diretório do projeto.


**Exemplo de uso no console:**

```
***********************************

Conversor de moedas...
Selecione a opção no menu Abaixo:
1-Real para Dolar
// ... (outras opções)
0-Sair

***********************************

1
Insira o valor:
100
100,00 reais equivalem a 17,67 dólares
```

**Verificando o Log:**

Abra o arquivo `convertido.txt` no diretório do projeto para verificar se as conversões estão sendo registradas corretamente.  Cada linha do arquivo deve conter a data e hora da conversão, o valor original, a moeda de origem, o valor convertido e a moeda de destino.


Este tutorial fornece um guia passo a passo para importar, configurar e testar o projeto Conversor de Moedas. Com este guia, você poderá executar o projeto e começar a fazer conversões de moedas.  Lembre-se de que este tutorial assume que você já tenha um repositório Git configurado com o código do projeto.

