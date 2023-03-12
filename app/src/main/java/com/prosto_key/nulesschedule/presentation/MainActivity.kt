package com.prosto_key.nulesschedule.presentation

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prosto_key.nulesschedule.data.local.excel_parsing.WorkBook
import com.prosto_key.nulesschedule.presentation.navigation.Navigation
import com.prosto_key.nulesschedule.presentation.theme.NulesScheduleTheme
import com.prosto_key.nulesschedule.presentation.util.LockScreenOrientation
import com.prosto_key.nulesschedule.presentation.util.queryName
import dagger.hilt.android.AndroidEntryPoint
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var workbookState: MutableState<WorkBook>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val openFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data!!

                with(contentResolver.openInputStream(uri)){
                    if(this != null){
                        workbookState.value = WorkBook(workBook = XSSFWorkbook(this), fileName = queryName(contentResolver, uri))
                        Toast.makeText(this@MainActivity, "Файл відкрито", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        setContent {
            LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            NulesScheduleTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(MaterialTheme.colors.primary)
                systemUiController.setNavigationBarColor(MaterialTheme.colors.surface)

                Navigation(navController = rememberNavController(), launcher = openFileLauncher)
            }
        }
    }
}