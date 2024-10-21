package Utilitario;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class ApiClient {
    private static final String CHAVE_API = "a7fcc32cb108c34409e8421a"; // ***Substitua pela sua chave***
    private static final String MOEDA_BASE = "BRL";
    private static TaxasDeCambio taxasDeCambio;
    private static Instant ultimaAtualizacao;

    private static class TaxasDeCambio {
        @SerializedName("conversion_rates")
        public Map<String, Double> taxas;
    }

    private static void atualizarTaxasDeCambio() throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/" + CHAVE_API + "/latest/" + MOEDA_BASE;

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        taxasDeCambio = gson.fromJson(resposta.body(), TaxasDeCambio.class);
        ultimaAtualizacao = Instant.now();
    }

    public static double converter(String moedaOrigem, String moedaDestino, double valor) throws IOException, InterruptedException {
        if (taxasDeCambio == null || ultimaAtualizacao.plus(1, ChronoUnit.HOURS).isBefore(Instant.now())) {
            atualizarTaxasDeCambio();
        }

        if (taxasDeCambio == null || taxasDeCambio.taxas == null) {
            throw new RuntimeException("Erro: Falha ao obter taxas de câmbio.");
        }

        Double taxaOrigem = taxasDeCambio.taxas.get(moedaOrigem);
        Double taxaDestino = taxasDeCambio.taxas.get(moedaDestino);

        if (taxaOrigem == null || taxaDestino == null) {
            throw new IllegalArgumentException("Moeda inválida: " + moedaOrigem + " ou " + moedaDestino);
        }

        double resultado = valor * (taxaDestino / taxaOrigem);

        Dados.logConversor(valor, moedaOrigem, resultado, moedaDestino);

        return resultado;
    }
}