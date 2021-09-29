package za.co.discovery.assignment.musa.mthembu.helper;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import za.co.discovery.assignment.musa.mthembu.model.Traffic;

public class ExcelHelperTest {

    private ExcelHelper excelHelper;

    @Before
    public void setUp() {
        excelHelper = new ExcelHelper();
    }

    @Test
    public void readFile() {

        Sheet mockSheet = mock(Sheet.class);
        Row mockRow = mock(Row.class);
        when(mockSheet.createRow(anyInt())).thenReturn(mockRow);

        List<Traffic> sheetData = excelHelper.readFile("C:\\Users\\Musa\\Documents\\data.xlsx");
        assertNotNull(sheetData);
    }
}