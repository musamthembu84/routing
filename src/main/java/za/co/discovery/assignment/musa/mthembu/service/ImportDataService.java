package za.co.discovery.assignment.musa.mthembu.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.assignment.musa.mthembu.helper.ExcelHelper;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;
import za.co.discovery.assignment.musa.mthembu.repository.TrafficRepository;
import java.util.List;

@Service
public class ImportDataService {

    private TrafficRepository trafficRepository;
    private ExcelHelper excelHelper;

    @Autowired
    public ImportDataService(TrafficRepository trafficRepository, ExcelHelper excelHelper) {
        this.trafficRepository = trafficRepository;
        this.excelHelper = excelHelper;
    }

    public void saveAllRecordsFromExcel(String path){

        try{
            List<Traffic> trafficList = excelHelper.readFile(path);
            System.out.println("size of data" + trafficList.size());
            trafficRepository.saveAll(trafficList);
            System.out.println(trafficRepository.count());


        }
        catch (Exception e){
            throw  new RuntimeException("failed to store data from excel" + e.getMessage());
        }
    }

    public long findAllTrafficRecords(){
        trafficRepository.count();
        return trafficRepository.count();
    }

}
