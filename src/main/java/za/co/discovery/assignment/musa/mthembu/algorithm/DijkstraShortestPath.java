package za.co.discovery.assignment.musa.mthembu.algorithm;

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

    public void computeShortestPath(Vertex sourceVertex){

        sourceVertex.setDistance(0);
        PriorityQueue<Vertex> priorityQueue  = chooseNodeWithPriorityQueue(sourceVertex);


        while(!priorityQueue.isEmpty()){

            System.out.println("check empty queue" +priorityQueue);

            //get min distance

            Vertex actualVertex = priorityQueue.poll();

            adding(actualVertex,priorityQueue);

            actualVertex.setVisited(true);
        }
    }

    private void adding(final Vertex actualVertex, final PriorityQueue<Vertex> priorityQueue){

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
