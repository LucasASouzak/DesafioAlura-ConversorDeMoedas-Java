package Utilitario;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dados {
    private static final String ARQUIVO_CONVERSOES = "convertido.txt";
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static synchronized void logConversor(double valor, String moedaOrigem, double resultado, String moedaDestino) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARQUIVO_CONVERSOES, true))) {
            String dataHora = LocalDateTime.now().format(FORMATO_DATA);
            String linhaSalva = String.format("%s: %.2f %s -> %.2f %s", dataHora, valor, moedaOrigem, resultado, moedaDestino);
            escritor.println(linhaSalva);
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo. " + e.getMessage());
        }
    }
}