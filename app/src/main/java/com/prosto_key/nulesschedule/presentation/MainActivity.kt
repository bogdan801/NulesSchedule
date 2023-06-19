package com.prosto_key.nulesschedule.presentation

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prosto_key.nulesschedule.R
import com.prosto_key.nulesschedule.data.local.excel_parsing.WorkBook
import com.prosto_key.nulesschedule.presentation.navigation.Navigation
import com.prosto_key.nulesschedule.presentation.theme.NulesScheduleTheme
import com.prosto_key.nulesschedule.presentation.util.queryName
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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
                        Toast.makeText(this@MainActivity, this@MainActivity.getString(R.string.file_opened), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        setTheme(R.style.GreenTheme)
        setContent {
            NulesScheduleTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(MaterialTheme.colors.primary)
                systemUiController.setNavigationBarColor(MaterialTheme.colors.surface)

                var showSplashScreen by remember { mutableStateOf(true) }

                LaunchedEffect(key1 = true){
                    delay(1000)
                    showSplashScreen = false
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    Navigation(navController = rememberNavController(), launcher = openFileLauncher)
                    if(showSplashScreen){
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colors.surface),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(100.dp),
                                painter = painterResource(id = R.drawable.ic_splash),
                                contentDescription = "",
                                tint = MaterialTheme.colors.secondary
                            )
                        }
                    }
                }
            }
        }
    }
}