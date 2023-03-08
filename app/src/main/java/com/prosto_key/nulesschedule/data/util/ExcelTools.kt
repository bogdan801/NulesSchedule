package com.prosto_key.nulesschedule.data.util

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook

//Клас для спрощення доступу до Excel файлу, організації роботи з бібліотекою Apache Poi
fun getMergedRegionForCell(c: Cell): CellRangeAddress? {
    val s = c.row.sheet
    val addresses = arrayOfNulls<CellRangeAddress>(s.numMergedRegions)
    for (i in addresses.indices) {
        addresses[i] = s.getMergedRegion(i)
    }
    for (mergedRegion in addresses) {
        if (mergedRegion!!.isInRange(c.rowIndex, c.columnIndex)) {
            // This region contains the cell in question
            return mergedRegion
        }
    }
    // Not in any
    return null
}

fun getCellValue(workbook: XSSFWorkbook, sheet: Int, row: Int, cellID: Int): String {
    val cell = workbook.getSheetAt(sheet).getRow(row).getCell(cellID) ?: return ""
    val cells = getMergedRegionForCell(cell as Cell)
    return if (cells != null) {
        val firstRegCell = workbook.getSheetAt(sheet).getRow(cells.firstRow).getCell(cells.firstColumn)
        when (firstRegCell.cellType) {
            1 -> firstRegCell.stringCellValue
            0 -> firstRegCell.numericCellValue.toString()
            4 -> firstRegCell.booleanCellValue.toString()
            else -> ""
        }
    } else {
        when (cell.cellType) {
            1 -> cell.stringCellValue
            0 -> cell.numericCellValue.toString()
            4 -> cell.booleanCellValue.toString()
            else -> ""
        }
    }
}

fun getLastRowNum(workbook: XSSFWorkbook, sheet: Int): Int {
    return workbook.getSheetAt(sheet).lastRowNum
}

fun getLastColumnNum(workbook: XSSFWorkbook, sheet: Int): Int {
    return workbook.getSheetAt(sheet).getRow(3).lastCellNum.toInt()
}