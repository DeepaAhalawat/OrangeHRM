package Utilities;
import java.io.FileInputStream;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



public class ExcelFileUtil {

	Workbook wb;
	//Reading path of excel file
	public ExcelFileUtil(String excelpath) throws Throwable 
	{
		
		FileInputStream fi= new FileInputStream(excelpath);
		wb=WorkbookFactory.create(fi)	;
	}
	
	public int rowCount(String sheetname)
	{
	 return wb.getSheet(sheetname).getLastRowNum();
	}
	
	public int cellCount(String sheetname)
	{
		
		return wb.getSheet(sheetname).getRow(0).getLastCellNum();
	}
	
	public String getCellData(String sheetname,int row,int column)
	{
		
		String data="";
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
			int cellData= (int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			data= String.valueOf(cellData);
			
		}
		else
			data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		
		return data;
	}
	
	//Writing to excel file
	public void setCellData(String sheetname,int row,int column,String status,String writeexcel) throws Throwable
	{
		
		Sheet ws= wb.getSheet(sheetname);
		Row rowNum=ws.getRow(row);
		Cell cell= rowNum.createCell(column);
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("Pass"))
		{
			
		CellStyle style=	wb.createCellStyle();
		Font font= wb.createFont();
		font.setColor(IndexedColors.GREEN.getIndex());
		font.setBold(true);
		
		//font.setBoldweight(font.BOLDWEIGHT_BOLD);  not supported by version 3.16
		style.setFont(font);
		rowNum.getCell(column).setCellStyle(style);
		
		}
		else if(status.equalsIgnoreCase("Fail"))
		{
			CellStyle style=wb.createCellStyle();
			//create font
			Font font=wb.createFont();
			//colour text
			font.setColor(IndexedColors.RED.getIndex());
			//bold text
			font.setBold(true);
			//font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("Blocked"))
		{
			CellStyle style=wb.createCellStyle();
			//create font
			Font font=wb.createFont();
			//colour text
			font.setColor(IndexedColors.BLUE.getIndex());
			//bold text
			font.setBold(true);
			//font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		FileOutputStream fo= new FileOutputStream(writeexcel);
		wb.write(fo);
		
	}
	
}
