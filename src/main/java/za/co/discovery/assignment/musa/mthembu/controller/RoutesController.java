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
import za.co.discovery.assignment.musa.mthembu.service.ImportDataService;
import za.co.discovery.assignment.musa.mthembu.service.TrafficService;

import java.util.List;

@RestController
public class RoutesController {

    private static final String CREATED_SUCCESSFULLY = "Traffic Entry Created Successfully";
    private static final String UPDATED_SUCCESSFULLY = "Traffic Entry Updated Successfully";
    private static final String DELETED_SUCCESSFULLY = "Traffic Entry Deleted Successfully";
    private final TrafficService trafficService;
    private final GlobalExceptionHandler globalExceptionHandler;
    private final ImportDataService importDataService;

    @Autowired
    public RoutesController(TrafficService trafficService,
                            GlobalExceptionHandler globalExceptionHandler,
                            ImportDataService importDataService) {
        this.trafficService = trafficService;
        this.globalExceptionHandler = globalExceptionHandler;
        this.importDataService = importDataService;
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

    @GetMapping(value = "/calculatePoints")
    public void getRoutes(){
        Vertex vertexsA = new Vertex("A");
        Vertex vertexsB = new Vertex("B");
        Vertex vertexsC = new Vertex("C");
        Vertex vertexsD = new Vertex("D");
        Vertex vertexsE = new Vertex("E");


        vertexsA.addNeighbour(new Edge(10, vertexsA, vertexsC));
        vertexsA.addNeighbour(new Edge(17, vertexsA, vertexsB));


        vertexsC.addNeighbour(new Edge(5, vertexsC, vertexsB));
        vertexsC.addNeighbour(new Edge(9, vertexsC, vertexsD));
        vertexsC.addNeighbour(new Edge(11, vertexsC, vertexsE));


        vertexsB.addNeighbour(new Edge(1, vertexsB, vertexsD));
        vertexsD.addNeighbour(new Edge(6, vertexsD, vertexsE));

        DijkstraShortestPath shortestPath = new DijkstraShortestPath();
        shortestPath.computeShortestPath(vertexsA);

        System.out.println("======================================");
        System.out.println("Calculating minimum distance");
        System.out.println("======================================");

        System.out.println("Minimum distance from A to B: "+ vertexsB.getDistance());
        System.out.println("Minimum distance from A to C: "+ vertexsC.getDistance());
        System.out.println("Minimum distance from A to D: "+ vertexsD.getDistance());
        System.out.println("Minimum distance from A to E: "+ vertexsE.getDistance());

        System.out.println("=====================   =================");
        System.out.println("Calculating Paths");
        System.out.println("======================================");

        System.out.println("Shortest Path from A to B: "+shortestPath.getShortestPathTo(vertexsB));
        System.out.println("Shortest Path from A to C: "+shortestPath.getShortestPathTo(vertexsC));
        System.out.println("Shortest Path from A to D: "+shortestPath.getShortestPathTo(vertexsD));
        System.out.println("Shortest Path from A to E: "+shortestPath.getShortestPathTo(vertexsE));
    }
}
