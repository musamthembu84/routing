package za.co.discovery.assignment.musa.mthembu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.discovery.assignment.musa.mthembu.algorithm.DijkstraShortestPath;
import za.co.discovery.assignment.musa.mthembu.algorithm.Edge;
import za.co.discovery.assignment.musa.mthembu.algorithm.Vertex;
import za.co.discovery.assignment.musa.mthembu.exceptions.ApplicationException;
import za.co.discovery.assignment.musa.mthembu.exceptions.GlobalExceptionHandler;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;
import za.co.discovery.assignment.musa.mthembu.responses.ApplicationResponse;
import za.co.discovery.assignment.musa.mthembu.service.CalculateRoutesService;
import za.co.discovery.assignment.musa.mthembu.service.ImportDataService;
import za.co.discovery.assignment.musa.mthembu.service.TrafficService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoutesController {

    private static final String CREATED_SUCCESSFULLY = "Traffic Entry Created Successfully";
    private static final String UPDATED_SUCCESSFULLY = "Traffic Entry Updated Successfully";
    private static final String DELETED_SUCCESSFULLY = "Traffic Entry Deleted Successfully";
    private final TrafficService trafficService;
    private final GlobalExceptionHandler globalExceptionHandler;
    private final ImportDataService importDataService;
    private final CalculateRoutesService calculateRoutesService;

    @Autowired
    public RoutesController(TrafficService trafficService,
                            GlobalExceptionHandler globalExceptionHandler,
                            CalculateRoutesService calculateRoutesService,
                            ImportDataService importDataService) {
        this.trafficService = trafficService;
        this.globalExceptionHandler = globalExceptionHandler;
        this.importDataService = importDataService;
        this.calculateRoutesService = calculateRoutesService;
    }

    @PostMapping(value = "/add-entry")
    public HttpEntity<ApplicationResponse> createNewTrafficEntry (@RequestBody Traffic trafficRecord){
        try{
             trafficService.addTrafficEntry(trafficRecord);
             return new ResponseEntity<>(new ApplicationResponse(CREATED_SUCCESSFULLY,
                    HttpStatus.OK.value()), HttpStatus.ACCEPTED);
        }
        catch (ApplicationException exception){
            return globalExceptionHandler.handleException(exception);
        }

    }

    @GetMapping(value = "/get-entry/{id}")
    public Traffic getTrafficEntryById(@PathVariable int id){
        return trafficService.getTrafficEntryById(id);
    }

    @GetMapping(value = "/delete-entry/{id}")
    public HttpEntity<ApplicationResponse> deleteTrafficEntry (@PathVariable int id){
        try{
            trafficService.deleteTrafficEntry(id);
            return new ResponseEntity<>(new ApplicationResponse(DELETED_SUCCESSFULLY,
                    HttpStatus.OK.value()), HttpStatus.ACCEPTED);
        }
        catch (ApplicationException exception){
            return globalExceptionHandler.handleException(exception);
        }

    }

    @PostMapping(value = "/update-entry/{id}")
    public HttpEntity<ApplicationResponse> updateTrafficEntry (@RequestBody Traffic trafficRecord, @PathVariable int id){
        try{
            trafficService.updateTrafficRecord(trafficRecord, id);
            return new ResponseEntity<>(new ApplicationResponse(UPDATED_SUCCESSFULLY,
                    HttpStatus.OK.value()), HttpStatus.ACCEPTED);
        }
        catch (ApplicationException exception){
            return globalExceptionHandler.handleException(exception);
        }

    }

    @GetMapping(value = "/get-entries")
    public  List<Traffic> getTrafficEntryById(){
        return trafficService.findAllTrafficRecords();
    }

    @PostMapping(value = "/save-all")
    public  HttpEntity<ApplicationResponse> saveEntriesFile(){
        try{
            importDataService.saveAllRecordsFromExcel("C:\\Users\\Musa\\Documents\\data.xlsx");
            return new ResponseEntity<>(new ApplicationResponse(CREATED_SUCCESSFULLY,
                    HttpStatus.OK.value()), HttpStatus.ACCEPTED);
        }
        catch (ApplicationException exception){
            return globalExceptionHandler.handleException(exception);
        }
    }
    @GetMapping(value = "/get-entries-all")
    public  long getTrafficEntries(){
        return importDataService.findAllTrafficRecords();
    }

    private void AddNeighbours(Traffic traffic, List<Vertex> vertexNames){
        for(Vertex v: vertexNames){
            v.addNeighbour(new Edge(traffic.getTraffic(),new Vertex(traffic.getOrigin()),new Vertex(traffic.getOrigin())));
        }
    }
    @GetMapping(value = "/calculatePoints")
    public void getRoutes(){

        List<Vertex> vertexNames = new ArrayList<>();
        List<Vertex> realVertex = new ArrayList<>();


        for(String names:calculateRoutesService.uniqueEntries()){
           vertexNames.add(new Vertex(names));
        }

        for(Vertex v: vertexNames){
            for (Traffic traffic : calculateRoutesService.getAllRoutes()){
                if(v.getName().equals(traffic.getOrigin())){
                    v.addNeighbour(new Edge(traffic.getTraffic(),new Vertex(traffic.getOrigin()),new Vertex(traffic.getDestination())));
                    realVertex.add(v);
                    System.out.println(
                    "Name :" + v.getName()+ " Origin :"+ traffic.getOrigin() + " Destination :" + traffic.getDestination()
                    +" Weight :" + traffic.getTraffic());
                }
            }
        }


          DijkstraShortestPath shortestPath = new DijkstraShortestPath();
        for (int  v = 0; v<realVertex.size();v++){
            shortestPath.calculateShortestPath(realVertex.get(1));
            System.out.println("Shortest Path from "+realVertex.get(1)+" to " +realVertex.get(v)+" : "+shortestPath.getShortestPathTo(realVertex.get(v)));

        }

    }

}
