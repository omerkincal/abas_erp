package org.kurtlarkonseyi;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.kurtlarkonseyi.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        DBOperations dbOperations = new DBOperations();
        dbOperations.calculateAveragePriceBasedOnProduct();
        dbOperations.calculateTotalAmountOfAllProducts();
        dbOperations.calculateAveragePriceOfAllProducts();
        dbOperations.calculateProductQuantityAccordingToOrder();

        String url = "http://localhost:9097/api/todos";

        HttpGet getRequest = new HttpGet(url);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpResponse httpResponse = httpClient.execute(getRequest);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String responseBody = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(responseBody);
            } else {
                System.out.println("Hata: " + httpResponse.getStatusLine().getStatusCode());
            }
        }

        String jsonData = "{ \"title\": \"Yeni Görev\", \"description\": \"Bu bir test görevidir.\" }";

        HttpPost postRequest = new HttpPost(url);

        postRequest.setHeader("Content-Type", "application/json");

        StringEntity requestEntity = new StringEntity(jsonData, "UTF-8");
        postRequest.setEntity(requestEntity);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpResponse httpResponse = httpClient.execute(postRequest);

            if (httpResponse.getEntity() == null) {
                System.out.println("Hata: Boş yanıt gövdesi");
            } else if (httpResponse.getStatusLine().getStatusCode() != 201) {
                System.out.println("Hata: " + httpResponse.getStatusLine().getStatusCode());
                System.out.println("Hata mesajı: " + EntityUtils.toString(httpResponse.getEntity()));
            } else {
                String responseBody = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(responseBody);
            }
        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }
    public static List<Product> getAll() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/abas";

        // Kimlik bilgileri
        String username = "your_username";
        String password = "your_password";

        // Bağlantı nesnesi oluşturun
        Connection connection = DriverManager.getConnection(url, username, password);

        // Statement nesnesi oluşturun
        Statement statement = connection.createStatement();

        // SQL sorgusu çalıştırın
        ResultSet resultSet = statement.executeQuery("SELECT * FROM product");

        // Ürünleri saklamak için bir liste oluşturun
        List<Product> products = new ArrayList<>();

        // Sonuçları okuyun ve listeye ekleyin
        while (resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getInt("id"));
            product.setProductNo(resultSet.getInt("product_no"));
            product.setOrderId(resultSet.getInt("order_id"));
            product.setQuantity(resultSet.getInt("quantity"));
            product.setUnitPrice(resultSet.getDouble("unit_price"));

            products.add(product);
        }

        // ResultSet nesnesini kapatın
        resultSet.close();

        // Bağlantıyı kapatın
        connection.close();

        return products;
    }
}
