package za.co.discovery.assignment.musa.mthembu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import za.co.discovery.assignment.musa.mthembu.utils.DijkstraShortestPath;
import za.co.discovery.assignment.musa.mthembu.utils.Edge;
import za.co.discovery.assignment.musa.mthembu.utils.Vertex;
import za.co.discovery.assignment.musa.mthembu.dto.RoutingDto;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteRequestService {

    private CalculateRoutesService calculateRoutesService;
    private static final String NAMESPACE_URI = "https://www.discoveryassignment.com/xml/routes";

    @Autowired
    public RouteRequestService(CalculateRoutesService calculateRoutesService) {
        this.calculateRoutesService = calculateRoutesService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "calculateRoutes")
    @ResponsePayload
    public List<Vertex> shortestPathRoutes(RoutingDto routingDto){

        List<Vertex> vertices = new ArrayList<>();

        for(String names:calculateRoutesService.uniqueEntries()){
            vertices.add(new Vertex(names));
        }

        for(Vertex v: vertices){
            for (Traffic traffic : calculateRoutesService.getAllRoutes()){
                if(v.getName().equals(traffic.getOrigin())){
                    v.addNeighbour(new Edge(traffic.getTraffic(),new Vertex(traffic.getOrigin()),new Vertex(traffic.getDestination())));
                }
            }
        }

       return  getShortestPath(vertices,routingDto);

    }

    private List<Vertex> getShortestPath (List<Vertex> vertices,RoutingDto routingDto){
        DijkstraShortestPath shortestPath = new DijkstraShortestPath();

        int sourceCount = 0;
        int sourceDestination = 0;
        for (int  v = 0; v<vertices.size();v++){
            if (vertices.get(v).getName().equals(routingDto.getSource())){
                sourceCount = v;
            }
            if(vertices.get(v).getName().equals(routingDto.getDestination())){
                sourceDestination = v;
            }

        }
        shortestPath.calculateShortestPath(vertices.get(sourceCount));
        System.out.println("Shortest Path from "+vertices.get(sourceCount)+" to " +vertices.get(sourceDestination)+" : "+shortestPath.getShortestPathTo(vertices.get(sourceDestination)).get(0).getName());
        return   shortestPath.getShortestPathTo(vertices.get(sourceDestination));
    }

}
