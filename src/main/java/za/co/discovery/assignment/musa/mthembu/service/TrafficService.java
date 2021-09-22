package za.co.discovery.assignment.musa.mthembu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;
import za.co.discovery.assignment.musa.mthembu.repository.TrafficRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrafficService {

    private final TrafficRepository trafficRepository;

    @Autowired
    public TrafficService(TrafficRepository trafficRepository) {
        this.trafficRepository = trafficRepository;
    }

    public void addTrafficEntry(final Traffic trafficRecord){
        trafficRepository.save(trafficRecord);
    }
    public List<Traffic> getRecords(){

        List<Traffic> records = new ArrayList<>();
        trafficRepository.findAll().forEach(records::add);
        return records;
    }
}
