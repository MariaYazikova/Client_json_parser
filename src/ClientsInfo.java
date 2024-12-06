import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

public class ClientsInfo {
    private final String apiurl;

    //конструктор с параметром
    public ClientsInfo(String apiurl) {
        this.apiurl = apiurl;
    }

    //конструктор без параметра
    public ClientsInfo(){
        this.apiurl = "";
    }

    //выполнение запроса
    public void getClientInfo() {
        try {
            //создание ссылки на API
            URL url = new URL(apiurl);
            printUrlDetails(url);

            //установка соединения и настройка запроса
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();
            System.out.println("Connection established.");

            //вывод заголовков ответа
            System.out.println("\nResponse headers:");
            connection.getHeaderFields().forEach((key, value) -> {
                System.out.println(key + ": " + value);
            });

            //получение кода ответа
            int response_code = connection.getResponseCode();
            System.out.println("\nResponse code: " + response_code + "\n");

            //чтение и вывод тела ответа
            if (response_code == HttpURLConnection.HTTP_OK) {
                String response = readResponse(connection);
                ResponseFormat(response);
            } else {
                System.out.println("The request failed.");
            }

        }
        //обработка разных типов исключений
        //неверный формат URL
         catch (MalformedURLException e) {
            System.err.println("Invalid URL format: " + e.getMessage());
        //ошибки ввода/вывода
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        //другие исключение не попавшие в предыдущие блоки
        } catch (Exception e) {
            System.err.println("General error: " + e.getMessage());
        }
    }

    //парсинг URL
    private void printUrlDetails(URL url) {
        System.out.println("URL Details:");
        System.out.println("Protocol:" + url.getProtocol());
        System.out.println("Host:" + url.getHost());
        System.out.println("Port:" + url.getPort());
        System.out.println("File:" + url.getFile());
        System.out.println("Anchor:" + url.getRef());
        System.out.println();
    }

    //чтение ответа сервера
    private String readResponse(HttpURLConnection connection) throws Exception {
        InputStream input_stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input_stream));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line).append("\n");
        }
        reader.close();
        return response.toString();
    }

    //красивый вывод ответа для первой url
    private void ResponseFormatClientData(String response) {
        JSONArray clients = new JSONArray(response);
        for (int i = 0; i < clients.length(); i++) {
            JSONObject client = clients.getJSONObject(i);

            String id = client.optString("id", "N/A");
            String name = client.optString("name", "N/A");
            String company = client.optString("company", "N/A");
            String username = client.optString("username", "N/A");
            String email = client.optString("email", "N/A");
            String address = client.optString("address", "N/A");
            String zip = client.optString("zip", "N/A");
            String state = client.optString("state", "N/A");
            String country = client.optString("country", "N/A");
            String phone = client.optString("phone", "N/A");
            String photo = client.optString("photo", "N/A");

            System.out.println("CLIENT " + (i + 1));
            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Company: " + company);
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            System.out.println("Address: " + address);
            System.out.println("ZIP: " + zip);
            System.out.println("State: " + state);
            System.out.println("Country: " + country);
            System.out.println("Phone: " + phone);
            System.out.println("Photo: " + photo);
            System.out.println();
        }
    }

    //красивый вывод для второй url
    private void ResponseFormatTasks(String response) {
        JSONArray tasks = new JSONArray(response);
        for (int i = 0; i < tasks.length(); i++) {
            JSONObject task = tasks.getJSONObject(i);

            String userId = task.optString("userId", "N/A");
            String id = task.optString("id", "N/A");
            String title = task.optString("title", "N/A");
            String completed = task.optString("completed", "N/A");

            System.out.println("TASK " + (i + 1));
            System.out.println("User ID: " + userId);
            System.out.println("Task ID: " + id);
            System.out.println("Title: " + title);
            System.out.println("Completed: " + completed);
            System.out.println();
        }
    }

    //выбор форматирования в зависимости от переданной url
    private void ResponseFormat(String response) {
        if (response.contains("userId")) {
            ResponseFormatTasks(response);
        } else {
            ResponseFormatClientData(response);
        }
    }
}

