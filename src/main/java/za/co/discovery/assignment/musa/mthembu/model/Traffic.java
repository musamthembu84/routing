package za.co.discovery.assignment.musa.mthembu.model;


import javax.persistence.*;

@Entity
public class Traffic {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int Id;
    private String origin;
    private String destination;
    private double traffic;

    public Traffic(String origin, String destination, double traffic) {
        this.origin = origin;
        this.destination = destination;
        this.traffic = traffic;
    }

    public Traffic() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getTraffic() {
        return traffic;
    }

    public void setTraffic(double traffic) {
        this.traffic = traffic;
    }
}
