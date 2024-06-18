package lim.seyeon.safe.stay.infrastructure;

import lim.seyeon.safe.stay.application.CrimeDataService;
import lim.seyeon.safe.stay.domain.CrimeDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/*
@Configuration
public class TestConfigCrimeDataSerevice {

    @Bean
    @Primary
    public CrimeDataRepository crimeDataRepository() {
        return new ListCrimeDataRepository();
    }

    @Bean
    public CrimeDataService crimeDataService(CrimeDataRepository crimeDataRepository) {
        return new CrimeDataService(crimeDataRepository);
    }
}
*/