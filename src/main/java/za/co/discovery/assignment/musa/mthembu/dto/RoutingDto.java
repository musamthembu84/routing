package za.co.discovery.assignment.musa.mthembu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoutingDto {

    @JsonProperty("source")
    private String source;

    @JsonProperty("destination")
    private String destination;

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }
}
