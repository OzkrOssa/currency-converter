package org.example;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class CurrencyConverter {

    // Reemplaza YOUR_API_KEY con tu clave de API
    private static final String API_KEY = "49a7e9eff7c7204a7fbc0450";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido al conversor de monedas.");
        System.out.println("Monedas mas populares EUR, GBP, CHF, JPY, HKD, CAD, CNY, AUD, BRL, RUB y MXN");
        System.out.print("Ingrese el monto en USD: ");
        double amount = scanner.nextDouble();
        System.out.print("Ingrese la moneda a la que desea convertir: ");
        String currency = scanner.next().toUpperCase();

        try {
            double convertedAmount = convertCurrency(amount, currency);
            System.out.printf("Monto convertido: %.2f %s\n", convertedAmount, currency);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al convertir la moneda: " + e.getMessage());
        }

        scanner.close();
    }

    private static double convertCurrency(double amount, String currency) throws Exception {
        URL url = new URL(API_URL + API_KEY + "/latest/USD");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int status = connection.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("HTTP error code: " + status);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONObject jsonObject = new JSONObject(response.toString());
        double exchangeRate = jsonObject.getJSONObject("conversion_rates").getDouble(currency);
        return amount * exchangeRate;
    }
}
