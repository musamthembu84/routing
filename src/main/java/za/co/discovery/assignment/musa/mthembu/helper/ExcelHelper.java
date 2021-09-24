package za.co.discovery.assignment.musa.mthembu.helper;

import com.google.common.collect.Iterables;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import za.co.discovery.assignment.musa.mthembu.model.Traffic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelHelper {

    public List<Traffic>  readFile(String path) {

        List<Traffic> trafficArrayList = new ArrayList<>();
        try{
            Workbook workbook = WorkbookFactory.create(new File(path));

            for(Sheet sheet: workbook) {
                if(sheet.getSheetName().equals("Traffic")){
                    for (Row row: Iterables.skip( sheet, 1)) {
                        Traffic traffic = new Traffic();
                        traffic.setOrigin(row.getCell(1).getStringCellValue());
                        traffic.setDestination(row.getCell(2).getStringCellValue());
                        traffic.setTraffic(row.getCell(3).getNumericCellValue());
                        trafficArrayList.add(traffic);
                    }
                }
            }
        } catch (FileNotFoundException ec) {
            ec.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Traffic t : trafficArrayList){
            System.out.println("origins" + t.getOrigin());
        }
        return trafficArrayList;
    }


}
