package lim.seyeon.safe.stay;

//import lim.seyeon.safe.stay.infrastructure.TestConfigCrimeDataSerevice;

/*
@SpringBootTest
@ContextConfiguration(classes = {TestConfigCrimeDataSerevice.class})
public class CrimeDataServiceTest {

    @Autowired
    private CrimeDataService crimeDataService;

    @Test
    @DisplayName("Load Crime Data from JSON file and save to repository")
    void loadCrimeDataTest() throws Exception {
        Path path = Paths.get("src/test/resources/test-crime-data.json");
        String jsonData = "[\n" +
                "  {\n" +
                "    \"DR_NO\": 1,\n" +
                "    \"Date_Rptd\": \"1/1/2024 12:00:00 AM\",\n" +
                "    \"Date_Occ\": \"1/1/2024 12:00:00 AM\",\n" +
                "    \"Time_Occ\": 1111,\n" +
                "    \"Area\": 1,\n" +
                "    \"Area_Name\": \"area name\",\n" +
                "    \"Rpt_Dist_No\": 1,\n" +
                "    \"Part12\": 1,\n" +
                "    \"Crm_Cd\": 111,\n" +
                "    \"Crm_Cd_Desc\": \"crm cd desc\",\n" +
                "    \"Mocodes\": \"mocodes\",\n" +
                "    \"Vict_Age\": 1,\n" +
                "    \"Vict_Sex\": \"X\",\n" +
                "    \"Vict_Descent\": \"X\",\n" +
                "    \"Premis_Cd\": 111,\n" +
                "    \"Premis_Desc\": \"premis desc\",\n" +
                "    \"Weapon_Used\": null,\n" +
                "    \"Weapon_Desc\": null,\n" +
                "    \"Status\": \"status\",\n" +
                "    \"Status_Desc\": \"status desc\",\n" +
                "    \"Crm_Cd_1\": 111,\n" +
                "    \"Crm_Cd_2\": null,\n" +
                "    \"Crm_Cd_3\": null,\n" +
                "    \"Crm_Cd_4\": null,\n" +
                "    \"Location\": \"locatiton\",\n" +
                "    \"Cross_Street\": null,\n" +
                "    \"LAT\": \"11.1111\",\n" +
                "    \"LON\": \"-111.1111\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"DR_NO\": 2,\n" +
                "    \"Date_Rptd\": \"1/1/2024 12:00:00 AM\",\n" +
                "    \"Date_Occ\": \"1/1/2024 12:00:00 AM\",\n" +
                "    \"Time_Occ\": 2222,\n" +
                "    \"Area\": 1,\n" +
                "    \"Area_Name\": \"area name2\",\n" +
                "    \"Rpt_Dist_No\": 2,\n" +
                "    \"Part12\": 2,\n" +
                "    \"Crm_Cd\": 222,\n" +
                "    \"Crm_Cd_Desc\": \"crm cd desc2\",\n" +
                "    \"Mocodes\": \"mocodes2\",\n" +
                "    \"Vict_Age\": 2,\n" +
                "    \"Vict_Sex\": \"Y\",\n" +
                "    \"Vict_Descent\": \"Y\",\n" +
                "    \"Premis_Cd\": 222,\n" +
                "    \"Premis_Desc\": \"premis desc2\",\n" +
                "    \"Weapon_Used\": null,\n" +
                "    \"Weapon_Desc\": null,\n" +
                "    \"Status\": \"status2\",\n" +
                "    \"Status_Desc\": \"status desc2\",\n" +
                "    \"Crm_Cd_1\": 222,\n" +
                "    \"Crm_Cd_2\": null,\n" +
                "    \"Crm_Cd_3\": null,\n" +
                "    \"Crm_Cd_4\": null,\n" +
                "    \"Location\": \"locatiton2\",\n" +
                "    \"Cross_Street\": null,\n" +
                "    \"LAT\": \"22.2222\",\n" +
                "    \"LON\": \"-222.2222\"\n" +
                "  }\n" +
                "]";
        Files.write(path, jsonData.getBytes());
        File file = path.toFile();

        crimeDataService.loadCrimeData(file);

        List<CrimeDataDTO> crimeDataList = crimeDataService.findCrimeDataByAreaNum(1);
        assertNotNull(crimeDataList);
        assertEquals(2, crimeDataList.size());
        assertEquals(111, crimeDataList.get(0).getCrm_cd());
        assertEquals(222, crimeDataList.get(1).getCrm_cd());

        Files.delete(path);
        crimeDataService.deleteAllCrimes();
    }
}

*/