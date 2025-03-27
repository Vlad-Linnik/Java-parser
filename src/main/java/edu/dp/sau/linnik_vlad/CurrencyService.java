package edu.dp.sau.linnik_vlad;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CurrencyService {

    public List<CurrencyRate> getRates() {
        String url = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";
        RestTemplate restTemplate = new RestTemplate();

        CurrencyRate[] rates = restTemplate.getForObject(url, CurrencyRate[].class);
        return Arrays.asList(rates);
    }
}
