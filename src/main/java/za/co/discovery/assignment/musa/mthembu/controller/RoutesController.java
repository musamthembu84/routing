package za.co.discovery.assignment.musa.mthembu.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.discovery.assignment.musa.mthembu.utils.Vertex;
import za.co.discovery.assignment.musa.mthembu.dto.RoutingDto;
import za.co.discovery.assignment.musa.mthembu.exceptions.ApplicationException;
import za.co.discovery.assignment.musa.mthembu.exceptions.GlobalExceptionHandler;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;
import za.co.discovery.assignment.musa.mthembu.responses.ApplicationResponse;
import za.co.discovery.assignment.musa.mthembu.service.CalculateRoutesService;
import za.co.discovery.assignment.musa.mthembu.service.ImportDataService;
import za.co.discovery.assignment.musa.mthembu.service.RouteRequestService;
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
    private final CalculateRoutesService calculateRoutesService;
    private final RouteRequestService routeRequestService;


    @Autowired
    public RoutesController(TrafficService trafficService,
                            GlobalExceptionHandler globalExceptionHandler,
                            CalculateRoutesService calculateRoutesService,
                            ImportDataService importDataService,
                            RouteRequestService routeRequestService) {
        this.trafficService = trafficService;
        this.globalExceptionHandler = globalExceptionHandler;
        this.importDataService = importDataService;
        this.calculateRoutesService = calculateRoutesService;
        this.routeRequestService = routeRequestService;
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



    @PostMapping(value = "/saveDataFromExcelFile")
    public  HttpEntity<ApplicationResponse> saveEntriesFile() throws Exception{
        try{


            importDataService.saveAllRecordsFromExcel(importDataService.readResource());
            return new ResponseEntity<>(new ApplicationResponse(CREATED_SUCCESSFULLY,
                    HttpStatus.OK.value()), HttpStatus.ACCEPTED);
        }
        catch (ApplicationException  exception){
            return globalExceptionHandler.handleException(exception);
        }
    }


    @PostMapping(value = "/calculatePoints")
    public List<Vertex> getRoutes(@RequestBody RoutingDto routingDto){
        return  routeRequestService.shortestPathRoutes(routingDto);
    }

}
