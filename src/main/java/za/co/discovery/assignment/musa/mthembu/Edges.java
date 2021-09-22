package za.co.discovery.assignment.musa.mthembu;

public class Edges {

    private double weight;
    private Vertexs startVertexs;
    private Vertexs targetVertexs;

    public Edges(double weight, Vertexs startVertexs, Vertexs targetVertexs) {
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

    public Vertexs getStartVertex() {
        return startVertexs;
    }

    public void setStartVertex(Vertexs startVertexs) {
        this.startVertexs = startVertexs;
    }

    public Vertexs getTargetVertex() {
        return targetVertexs;
    }

    public void setTargetVertex(Vertexs targetVertexs) {
        this.targetVertexs = targetVertexs;
    }
}
