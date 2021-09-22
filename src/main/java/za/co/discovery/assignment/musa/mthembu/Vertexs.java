package za.co.discovery.assignment.musa.mthembu;

import java.util.ArrayList;
import java.util.List;

public class Vertexs implements Comparable<Vertexs> {



    private String name;
    private List<Edges> adjacenciesList;
    private boolean visited;
    private Vertexs predecessor;
    private double distance = Double.MAX_VALUE;

    public Vertexs(String name) {
        this.name = name;
        this.adjacenciesList = new ArrayList<>();
    }

    public void addNeighbour(Edges edges){
        this.adjacenciesList.add(edges);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Edges> getAdjacenciesList() {
        return adjacenciesList;
    }

    public void setAdjacenciesList(List<Edges> adjacenciesList) {
        this.adjacenciesList = adjacenciesList;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Vertexs getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Vertexs predecessor) {
        this.predecessor = predecessor;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Vertexs o) {
        return 0;
    }
}
