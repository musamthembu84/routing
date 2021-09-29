package za.co.discovery.assignment.musa.mthembu.helper;

import com.google.common.collect.Iterables;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelHelper {
    private static final String TrafficData = "Traffic";

    public List<Traffic>  readFile(String path) {

        List<Traffic> trafficArrayList = new ArrayList<>();
        try{
            Workbook workbook = WorkbookFactory.create(new File(path));

            for(Sheet sheet: workbook) {
                if(sheet.getSheetName().equals(TrafficData)){
                    for (Row row: Iterables.skip( sheet, 1)) {
                        Traffic traffic = new Traffic();
                        createTrafficObject(traffic
                                            ,row.getCell(1).getStringCellValue()
                                            ,row.getCell(2).getStringCellValue()
                                            ,row.getCell(3).getNumericCellValue());
                        trafficArrayList.add(traffic);
                    }
                }
            }
        } catch (FileNotFoundException ec ) {
            ec.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trafficArrayList;
    }

    private void createTrafficObject(Traffic traffic, String origin, String dest, double trafficValue){
        traffic.setOrigin(origin);
        traffic.setDestination(dest);
        traffic.setTraffic(trafficValue);
    }
}
