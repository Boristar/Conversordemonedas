import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class Change {

    static class ExchangeResponse {
        String base_code;
        Map<String, Double> conversion_rates;
    }

    private static final String API_KEY = "e4d695cb565f1f80dd5274a2";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;
        double amount;

        do {
            System.out.println("=== ¿Problemas con los calculos de tus monedas? ===");
            System.out.println("1. Dólar (USD) a Peso Argentino (ARS)");
            System.out.println("2. Peso Argentino (ARS) a Dólar (USD)");
            System.out.println("3. Dólar (USD) a Real Brasileño (BRL)");
            System.out.println("4. Real Brasileño (BRL) a Dólar (USD)");
            System.out.println("5. Dólar (USD) a Peso Chileno (CLP)");
            System.out.println("6. Peso Chileno (CLP) a Dólar (USD)");
            System.out.println("7. Salir");
            System.out.print("Selecciona una opción: ");
            option = scanner.nextInt();

            if (option >= 1 && option <= 6) {
                System.out.print("¿Cuanto quieres convertir?: ");
                amount = scanner.nextDouble();

                switch (option) {
                    case 1: convert("USD", "ARS", amount); break;
                    case 2: convert("ARS", "USD", amount); break;
                    case 3: convert("USD", "BRL", amount); break;
                    case 4: convert("BRL", "USD", amount); break;
                    case 5: convert("USD", "CLP", amount); break;
                    case 6: convert("CLP", "USD", amount); break;
                }
            } else if (option != 7) {
                System.out.println("Opción inválida.");
            }
        } while (option != 7);

        System.out.println("Programa finalizado.");
    }

    public static void convert(String from, String to, double amount) {
        try {
            String urlStr = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + from;
            URL url = new URL(urlStr);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            InputStreamReader reader = new InputStreamReader(request.getInputStream());
            ExchangeResponse response = new Gson().fromJson(reader, ExchangeResponse.class);

            if (!response.conversion_rates.containsKey(to)) {
                System.out.println("Moneda de destino no encontrada.");
                return;
            }

            double rate = response.conversion_rates.get(to);
            double converted = amount * rate;

            System.out.printf("%.2f %s = %.2f %s%n%n", amount, from, converted, to);
        } catch (Exception e) {
            System.out.println("Error al realizar la conversión: " + e.getMessage());
        }
    }
}
