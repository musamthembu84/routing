package za.co.discovery.assignment.musa.mthembu.algorithm;

public class Edge {

    private double weight;
    private Vertex startVertexs;
    private Vertex targetVertexs;

    public Edge(double weight, Vertex startVertexs, Vertex targetVertexs) {
        this.weight = weight;
        this.startVertexs = startVertexs;
        this.targetVertexs = targetVertexs;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Vertex getStartVertex() {
        return startVertexs;
    }

    public void setStartVertex(Vertex startVertexs) {
        this.startVertexs = startVertexs;
    }

    public Vertex getTargetVertex() {
        return targetVertexs;
    }

    public void setTargetVertex(Vertex targetVertexs) {
        this.targetVertexs = targetVertexs;
    }
}
