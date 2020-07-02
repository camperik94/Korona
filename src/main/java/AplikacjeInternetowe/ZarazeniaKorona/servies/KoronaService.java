package AplikacjeInternetowe.ZarazeniaKorona.servies;

import AplikacjeInternetowe.ZarazeniaKorona.models.StatystykiLokalizacji;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class KoronaService {
    private static String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<StatystykiLokalizacji> allStats = new ArrayList<>();

    public List<StatystykiLokalizacji> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *") // odpowiada za czestotliwosc pobierania danych
    public void pobranieDanych() throws IOException, InterruptedException {
        List<StatystykiLokalizacji> newStats = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient(); // sluzy do wysylania i otrzymywania zapytan/odpowiedzi
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);

        for (CSVRecord record: records) { // biblioteka która wykrywa nagłowki
            StatystykiLokalizacji statystykiLokalizacji = new StatystykiLokalizacji();
            String state = record.get("Province/State");
            statystykiLokalizacji.setState(record.get("Province/State"));
            statystykiLokalizacji.setCountry(record.get("Country/Region"));
            statystykiLokalizacji.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));
            newStats.add(statystykiLokalizacji);
        }
        this.allStats = newStats;
    }
}
