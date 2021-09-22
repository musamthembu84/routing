package za.co.discovery.assignment.musa.mthembu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;
import za.co.discovery.assignment.musa.mthembu.service.TrafficService;

import java.util.List;

@RestController
public class RoutesController {

    private TrafficService trafficService;

    @Autowired
    public RoutesController(TrafficService trafficService) {
        this.trafficService = trafficService;
    }

    @RequestMapping(value = "/add-entry", method = RequestMethod.POST)
    public void createNewTrafficEntry (@RequestBody Traffic trafficRecord){
        trafficService.addTrafficEntry(trafficRecord);
    }

    @RequestMapping("/adds")
    public List<Traffic> trafficData (){
        return trafficService.getRecords();
    }

    @Value("${spring.application.name}")
    String appName;
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }
}
