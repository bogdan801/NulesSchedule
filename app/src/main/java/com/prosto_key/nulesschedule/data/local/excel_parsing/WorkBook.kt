package com.prosto_key.nulesschedule.data.local.excel_parsing

import org.apache.poi.xssf.usermodel.XSSFWorkbook

data class WorkBook(
    val workBook: XSSFWorkbook,
    val fileName: String = ""
)
