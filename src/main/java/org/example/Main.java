package org.example;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException{
        //The URL of the target website's home page - Static Webpage
        String baseUrl = "https://quotes.toscrape.com";

        //initializing the list of Quote data objects that contains scraped data
        List<Quote> quotes = new ArrayList<>();

        //Downloading the target website with an HTTP GET request WITH VALID User Agent
        Document doc = Jsoup
                .connect(baseUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                .get();

        //Looking for the "Next →" HTML element
        Elements nextElements = doc.select(".next");

        //If there is a next page to scrape
        while (!nextElements.isEmpty()){
            Element nextElement = nextElements.first();

            //Extracting the relative URL of the next page
            String relativeUrl = nextElement.getElementsByTag("a").first().attr("href");

            //Building the complete URL of the next page
            String completeUrl = baseUrl + relativeUrl;

            //Connecting to the next page
            doc = Jsoup
                    .connect(completeUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                    .get();

            //Retrieving the list of product HTML elements
            Elements quoteElements = doc.select(".quote");

            //Iterating over the quoteElements list of HTML quotes
            for (Element quoteElement : quoteElements){
                Quote quote = new Quote();

                //Extracting the text of the quote and removing special characters
                String text = quoteElement.select(".text").first().text();
                String author = quoteElement.select(".author").first().text();

                List<String> tags = new ArrayList<>();

                //Iterating over the list of tags
                for (Element tag : quoteElement.select(".tag"))
                    tags.add(tag.text());

                //Storing the scraped data in the Quote object
                quote.setText(text);
                quote.setAuthor(author);
                quote.setTags(String.join(", ", tags));

                quotes.add(quote);
            }
            nextElements = doc.select(".next");
        }

        //Creating the output CSV file
        File csvFile = new File("output.csv");
        try (PrintWriter printWriter = new PrintWriter(csvFile, StandardCharsets.UTF_8)) {
            //To handle BOM
            printWriter.write('\ufeff');

            for (Quote quote : quotes){
                List<String> row = new ArrayList<>();

                //Wrapping each field with between quotes to make the CSV file more consistent
                row.add("\"" + quote.getText() + "\"");
                row.add("\"" +quote.getAuthor() + "\"");
                row.add("\"" +quote.getTags() + "\"");

                //Printing a CSV line
                printWriter.println(String.join(",", row));
            }
        }
    }
}