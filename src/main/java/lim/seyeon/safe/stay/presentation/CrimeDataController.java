package lim.seyeon.safe.stay.presentation;

import lim.seyeon.safe.stay.application.CrimeDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/crime-data")
public class CrimeDataController {

    private CrimeDataService crimeDataService;
    private static final Logger logger = LoggerFactory.getLogger(CrimeDataController.class);

    @Autowired
    public CrimeDataController(CrimeDataService crimeDataService) {
        this.crimeDataService = crimeDataService;
    };

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCrimeData(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        try {
            File convFile = new File(file.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(convFile)) {
                fos.write(file.getBytes());
            }

            crimeDataService.loadCrimeData(convFile);

            boolean deleted = convFile.delete();
            if (!deleted) {
                logger.warn("Temporary file was not deleted: " + convFile.getAbsolutePath());
            }
            return ResponseEntity.status(HttpStatus.OK).body("Crime data file successfully uploaded");
        } catch (IOException e) {
            logger.error("IOException occurred while processing the crime data file: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing crime data file");
        } catch (Exception e) {
            logger.error("Exception occurred while saving crime data: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving crime data");
        }
    }
}
