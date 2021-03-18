package com.cisco.services.api_automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    /**
     * <p>Row 0 needs to have all keys and Row 1 should have all the values for the keys</p>
     * <p>If multiline true, multiple values can be entered for single key</p>
     *
     * @param file
     * @param sheetName
     * @param multiline
     * @return
     */
    public static String[][] readTestData(String file, String sheetName, boolean multiline) {
        Workbook wb;
        Sheet sh;
        String[][] data = null;
        Cell cell;
        try (InputStream fis = new ClassPathResource(file).getInputStream()) {
            wb = WorkbookFactory.create(fis);
            sh = wb.getSheet(sheetName);
            if (sh != null) {
                if (multiline) {
                    int rowCnt = sh.getLastRowNum();
                    int colCnt = sh.getRow(0).getLastCellNum();
                    data = new String[rowCnt][colCnt];

                    for (int i = 0; i < rowCnt; i++)
                        for (int j = 0; j < colCnt; j++) {
                            cell = sh.getRow(i + 1).getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            switch (cell.getCellType()) {
                                case NUMERIC:
                                    data[i][j] = String.valueOf((int) cell.getNumericCellValue());
                                    break;
                                case STRING:
                                    data[i][j] = cell.getStringCellValue();
                                    break;
                                case BOOLEAN:
                                    data[i][j] = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case BLANK:
                                case _NONE:
                                    data[i][j] = "";
                                    break;
                                default:
                                    System.out.println("Verify the input text for the cell value");
                            }
                        }

                } else {
                    int rowCnt = sh.getLastRowNum() + 1;
                    int colCnt = sh.getRow(0).getLastCellNum();

                    data = new String[rowCnt][colCnt];

                    for (int i = 0; i < rowCnt; i++) {
                        for (int j = 0; j < colCnt; j++) {
                            cell = sh.getRow(i).getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            switch (cell.getCellType()) {
                                case NUMERIC:
                                    data[i][j] = String.valueOf((int) cell.getNumericCellValue());
                                    break;
                                case STRING:
                                    data[i][j] = cell.getStringCellValue();
                                    break;
                                case BOOLEAN:
                                    data[i][j] = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case BLANK:
                                case _NONE:
                                    data[i][j] = "";
                                    break;
                                default:
                                    System.out.println("Verify the input text for the cell value");
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public static List<Map<String, String >> getDataAsMap(String file, String sheetName) {
        Workbook wb;
        Sheet sh;
        List<Map<String,String>> data = new ArrayList<>();

        try (InputStream fis = new ClassPathResource(file).getInputStream()) {
            wb = WorkbookFactory.create(fis);
            sh = wb.getSheet(sheetName);
            if (sh != null) {
              List<String> headers = new ArrayList<>();
                sh.getRow(0).cellIterator().forEachRemaining(header -> headers.add(header.toString()));
                sh.removeRow(sh.getRow(0));
                sh.rowIterator().forEachRemaining(rows ->{
                    Map<String, String> row= new LinkedHashMap<>();
                    rows.cellIterator().forEachRemaining(cell -> {
                            row.put(headers.get(cell.getColumnIndex()),cell.toString());
                        });
                        data.add(row);
                });

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

}
