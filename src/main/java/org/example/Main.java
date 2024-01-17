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

/**
 * Main class to scrape quotes from a STATIC website and store them in a CSV file.
 * The chosen website is "Quotes to Scrape" - Static website made for scraping quotes from it
 * Code is hard coded to suit it for website however learnt about the Jsoup API
 */
public class Main{

    /**
     * The main entry point of the program.
     * @param args Command-line arguments (not used in this program).
     * @throws IOException If an I/O error occurs during web scraping or file writing.
     */
    public static void main(String[] args) throws IOException{
        String baseUrl = "https://quotes.toscrape.com";
        List<Quote> quotes = scrapeQuotes(baseUrl);
        writeQuotesToCSV("output.csv", quotes);

        System.gc();
        System.exit(0);
    }

    /**
     * Scrapes quotes from the given base URL and returns a list of Quote objects.
     * @param baseUrl The base URL of the website to scrape.
     * @return A list of Quote objects containing scraped data.
     * @throws IOException If an I/O error occurs during web scraping.
     */
    private static List<Quote> scrapeQuotes(String baseUrl) throws IOException{
        List<Quote> quotes = new ArrayList<>();
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36";
        Document doc = connectToUrl(baseUrl, userAgent);

        Elements nextElements = doc.select(".next");

        while (!nextElements.isEmpty()){
            Element nextElement = nextElements.first();
            String relativeUrl = nextElement.getElementsByTag("a").first().attr("href");
            String completeUrl = baseUrl + relativeUrl;
            doc = connectToUrl(completeUrl, userAgent);

            Elements quoteElements = doc.select(".quote");

            for (Element quoteElement : quoteElements)
                quotes.add(extractQuoteData(quoteElement));

            nextElements = doc.select(".next");
        }
        return quotes;
    }

    /**
     * Connects to the given URL with the specified user agent and returns the document.
     * @param url The URL to connect to.
     * @param userAgent The user agent to use for the connection.
     * @return The Document representing the HTML content of the page.
     * @throws IOException If an I/O error occurs during the connection.
     */
    private static Document connectToUrl(String url, String userAgent) throws IOException{
        return Jsoup.connect(url).userAgent(userAgent).get();
    }

    /**
     * Extracts quote data from the given HTML element and returns a Quote object.
     * @param quoteElement The HTML element containing quote information.
     * @return A Quote object containing extracted data.
     */
    private static Quote extractQuoteData(Element quoteElement){
        Quote quote = new Quote();
        String text = quoteElement.select(".text").first().text();
        String author = quoteElement.select(".author").first().text();

        List<String> tags = new ArrayList<>();
        for (Element tag : quoteElement.select(".tag"))
            tags.add(tag.text());

        quote.setText(text);
        quote.setAuthor(author);
        quote.setTags(String.join(", ", tags));
        return quote;
    }

    /**
     * Writes the list of Quote objects to a CSV file.
     * @param filePath The path of the CSV file to be created.
     * @param quotes   The list of Quote objects to be written to the file.
     * @throws IOException If an I/O error occurs during file writing.
     */
    private static void writeQuotesToCSV(String filePath, List<Quote> quotes) throws IOException{
        File csvFile = new File(filePath);
        try (PrintWriter printWriter = new PrintWriter(csvFile, StandardCharsets.UTF_8)) {
            //To handle BOM
            printWriter.write('\ufeff');

            for (Quote quote : quotes){
                List<String> row = new ArrayList<>();
                row.add("\"" + quote.getText() + "\"");
                row.add("\"" + quote.getAuthor() + "\"");
                row.add("\"" + quote.getTags() + "\"");
                printWriter.println(String.join(",", row));
            }
        }
    }
}