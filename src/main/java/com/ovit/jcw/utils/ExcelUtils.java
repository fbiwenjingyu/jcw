package com.ovit.jcw.utils;

import java.text.*;
import org.apache.poi.hssf.usermodel.*;
import java.util.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtils
{
    public static String getCellValue(final Cell cell) {
        final String DATE_OUTPUT_PATTERNS = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_OUTPUT_PATTERNS);
        String ret = "";
        if (cell == null) {
            return ret;
        }
        final CellType type = cell.getCellTypeEnum();
        switch (type) {
            case BLANK: {
                ret = "";
                break;
            }
            case BOOLEAN: {
                ret = String.valueOf(cell.getBooleanCellValue());
                break;
            }
            case ERROR: {
                ret = null;
                break;
            }
            case FORMULA: {
                final Workbook wb = cell.getSheet().getWorkbook();
                final CreationHelper crateHelper = wb.getCreationHelper();
                final FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();
                ret = getCellValue(evaluator.evaluateInCell(cell));
                break;
            }
            case NUMERIC: {
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    final Date theDate = cell.getDateCellValue();
                    ret = simpleDateFormat.format(theDate);
                    break;
                }
                ret = NumberToTextConverter.toText(cell.getNumericCellValue());
                break;
            }
            case STRING: {
                ret = cell.getRichStringCellValue().getString();
                break;
            }
            default: {
                ret = "";
                break;
            }
        }
        return ret;
    }
    
    public static String getMergedRegionValueAndInfo(final Sheet sheet, final int row, final int column) {
        String MergedVal = "";
        String MergedInfo = "";
        for (int sheetMergeCount = sheet.getNumMergedRegions(), i = 0; i < sheetMergeCount; ++i) {
            final CellRangeAddress ca = sheet.getMergedRegion(i);
            final int firstColumn = ca.getFirstColumn();
            final int lastColumn = ca.getLastColumn();
            final int firstRow = ca.getFirstRow();
            final int lastRow = ca.getLastRow();
            if (row >= firstRow && row <= lastRow && column >= firstColumn && column <= lastColumn) {
                final Row fRow = sheet.getRow(firstRow);
                final Cell fCell = fRow.getCell(firstColumn);
                MergedVal = getCellValue(fCell);
                MergedInfo = String.valueOf(firstColumn) + "," + String.valueOf(firstRow) + "," + String.valueOf(lastColumn) + "," + String.valueOf(lastRow);
                return MergedVal + "&" + MergedInfo;
            }
        }
        return null;
    }
    
    public static int getMergerCellRegionCol(final Sheet sheet, final int cellRow, final int cellCol) {
        int retVal = 0;
        try {
            for (int sheetMergerCount = sheet.getNumMergedRegions(), i = 0; i < sheetMergerCount; ++i) {
                final CellRangeAddress cra = sheet.getMergedRegion(i);
                final int firstRow = cra.getFirstRow();
                final int firstCol = cra.getFirstColumn();
                final int lastRow = cra.getLastRow();
                final int lastCol = cra.getLastColumn();
                if (cellRow >= firstRow && cellRow <= lastRow && cellCol >= firstCol && cellCol <= lastCol) {
                    retVal = lastCol - firstCol + 1;
                    break;
                }
            }
        }
        catch (Exception ex) {}
        return retVal;
    }
    
    public static int getMergerCellRegionRow(final Sheet sheet, final int cellRow, final int cellCol) {
        int retVal = 0;
        for (int sheetMergerCount = sheet.getNumMergedRegions(), i = 0; i < sheetMergerCount; ++i) {
            final CellRangeAddress cra = sheet.getMergedRegion(i);
            final int firstRow = cra.getFirstRow();
            final int firstCol = cra.getFirstColumn();
            final int lastRow = cra.getLastRow();
            final int lastCol = cra.getLastColumn();
            if (cellRow >= firstRow && cellRow <= lastRow && cellCol >= firstCol && cellCol <= lastCol) {
                retVal = lastRow - firstRow + 1;
                break;
            }
        }
        return retVal;
    }
}
