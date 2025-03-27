package edu.dp.sau.linnik_vlad;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class ParserController {

    private final ParserService parserService;
    private final ExcelExportService excelExportService;
    private final CurrencyService currencyService;

    public ParserController(ParserService parserService,
            ExcelExportService excelExportService,
            CurrencyService currencyService) {
        this.parserService = parserService;
        this.excelExportService = excelExportService;
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1") int page, Model model) throws IOException {
        List<String> quotes = parserService.parseQuotes(page);
        model.addAttribute("quotes", quotes);
        model.addAttribute("rates", currencyService.getRates());
        model.addAttribute("selectedPage", page);
        return "index";
    }

    @GetMapping("/parse")
    public String parse(Model model) throws IOException {
        List<String> quotes = parserService.parseQuotes(0);
        model.addAttribute("quotes", quotes);
        model.addAttribute("rates", currencyService.getRates());
        return "index";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportToExcel(@RequestParam(defaultValue = "1") int page) throws IOException {
        List<String> quotes = parserService.parseQuotes(page);
        byte[] excelFile = excelExportService.generateExcel(quotes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.attachment().filename("quotes_page_" + page + ".xlsx").build());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelFile);
    }

}
