package lim.seyeon.safe.stay.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lim.seyeon.safe.stay.domain.CrimeData.CrimeData;
import lim.seyeon.safe.stay.domain.CrimeData.CrimeDataRepository;
import lim.seyeon.safe.stay.presentation.DTO.CrimeDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class CrimeDataService {

    private CrimeDataRepository crimeDataRepository;

    @Autowired
    public CrimeDataService(CrimeDataRepository crimeDataRepository) {
        this.crimeDataRepository = crimeDataRepository;
    }

    public void loadCrimeData(File file) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CrimeDataDTO> crimeDataDTOList = objectMapper.readValue(file, new TypeReference<List<CrimeDataDTO>>(){});
        List<CrimeData> crimeDataList = crimeDataDTOList.stream()
                    .map(crimeDataDTO -> CrimeDataDTO.toEntity(crimeDataDTO))
                    .toList();
        for (CrimeData crimeData : crimeDataList) {
            crimeDataRepository.add(crimeData);
        }
    }

    public List<CrimeDataDTO> findCrimeDataByAreaNum(Integer area_num) {
        List<CrimeData> crimeDatas = crimeDataRepository.findCrimeDataByAreaNum(area_num);
        List<CrimeDataDTO> crimeDataDTOS = crimeDatas.stream()
                .map(crimeData -> CrimeDataDTO.toDTO(crimeData))
                .toList();
        return crimeDataDTOS;
    }

    public void deleteAllCrimes() {
        crimeDataRepository.deleteAllCrimes();
    }
}
