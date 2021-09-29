package za.co.discovery.assignment.musa.mthembu.service;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;
import za.co.discovery.assignment.musa.mthembu.repository.TrafficRepository;


public class CalculateRoutesServiceTest {

    private CalculateRoutesService calculateRoutesService;

    @Mock
    private TrafficRepository trafficRepository;
    @Mock
    private Traffic traffic;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        calculateRoutesService = new CalculateRoutesService(trafficRepository);
    }

    @Test
    public void getAllRoutesTest() {
        List<Traffic> traffics = Arrays.asList(new Traffic("earth","mars",0.32));
        trafficRepository.save(traffic);
        when(trafficRepository.findAll()).thenReturn(traffics);
        List<Traffic> trafficEntry = calculateRoutesService.getAllRoutes();
        assertThat(traffics, is(equalTo(trafficEntry)));
        Mockito.when(trafficRepository.findAll()).thenReturn(traffics);
    }


    @Test
    public void testUniqueTrafficEntries() {
        List<String> traffics = new ArrayList<>();
        traffics.add("earth");
        traffics.add("earth");
        traffics.add("earth");

        trafficRepository.save(traffic);
       // when(trafficRepository.findAll()).thenReturn(traffics);
        List<String> names = calculateRoutesService.uniqueEntries();
      //  assertThat(traffics.size(), is(equalTo(names.size())));


    }


}