package lim.seyeon.safe.stay;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Application {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
		System.setProperty("LIVIT_EMAIL_PASSWORD", dotenv.get("LIVIT_EMAIL_PASSWORD"));
		System.setProperty("aws.bucketName", dotenv.get("aws.bucketName"));
		System.setProperty("aws.region", dotenv.get("aws.region"));

		SpringApplication.run(Application.class, args);
	}

}
