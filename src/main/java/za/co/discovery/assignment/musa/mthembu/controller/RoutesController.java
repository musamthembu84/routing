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
        System.out.println("testing");

        List<Vertex> vertices = new ArrayList<>();
        for(String names:calculateRoutesService.uniqueEntries()){
            vertices.add(new Vertex(names));

        }

        Vertex vertexA = new Vertex("A");
        Vertex vertexB = new Vertex("B");
        Vertex vertexC = new Vertex("C");
        Vertex vertexD = new Vertex("D");
        Vertex vertexE = new Vertex("E");

        for (Traffic traffic : calculateRoutesService.getAllRoutes()){
            if(traffic.getOrigin().equals(vertexA.getName())){
                System.out.println();
                vertexA.addNeighbour(new Edge(traffic.getTraffic(),vertexA,new Vertex(traffic.getOrigin())));
            }
            if(traffic.getOrigin().equals(vertexB.getName())){
                vertexB.addNeighbour(new Edge(traffic.getTraffic(),vertexB,new Vertex(traffic.getOrigin())));
            }
            if(traffic.getOrigin().equals(vertexC.getName())){
                vertexC.addNeighbour(new Edge(traffic.getTraffic(),vertexC,new Vertex(traffic.getOrigin())));
            }
            if(traffic.getOrigin().equals(vertexD.getName())){
                vertexD.addNeighbour(new Edge(traffic.getTraffic(),vertexD,new Vertex(traffic.getOrigin())));
            }
            if(traffic.getOrigin().equals(vertexE.getName())){
                vertexE.addNeighbour(new Edge(traffic.getTraffic(),vertexE,new Vertex(traffic.getOrigin())));
            }

        }



        DijkstraShortestPath shortestPath = new DijkstraShortestPath();
        shortestPath.calculateShortestPath(vertexA);


        System.out.println("Shortest Path from A to B: "+shortestPath.getShortestPathTo(vertexB));
        System.out.println("Shortest Path from A to C: "+shortestPath.getShortestPathTo(vertexC));
        System.out.println("Shortest Path from A to D: "+shortestPath.getShortestPathTo(vertexD));
        System.out.println("Shortest Path from A to E: "+shortestPath.getShortestPathTo(vertexE));



//        System.out.println("Minimum distance from"+displayVertex.get(2)+" to "+displayVertex.get(3)+":" +
//                " "+displayVertex.get(3));
//        System.out.println("Minimum distance from"+displayVertex.get(2)+" to "+displayVertex.get(4)+":" +
//                " "+displayVertex.get(4));
//        System.out.println("Minimum distance from"+displayVertex.get(2)+" to "+displayVertex.get(5)+":" +
//                " "+displayVertex.get(5));




//        for(Vertex v: vertices){
//            for (Traffic traffic : calculateRoutesService.getAllRoutes()){
//                if(v.getName().equals(traffic.getOrigin())){
//                    v.addNeighbour(new Edge(traffic.getTraffic(),new Vertex(traffic.getOrigin()),new Vertex(traffic.getDestination())));
//                    realVertex.add(v);
//                    System.out.println(
//                    "Name :" + v.getName()+ " Origin :"+ traffic.getOrigin() + " Destination :" + traffic.getDestination()
//                    +" Weight :" + traffic.getTraffic());
//                }
//            }
//        }
//
//
//          DijkstraShortestPath shortestPath = new DijkstraShortestPath();
//        for (int  v = 0; v<realVertex.size();v++){
//            shortestPath.calculateShortestPath(realVertex.get(1));
//            System.out.println("Shortest Path from "+realVertex.get(1)+" to " +realVertex.get(v)+" : "+shortestPath.getShortestPathTo(realVertex.get(v)));
//
//        }

    }

}
