package za.co.discovery.assignment.musa.mthembu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.assignment.musa.mthembu.helper.ExcelHelper;
import za.co.discovery.assignment.musa.mthembu.repository.TrafficRepository;

import java.io.InputStream;
import java.util.Objects;


@Service
public class ImportDataService {

    private final TrafficRepository trafficRepository;
    private final ExcelHelper excelHelper;
    private final static String FAILURE_MESSAGE = "Failed to store data from excel";

    @Autowired
    public ImportDataService(TrafficRepository trafficRepository, ExcelHelper excelHelper) {
        this.trafficRepository = trafficRepository;
        this.excelHelper = excelHelper;
    }

    public void saveAllRecordsFromExcel(String path){

        try{
            trafficRepository.saveAll(excelHelper.readFile(path));
        }
        catch (Exception e){
            throw  new RuntimeException(FAILURE_MESSAGE+ e.getMessage());
        }
    }
    public String readResource() {
        String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("data.xlsx")).getPath();
        return filePath;
    }

    public long findAllTrafficRecords(){
        return trafficRepository.count();
    }


}
