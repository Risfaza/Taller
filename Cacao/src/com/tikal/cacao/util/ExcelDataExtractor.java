/**
 * 
 */
package com.tikal.cacao.util;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tikal.cacao.tarifas.TarifaISRMensual;

/**
 * @author Tikal
 *
 */
public class ExcelDataExtractor {
	
	

	public void cargarTarifa() {
        Workbook wb = null;
        TarifaISRMensual tarifa = null;
		try {
			wb = new XSSFWorkbook(new FileInputStream("C:\\Users\\Ismael\\Documents\\Tarifa_ISR_mensual_2016.xlsx"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            //System.out.println(wb.getSheetName(i));
            for (Row row : sheet) {
            	tarifa = new TarifaISRMensual();
            	tarifa.setIndex(row.getRowNum());
                for (Cell cell : row) {
                    switch (cell.getColumnIndex()) {
                    	case 0:
                    		tarifa.setLimiteInferior1(cell.getNumericCellValue());
                    		break;
                    }
                }
            }
        }
    }
}
