package za.co.discovery.assignment.musa.mthembu.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraShortestPath {

    private PriorityQueue<Vertex> chooseNodeWithPriorityQueue(Vertex source){
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(source);
        source.setVisited(true);
        return priorityQueue;
    }

    public void calculateShortestPath(Vertex sourceVertex){

        sourceVertex.setDistance(0);
        PriorityQueue<Vertex> priorityQueue  = chooseNodeWithPriorityQueue(sourceVertex);
        while(!priorityQueue.isEmpty()){

            Vertex actualVertex = priorityQueue.poll();

            addVertexToQueue(actualVertex,priorityQueue);

            actualVertex.setVisited(true);
        }
    }

    private void addVertexToQueue(final Vertex actualVertex, final PriorityQueue<Vertex> priorityQueue){
        for(Edge edge : actualVertex.getAdjacenciesList()){
            Vertex v = edge.getTargetVertex();
            if(!v.isVisited()){
                double newDistance = actualVertex.getDistance() + edge.getWeight();
                if( newDistance < v.getDistance() ){
                    priorityQueue.remove(v);
                    v.setDistance(newDistance);
                    v.setPredecessor(actualVertex);
                    priorityQueue.add(v);
                }
            }
        }
    }

    public List<Vertex> getShortestPathTo(Vertex targetVertex){
        List<Vertex> path = new ArrayList<>();

        for(Vertex vertex = targetVertex; vertex !=null; vertex = vertex.getPredecessor()){
            path.add(vertex);
        }

        Collections.reverse(path);
        return path;
    }
}
