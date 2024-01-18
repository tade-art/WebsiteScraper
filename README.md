# Basic Website Scraper
This Java program utilizes the Jsoup API to scrape quotes from a static website and store them in a CSV file. The chosen website for demonstration is "Quotes to Scrape." The code is designed to work specifically for this website but serves as a learning example of the Jsoup API.

## Prerequisites
Make sure you have the following installed:

- Java Development Kit (JDK)
- Jsoup library (Provided in **pom.xml**)

## Usage
1. Clone the repository:

    ```bash
    git clone https://github.com/tade-art/WebsiteScraper
    ```

2. Open the project in your favorite Java IDE.
3. Ensure Jsoup library is added to the project dependencies.
4. Run the `Main` class to execute the scraper.

## Code Explanation
The `Main` class is the main entry point of the program. It consists of the following key methods:

### `scrapeQuotes(String baseUrl)`
This method initiates the web scraping process. It starts from the provided base URL and navigates through paginated content to collect quotes. The scraped quotes are stored in a list of `Quote` objects.

### `connectToUrl(String url, String userAgent)`
This method establishes a connection to a given URL with the specified user agent and returns the HTML document using Jsoup.

### `extractQuoteData(Element quoteElement)`
This method extracts quote data from HTML elements and creates a `Quote` object. It retrieves the quote text, author, and tags.

### `writeQuotesToCSV(String filePath, List<Quote> quotes)`
This method writes the list of `Quote` objects to a CSV file. It handles the encoding and adds a Byte Order Mark (BOM) to ensure proper CSV file compatibility.

## Example Usage
```java
public static void main(String[] args) throws IOException{
    String baseUrl = "https://quotes.toscrape.com";
    List<Quote> quotes = scrapeQuotes(baseUrl);
    writeQuotesToCSV("output.csv", quotes);

    System.gc();
    System.exit(0);
}
```

## Disclaimer
This program is for educational purposes and should be used responsibly and in compliance with the website's terms of service. The scraping code is tailored for the "Quotes to Scrape" website and may need adjustments for other websites.

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
