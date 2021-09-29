package za.co.discovery.assignment.musa.mthembu.service;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;

import za.co.discovery.assignment.musa.mthembu.model.Traffic;
import za.co.discovery.assignment.musa.mthembu.repository.TrafficRepository;



public class TrafficServiceTest {

    private TrafficService trafficService;

    @Mock
    private TrafficRepository trafficRepository;
    @Mock
    private Traffic traffic;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        trafficService = new TrafficService(trafficRepository);
    }



    @Test
     public void shouldTestAddingTrafficEntry() {
         trafficService.addTrafficEntry(traffic);
         verify(trafficRepository, times(1)).save(traffic);
    }

    @Test
    public void testGetTrafficEntryById() {
        trafficRepository.save(traffic);
        when(trafficRepository.findById(1)).thenReturn(java.util.Optional.of(traffic));
        Traffic trafficEntry = trafficService.getTrafficEntryById(1);
        assertThat(trafficEntry, is(equalTo(traffic)));
    }

    @Test
    public void testDeleteTrafficEntry() {
        trafficRepository.save(traffic);
        doNothing().when(trafficRepository).deleteById(1);
        TrafficRepository trafficRepository = Mockito.mock(TrafficRepository.class);
        trafficService.deleteTrafficEntry(1);
        verify(trafficRepository, times(1)).deleteById(1);


    }


    @Test
    public void testFindingAllTrafficRecords() {
        List<Traffic> items = Arrays.asList(new Traffic("earth","mars",0.32));

        when(trafficRepository.findAll()).thenReturn(items);
        List<Traffic> trafficEntry = trafficService.findAllTrafficRecords();
        assertThat(items, is(equalTo(trafficEntry)));
        Mockito.when(trafficRepository.findAll()).thenReturn(items);
    }
}