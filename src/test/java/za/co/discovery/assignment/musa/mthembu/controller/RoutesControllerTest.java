package za.co.discovery.assignment.musa.mthembu.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import za.co.discovery.assignment.musa.mthembu.exceptions.GlobalExceptionHandler;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;
import za.co.discovery.assignment.musa.mthembu.service.CalculateRoutesService;
import za.co.discovery.assignment.musa.mthembu.service.ImportDataService;
import za.co.discovery.assignment.musa.mthembu.service.RouteRequestService;
import za.co.discovery.assignment.musa.mthembu.service.TrafficService;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class RoutesControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrafficService trafficService;
    @MockBean
    private  GlobalExceptionHandler globalExceptionHandler;
    @MockBean
    private  ImportDataService importDataService;
    @MockBean
    private  CalculateRoutesService calculateRoutesService;
    @MockBean
    private  RouteRequestService routeRequestService;


    private List<Traffic> trafficList;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {

        this.trafficList = new ArrayList<>();
        this.trafficList.add(new Traffic( "user1@gmail.com", "pwd1",0.23));
        this.trafficList.add(new Traffic( "user2@gmail.com", "pwd1",0.25));
        this.trafficList.add(new Traffic( "user3@gmail.com", "pwd1",0.28));


    }

    @Test
    public void createTestForNewTrafficEntries() throws Exception {

       mockMvc.perform(post("/add-entry")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(user()))
                       .accept(MediaType.APPLICATION_JSON))
                       .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testGetAllTrafficEntries() throws Exception{
        given(trafficService.findAllTrafficRecords()).willReturn(trafficList);

        Mockito.when(trafficService.findAllTrafficRecords()).thenReturn(trafficList);
        mockMvc.perform(MockMvcRequestBuilders.get("/get-entries"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGettingTrafficEntryById() throws Exception{

        Traffic entry = new Traffic( "a", "n",0.23);
        Mockito.when(trafficService.getTrafficEntryById(1)).thenReturn(entry);
        mockMvc.perform(MockMvcRequestBuilders.get("/get-entry/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.origin", Matchers.is("a")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination",Matchers.is("n")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.traffic",Matchers.is(0.23)));
        Mockito.verify(trafficService).getTrafficEntryById(1);
    }

    @Test
    public void testDeleteTrafficEntry() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete-entry/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



    @Test
    public void saveEntriesFile() {
    }


    @Test
    public void getRoutes() {
    }

    private Traffic user(){
        Traffic traffic = new Traffic();
        traffic.setTraffic(0.32);
        traffic.setOrigin("mars");
        traffic.setDestination("earth");
        return  traffic;
    }
}