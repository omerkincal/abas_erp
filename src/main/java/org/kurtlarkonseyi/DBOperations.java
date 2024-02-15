package org.kurtlarkonseyi;


import org.kurtlarkonseyi.entity.Product;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBOperations {


    public DBOperations() throws SQLException {
    }

    public void calculateTotalAmountOfAllProducts() throws SQLException {
        double totalAmount = 0;
        List<Product> products = Main.getAll();

        for (Product product : products) {
            totalAmount += product.getQuantity() * product.getUnitPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedTotalAmount = decimalFormat.format(totalAmount);

        System.out.println("Bütün malların toplam tutarı: " + formattedTotalAmount);


        //System.out.printf("Bütün malların toplam tutarı: %.2f%n", totalAmount);
    }


    // 1. sorunun b şıkkı
    public void calculateAveragePriceOfAllProducts() throws SQLException {
        List<Product> products = Main.getAll();
        int totalProductCount = 0;
        double totalAmount = 0;

        for (Product product : products) {
            totalAmount += product.getQuantity() * product.getUnitPrice();
            totalProductCount += product.getQuantity();
        }

        double average = (totalProductCount > 0) ? (totalAmount / totalProductCount) : Double.NaN;

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedAverage = decimalFormat.format(average);

        System.out.println("Bütün malların ortalama fiyatı: " + formattedAverage);
    }

    //1. sorunun c şıkkı
    public void calculateAveragePriceBasedOnProduct() throws SQLException {
        List<Product> products = Main.getAll();
        Map<Integer, Double> averagePrices = new HashMap<>();
        Map<Integer, Integer> productQuantities = new HashMap<>();

        for (Product product : products) {
            int productNo = product.getProductNo();
            double productTotalPrice = product.getQuantity() * product.getUnitPrice();

            averagePrices.merge(productNo, productTotalPrice, Double::sum);
            productQuantities.merge(productNo, product.getQuantity(), Integer::sum);
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        System.out.println("Ürün bazında ortalama fiyatlar:");

        for (Map.Entry<Integer, Double> entry : averagePrices.entrySet()) {
            int productName = entry.getKey();
            double totalProductPrice = entry.getValue();
            int totalProductQuantity = productQuantities.get(productName);

            double averageProductPrice = totalProductPrice / totalProductQuantity;

            String formattedAverage = decimalFormat.format(averageProductPrice);
            System.out.println(productName + ": " + formattedAverage);
        }
    }

    //1. sorunun d şıkkı
    public void calculateProductQuantityAccordingToOrder() throws SQLException {
        List<Product> products = Main.getAll();

        Map<Integer, Map<Integer, Integer>> productQuantitiesPerOrder = new HashMap<>();

        for (Product product : products) {
            Integer productNo = product.getProductNo();
            Integer orderId = product.getOrderId();
            int quantity = product.getQuantity();

            productQuantitiesPerOrder.putIfAbsent(productNo, new HashMap<>());

            productQuantitiesPerOrder.get(productNo).putIfAbsent(orderId, 0);

            int currentQuantity = productQuantitiesPerOrder.get(productNo).get(orderId);
            productQuantitiesPerOrder.get(productNo).put(orderId, currentQuantity + quantity);
        }

        System.out.println("Tek tek mal bazlı, malların hangi siparişlerde kaç adet olduğu: ");

        for (Map.Entry<Integer, Map<Integer, Integer>> productEntry : productQuantitiesPerOrder.entrySet()) {
            Integer productNo = productEntry.getKey();
            Map<Integer, Integer> productQuantities = productEntry.getValue();

            System.out.println(productNo + ": " + productQuantities);
        }
    }
}