package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "https://www.sas.am/en/catalog/new_products/";
        List<DataPoint> dataList = new ArrayList<>();

        try {
            // Connect to the URL and fetch the document
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(10000)
                    .get();

            List<Integer> prices = doc.getElementsByClass("price__new").eachText()
                    .stream()
                    .map(Main::removeLastThreeCharacters)
                    .map(Main::removeSpaces)
                    .map(Integer::valueOf)
                    .toList();
            List<String> titles = doc.getElementsByClass("product__name hidden visible-sm").eachText();

            // Loop through each product and extract data
            for (int i = 0; i < prices.size(); i++) {
                // Add the data to the list
                dataList.add(new DataPoint(titles.get(i), prices.get(i)));

                // Stop after scraping 20 products
                if (dataList.size() >= 20) break;
            }

            new QuickSort().quickSort(dataList, 0, dataList.size() - 1);
            //new TreeSortStrings().treeSort(dataList);

            // Save the data to a CSV file
            saveToCSV(dataList, "data.csv");
            System.out.println("Data saved successfully to data.csv!");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String removeSpaces(String it) {
        return it.replaceAll("\\s", "");
    }

    private static String removeLastThreeCharacters(String input) {
        if (input == null || input.length() <= 3) {
            return "";  // Return empty string if input is null or too short
        }
        return input.substring(0, input.length() - 3);
    }
    // Method to save the data to a CSV file
    private static void saveToCSV(List<DataPoint> data, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Name,Price,Unit\n");
            for (DataPoint point : data) {
                writer.write(point.name + "," + point.price + "\n");
            }
        }
    }
}
