package za.co.discovery.assignment.musa.mthembu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;
import za.co.discovery.assignment.musa.mthembu.repository.TrafficRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculateRoutesService {

    private final TrafficRepository trafficRepository;

    @Autowired
    public CalculateRoutesService(TrafficRepository trafficRepository) {
        this.trafficRepository = trafficRepository;
    }

    public List<Traffic> routes(String origin){
        return trafficRepository.trafficOrigins(origin);
    }

    public List<Traffic> getAllRoutes(){
        List<Traffic> records = new ArrayList<>();
        trafficRepository.findAll().forEach(records::add);
        return records;
    }

    public List<String> uniqueEntries(){
        return  trafficRepository.uniqueValues();
    }
}
