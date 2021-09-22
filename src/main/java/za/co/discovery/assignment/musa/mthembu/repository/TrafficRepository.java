package za.co.discovery.assignment.musa.mthembu.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;

@Repository
public interface TrafficRepository extends CrudRepository<Traffic,Integer> {
}
