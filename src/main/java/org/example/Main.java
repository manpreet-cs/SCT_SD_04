package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        try (FileWriter writer = new FileWriter("products.csv")){
            Document document = Jsoup.connect("https://books.toscrape.com/").get();
            Elements books = document.select("article.product_pod");

            writer.append("\"Title\",\"Price\",\"Rating\"\n");

            System.out.println("--------------------------------------------------------------------------");
            System.out.printf("%-45s %-10s %-10s%n", "TITLE", "PRICE", "RATING");
            System.out.println("--------------------------------------------------------------------------");


            for (Element book : books) {

                Element titleElement = book.selectFirst("h3 a");
                Element priceElement = book.selectFirst("p.price_color");
                Element ratingElement = book.selectFirst("p.star-rating");

                if (titleElement == null || priceElement == null || ratingElement == null) {
                    continue;
                }

                String title = titleElement.text();
                String price = priceElement.text();
                String rating = ratingElement.className().replace("star-rating ", "");

                System.out.printf("%-45s %-10s %-10s%n", title, price, rating);

                writer.append("\"")
                        .append(title)
                        .append("\",\"")
                        .append(price)
                        .append("\",\"")
                        .append(rating)
                        .append("\"\n");
            }
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Data saved successfully!");

            long end = System.currentTimeMillis();
            System.out.println("Execution Time : " + (end - start) + " ms");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
