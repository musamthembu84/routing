package za.co.discovery.assignment.musa.mthembu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraShortestPath {

    private PriorityQueue<Vertexs> chooseNodeWithPriorityQueue(Vertexs source){
        PriorityQueue<Vertexs> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(source);
        source.setVisited(true);
        return priorityQueue;
    }

    public void computeShortestPath(Vertexs sourceVertexs){

        sourceVertexs.setDistance(0);
        PriorityQueue<Vertexs> priorityQueue  = chooseNodeWithPriorityQueue(sourceVertexs);


        while(!priorityQueue.isEmpty()){

            System.out.println("check empty queue" +priorityQueue);

            //get min distance

            Vertexs actualVertexs = priorityQueue.poll();

            adding(actualVertexs,priorityQueue);

            actualVertexs.setVisited(true);
        }
    }

    private void adding(final Vertexs actualVertexs, final PriorityQueue<Vertexs> priorityQueue){

        for(Edges edges : actualVertexs.getAdjacenciesList()){
            Vertexs v = edges.getTargetVertex();
            if(!v.isVisited()){
                double newDistance = actualVertexs.getDistance() + edges.getWeight();
                if( newDistance < v.getDistance() ){
                    priorityQueue.remove(v);
                    v.setDistance(newDistance);
                    v.setPredecessor(actualVertexs);
                    priorityQueue.add(v);
                }
            }
        }
    }

    public List<Vertexs> getShortestPathTo(Vertexs targetVertexs){
        List<Vertexs> path = new ArrayList<>();

        for(Vertexs vertexs = targetVertexs; vertexs !=null; vertexs = vertexs.getPredecessor()){
            path.add(vertexs);
        }

        Collections.reverse(path);
        return path;
    }
}
