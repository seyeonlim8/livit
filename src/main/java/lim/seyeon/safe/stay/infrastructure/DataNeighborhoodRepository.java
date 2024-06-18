package lim.seyeon.safe.stay.infrastructure;

import lim.seyeon.safe.stay.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DataNeighborhoodRepository implements NeighborhoodRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private CrimeDataRepository crimeDataRepository;

    @Autowired
    public DataNeighborhoodRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, CrimeDataRepository crimeDataRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.crimeDataRepository = crimeDataRepository;
    }

    @Override
    public Neighborhood add(Neighborhood neighborhood) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(neighborhood);

        namedParameterJdbcTemplate.update(
                "INSERT INTO neighborhoods (name, population, safety_score) VALUES (:name, :population, :safety_score)",
                namedParameter
        );
        return neighborhood;
    }

    @Override
    public Neighborhood findNeighborhoodByName(String name) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("name", name);
        Neighborhood neighborhood = null;

        try {
            neighborhood = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM neighborhoods WHERE name = :name",
                    namedParameter, new BeanPropertyRowMapper<>(Neighborhood.class)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Neighborhood with name " + name + " not found");
        }
        return neighborhood;
    }

    @Override
    public List<Neighborhood> findAll() {
        List<Neighborhood> neighborhoods = namedParameterJdbcTemplate.query(
                "SELECT * FROM neighborhoods",
                new BeanPropertyRowMapper<>(Neighborhood.class)
        );
        return neighborhoods;
    }

    @Override
    public Neighborhood update(Neighborhood neighborhood) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(neighborhood);
        namedParameterJdbcTemplate.update(
                "UPDATE neighborhoods SET population = :population, safety_score = :safety_score WHERE name = :name",
                namedParameter
        );
        return neighborhood;
    }

    @Override
    public void delete(String name) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("name", name);
        namedParameterJdbcTemplate.update(
                "DELETE FROM neighborhoods WHERE name = :name",
                namedParameter);
    }

    @Override
    public Map<String, Double> calculateAndSetNeighborhoodSafetyScores() {
        Map<Neighborhood, Double> neighborhoodCrimeRatesInverted = new HashMap<>();
        List<Neighborhood> neighborhoods = this.findAll();

        for(Neighborhood neighborhood : neighborhoods) {
            String neighborhood_name = neighborhood.getName();
            Double totalWeightedCrimeSum = 0.0;
            Long population = neighborhood.getPopulation();

            // Get area num of neighborhood
            SqlParameterSource namedParameter = new MapSqlParameterSource("neighborhood_name", neighborhood_name);
            List<Integer> areaNums = namedParameterJdbcTemplate.queryForList(
                    "SELECT area_num FROM neighborhood_areas WHERE neighborhood_name = :neighborhood_name",
                    namedParameter, Integer.class
            );

            // Some neighborhoods have two area numbers, so sum crime rates of both area nums.
            for(Integer areaNum : areaNums) {
                List<CrimeData> crimeDataList = crimeDataRepository.findCrimeDataByAreaNum(areaNum);

                for(CrimeData crime : crimeDataList) {
                    totalWeightedCrimeSum += getWeightedScore(crime);
                }
            }

            // Crime rate per 1000 people
            Double crimeRate = (totalWeightedCrimeSum / population) * 1000;

            // Invert crime rate to calculate safety score
            Double invertedSafetyScore = 1 / crimeRate;
            neighborhoodCrimeRatesInverted.put(neighborhood, crimeRate);
        }

        Map<String, Double> neighborhoodSafetyScores = new HashMap<>();
        Double maxInvertedCrimeRate = Collections.max(neighborhoodCrimeRatesInverted.values());
        Double minInvertedCrimeRate = Collections.min(neighborhoodCrimeRatesInverted.values());

        for(Neighborhood neighborhood : neighborhoodCrimeRatesInverted.keySet()) {
            // Normalize safety score - range 0 to 100
            Double normalizedSafetyScore
                    = ((neighborhoodCrimeRatesInverted.get(neighborhood) - minInvertedCrimeRate) / (maxInvertedCrimeRate - minInvertedCrimeRate)) * 100;
            neighborhood.setSafety_score(normalizedSafetyScore);
            neighborhoodSafetyScores.put(neighborhood.getName(), normalizedSafetyScore);
            this.update(neighborhood);
        }

        return neighborhoodSafetyScores;
    }

    private Integer getWeightedScore(CrimeData crimeData) {
        Integer crm_cd = crimeData.getCrm_cd();
        // Part I Violent Crime
        if(isViolentCrime(crm_cd)) return 6;
        // Part I Property Crime
        if(isPropertyCrime(crm_cd)) return 5;
        // Crime Code 1 crime + Crime Code 2/3/4 crime
        if(crimeData.getCrm_cd_2() != null) return 4;
        if(crimeData.getCrm_cd_3() != null) return 3;
        if(crimeData.getCrm_cd_4() != null) return 2;
        return 1;
    }

    private boolean isViolentCrime(Integer crm_cd) {
        List<Integer> violentCrimeCodes = Arrays.asList(
                110, 113, 121, 122, 815, 820, 821, 210, 220, 230, 231, 235, 236, 250, 251, 761, 926,
                435, 436, 437, 622, 623, 624, 625, 626, 627, 647, 763, 928, 930
        );
        return violentCrimeCodes.contains(crm_cd);
    }

    private boolean isPropertyCrime(Integer crm_cd) {
        List<Integer> propertyCrimeCodes = Arrays.asList(
                310, 320, 510, 520, 433, 330, 331, 410, 420, 421, 350, 351, 352, 353, 450, 451, 452,
                453, 341, 343, 345, 440, 441, 442, 443, 444, 445, 470, 471, 472, 473, 474, 475, 480,
                485, 487, 491
        );
        return propertyCrimeCodes.contains(crm_cd);
    }
}
