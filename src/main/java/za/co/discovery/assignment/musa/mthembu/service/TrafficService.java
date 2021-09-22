package za.co.discovery.assignment.musa.mthembu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.assignment.musa.mthembu.exceptions.ApplicationException;
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

    public Traffic getTrafficEntryById(int id){
        checkIfEntryExits(id);
        return trafficRepository.findById(id).get();
    }

    public void deleteTrafficEntry(int id){
        checkIfEntryExits(id);
        trafficRepository.deleteById(id);
    }

    public void updateTrafficRecord(Traffic trafficEntry, int id){
        checkIfEntryExits(id);
        trafficRepository.save(trafficEntry);
    }
    public List<Traffic> findAllTrafficRecords(){
        List<Traffic> records = new ArrayList<>();
        trafficRepository.findAll().forEach(records::add);
        return records;
    }

    private void checkIfEntryExits(int id){
        trafficRepository.findById(id)
                .orElseThrow(()-> new ApplicationException(
                        String.format("User id %s not found",id))
                );
    }
}
