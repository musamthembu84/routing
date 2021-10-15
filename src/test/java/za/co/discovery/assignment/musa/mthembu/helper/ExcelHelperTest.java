package za.co.discovery.assignment.musa.mthembu.helper;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;
import za.co.discovery.assignment.musa.mthembu.repository.TrafficRepository;
import za.co.discovery.assignment.musa.mthembu.service.ImportDataService;

public class ExcelHelperTest {

    private ExcelHelper excelHelper;
    private ImportDataService importDataService;
    @Mock
    private TrafficRepository trafficRepository;

    @Before
    public void setUp() {
        excelHelper = new ExcelHelper();
        importDataService = new ImportDataService(trafficRepository,excelHelper);

    }

    @Test
    public void readFile() {

        Sheet mockSheet = mock(Sheet.class);
        Row mockRow = mock(Row.class);
        when(mockSheet.createRow(anyInt())).thenReturn(mockRow);

        List<Traffic> sheetData = excelHelper.readFile(importDataService.readResource());
        assertNotNull(sheetData);
    }
}