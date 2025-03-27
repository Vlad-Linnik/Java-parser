package edu.dp.sau.linnik_vlad;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParserService {

    public List<String> parseQuotes(int page) throws IOException {
        List<String> result = new ArrayList<>();

        String url = "https://quotes.toscrape.com/page/" + page + "/";
        Document doc = Jsoup.connect(url).get();

        Elements quotes = doc.select("div.quote");

        for (Element quote : quotes) {
            String text = quote.select("span.text").text();
            String author = quote.select("small.author").text();
            result.add(text + " — " + author);
        }

        return result;
    }

}
