package za.co.discovery.assignment.musa.mthembu.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;

import java.util.List;

@Repository
public interface TrafficRepository extends CrudRepository<Traffic,Integer> {
    @Query(value = "select * from Traffic  where origin =:planetOrigin", nativeQuery = true)
    List<Traffic> trafficOrigins(String planetOrigin);

    @Query(value = "select distinct origin from Traffic", nativeQuery = true)
    List<String> uniqueValues();


}
