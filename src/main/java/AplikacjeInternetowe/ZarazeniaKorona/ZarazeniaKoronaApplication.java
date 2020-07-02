package AplikacjeInternetowe.ZarazeniaKorona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZarazeniaKoronaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZarazeniaKoronaApplication.class, args);
	}

}
