package com.example.bookrental.utils;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@RequiredArgsConstructor
public class ExcelGenerator {
    public static <T> void generateExcel(HttpServletResponse response, List<T> data, String sheetName, Class<T> classes) throws IOException, IllegalAccessException {
        // Create workbook
        Workbook workbook = new HSSFWorkbook();

        // Create sheet
        Sheet sheet = workbook.createSheet(sheetName);

        // Create header row
        Row headerRow = sheet.createRow(0);
        Field[] fields = classes.getDeclaredFields();  //returns the field of provided entity in array format
        for (int i = 0; i < fields.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(fields[i].getName());
        }

        // Create data rows
        for (int i = 0; i < data.size(); i++) {//iterates over each element in the List<data>
            Row dataRow = sheet.createRow(i + 1);//create new row for every object of List<data>
            for (int j = 0; j < fields.length; j++) {//mathi ko field ko length jati hunxa date ko length ni teti nai hunxa
                // so iterating over each field of the field array
                Cell dataCell = dataRow.createCell(j);//create a cell for each field
                    fields[j].setAccessible(true);//field is accessible even if it is private
                    Object value = fields[j].get(data.get(i));
                    setCellValue(dataCell, value);
            }
        }
        // Set up response for file download
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + sheetName + ".xls");
        out.close();

    }
    private static void setCellValue(Cell cell, Object value) {
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof java.util.Date) {
            cell.setCellValue((java.util.Date) value);
            CellStyle style = cell.getSheet().getWorkbook().createCellStyle();
            style.setDataFormat((short) 14); // Date format
            cell.setCellStyle(style);
        }
    }

}
