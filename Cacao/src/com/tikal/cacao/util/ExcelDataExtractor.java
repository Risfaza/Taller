/**
 * 
 */
package com.tikal.cacao.util;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaDecenal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaMensual;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaQuincenal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSemanal;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaSubsidio;
import com.tikal.cacao.tarifas.subsidioEmpleo.TarifaTrabajoRealizado;

/**
 * @author Tikal
 *
 */
public class ExcelDataExtractor {
	
	

	public static void guardarTarifa(String nombreTarifa) {
		XSSFWorkbook wb = null; 
		TarifaSubsidio tarifa = null;
		
		try {
			OPCPackage pkg = OPCPackage.open(new File("WEB-INF/Tarifa_ISR_".concat(nombreTarifa).concat(".xlsx")), PackageAccess.READ);
			wb = new XSSFWorkbook(pkg);
		} catch (InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            //System.out.println(wb.getSheetName(i));
            for (Row row : sheet) {
            	if (TarifaSemanal.class.equals(determinarTipoTarifa(nombreTarifa))) {
        			tarifa = new TarifaSemanal();
            	} else if (TarifaDecenal.class.equals(determinarTipoTarifa(nombreTarifa))) {
            		tarifa = new TarifaDecenal();
            	} else if (TarifaQuincenal.class.equals(determinarTipoTarifa(nombreTarifa))) {
            		tarifa = new TarifaQuincenal();
            	} else if (TarifaMensual.class.equals(determinarTipoTarifa(nombreTarifa))) {
            		tarifa = new TarifaMensual();
            	} else {
        			tarifa = new TarifaTrabajoRealizado();
        		}
				tarifa.setId(row.getRowNum()+1); 
                for (Cell cell : row) {
                    switch (cell.getColumnIndex()) {
                    	case 0:
                    		tarifa.setLimiteInferior1(cell.getNumericCellValue());
                    		break;
                    	case 1:
                    		tarifa.setLimiteInferior2(cell.getNumericCellValue());
                    		break;
                    	case 2:
                    		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    			tarifa.setLimiteSuperior(cell.getNumericCellValue());
                    		}
                    		break;
                    	case 3:
                    		tarifa.setCuotaFija(cell.getNumericCellValue());
                    		break;
                    	case 4:
                    		tarifa.setPorcentajeDelImpuesto(cell.getNumericCellValue());
                    		break;
                    	case 5:
                    		tarifa.setSubsidioParaElEmpleo(cell.getNumericCellValue());
                    		break;
                    }
                    ofy().save().entity(tarifa).now();
                }
            }
        }
    }
	
	public static TarifaTrabajoRealizado recuperarT(long id) {	
		return ofy().load().type(TarifaTrabajoRealizado.class).id(id).now();
	}
	
	public static TarifaSemanal recuperarS(long id) {	
		return ofy().load().type(TarifaSemanal.class).id(id).now();
	}
	
	public static TarifaDecenal recuperarD(long id) {	
		return ofy().load().type(TarifaDecenal.class).id(id).now();
	}
	
	public static TarifaQuincenal recuperarQ(long id) {	
		return ofy().load().type(TarifaQuincenal.class).id(id).now();
	}
	
	public static TarifaMensual recuperarM(long id) {	
		return ofy().load().type(TarifaMensual.class).id(id).now();
	}
	
	private static Class<?> determinarTipoTarifa(String nombreTarifa) {
		switch (nombreTarifa.toLowerCase()) {
			case "semanal":
				return TarifaSemanal.class;
			case "decenal":
				return TarifaDecenal.class;
			case "quincenal":
				return TarifaQuincenal.class;
			case "mensual":
				return TarifaMensual.class;
			default:
				return TarifaTrabajoRealizado.class;
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
