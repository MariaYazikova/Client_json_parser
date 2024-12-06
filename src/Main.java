public class Main {
    public static void main(String[] args) {

        //URL API, к которому будет выполняться запрос
        String apiurl_1 = "https://laba4java.free.beeceptor.com/first";
        String apiurl_2 = "https://laba4java.free.beeceptor.com/second";

        //создание объектов класса HttpClient
        ClientsInfo clients_info_1 = new ClientsInfo(apiurl_1);
        ClientsInfo clients_info_2 = new ClientsInfo(apiurl_2);
        //вызов метода выполнения запроса
        clients_info_1.getClientInfo();
        clients_info_2.getClientInfo();
    }
}