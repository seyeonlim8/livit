package lim.seyeon.safe.stay.infrastructure;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class EmailConfigChecker {

    EmailConfigChecker() {}

    @PostConstruct
    public void checkEmailConfig() {
        Dotenv dotenv = Dotenv.load();
        System.out.println("Email password from environment: " + dotenv.get("LIVIT_EMAIL_PASSWORD"));
    }

    public static void main(String[] args) {
        EmailConfigChecker emailConfigChecker = new EmailConfigChecker();
        emailConfigChecker.checkEmailConfig();
    }

}

