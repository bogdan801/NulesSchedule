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
import com.prosto_key.nulesschedule.domain.repository.Repository
import com.prosto_key.nulesschedule.presentation.navigation.Navigation
import com.prosto_key.nulesschedule.presentation.theme.NulesScheduleTheme
import com.prosto_key.nulesschedule.presentation.util.LockScreenOrientation
import dagger.hilt.android.AndroidEntryPoint
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repo: Repository

    @Inject
    lateinit var workbookState: MutableState<XSSFWorkbook>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*val schedule = Schedule(
            scheduleID = 0,
            fileName = "fakultet_informaciynih_tehnologiy_03.03.23.xlsx",
            major = "КН",
            year = "4 + 2 ст",
            group = "1",
            week = Week(
                listOf(
                    Day(
                        numeratorLessons = listOf(
                            Lesson(
                                0,
                                0,
                                lessonName = "В6: Технології комп'ютерного проектування 214к.15 1"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В5: Адміністрування комп'ютерних мереж 206 к.15 2"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В1: Програмне забезпечення комп. інтегрованих технологій 214 к.15 2"
                            ),
                            null,
                            null,
                            null,
                            null
                        ),
                        denominatorLessons = listOf(
                            Lesson(
                                0,
                                0,
                                lessonName = "В6: Технології комп'ютерного проектування 214к.15 1"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В5: Адміністрування комп'ютерних мереж 206 к.15 2"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В1: Програмне забезпечення комп. інтегрованих технологій 214 к.15 2"
                            ),
                            null,
                            null,
                            null,
                            null
                        ),
                    ),
                    Day(
                        numeratorLessons = listOf(
                            null,
                            null,
                            null,
                            null,
                            Lesson(
                                0,
                                0,
                                lessonName = "В1: Засоби мультимедіа 230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В4: Цифрові технолгії в бізнесі 206 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "Технології розробки ІУС 230 к.15"
                            )
                        ),
                        denominatorLessons = listOf(
                            null,
                            null,
                            null,
                            null,
                            Lesson(
                                0,
                                0,
                                lessonName = "В4: Цифрові техн. В бізнесі 230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В4: Цифрові технолгії в бізнесі 206 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "Технології розробки ІУС 230 к.15"
                            )
                        ),
                    ),
                    Day(
                        numeratorLessons = listOf(
                            null,
                            Lesson(
                                0,
                                0,
                                lessonName = "В1: Засоби мультимедіа 211 к.15 1"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В3: Операційні системи реального часу 206 к.15 2"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В2: Програмування мобільних пристроїв 214 к.15 2"
                            ),
                            null,
                            null,
                            null
                        ),
                        denominatorLessons = listOf(
                            null,
                            Lesson(
                                0,
                                0,
                                lessonName = "В1: Засоби мультимедіа 211 к.15 1"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В3: Операційні системи реального часу 206 к.15 2"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В2: Програмування мобільних пристроїв 214 к.15 2"
                            ),
                            null,
                            null,
                            null
                        ),
                    ),
                    Day(
                        numeratorLessons = listOf(
                            Lesson(
                                0,
                                0,
                                lessonName = "В4: Системи КЕЕМ 230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В2: Програмування мобільних пристроїв  230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В1: Програмне забезпечення комп. Інтегрованих технологій 232 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "Іноземна мова 132 к.11"
                            ),
                            null,
                            null,
                            null
                        ),
                        denominatorLessons = listOf(
                            Lesson(
                                0,
                                0,
                                lessonName = "В5: Технології комп'ютерного проектування 230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В3: Операційні системи реального часу 230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В5: Адміністрування комп. мереж 230 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "Іноземна мова 132 к.11"
                            ),
                            null,
                            null,
                            null
                        ),
                    ),
                    Day(
                        numeratorLessons = listOf(
                            Lesson(
                                0,
                                0,
                                lessonName = "Технології розробки ІУС 213 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В4: Системи КЕЕМ 214 к.15"
                            ),
                            null,
                            null,
                            null,
                            null,
                            null
                        ),
                        denominatorLessons = listOf(
                            Lesson(
                                0,
                                0,
                                lessonName = "Технології розробки ІУС 213 к.15"
                            ),
                            Lesson(
                                0,
                                0,
                                lessonName = "В4: Системи КЕЕМ 214 к.15"
                            ),
                            null,
                            null,
                            null,
                            null,
                            null
                        ),
                    ),
                )
            )
        )*/

        val openFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data!!

                with(contentResolver.openInputStream(uri)){
                    if(this != null){
                        workbookState.value = XSSFWorkbook(this)
                        Toast.makeText(this@MainActivity, "Файл відкрито", Toast.LENGTH_SHORT).show()
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

                /*val majors by remember {
                    derivedStateOf {
                        if(workbookState.value.numberOfSheets > 0) repo.readMajorsFromFile(workbookState.value, 0)
                        else listOf()
                    }
                }
                var selectedMajor by remember { mutableStateOf(0) }

                val years by remember {
                    derivedStateOf {
                        if(workbookState.value.numberOfSheets > 0) repo.readYearsOfMajor(majors, selectedMajor)
                        else listOf()
                    }
                }

                var selectedYear by remember { mutableStateOf(0) }

                val groups by remember {
                    derivedStateOf {
                        if(workbookState.value.numberOfSheets > 0) {
                            repo.readGroupsFromMajorAndYear(
                                majors,
                                selectedMajor,
                                years,
                                selectedYear
                            )
                        }
                        else listOf()
                    }
                }

                var selectedGroup by remember { mutableStateOf(0) }

                val week by remember {
                    derivedStateOf {
                        if(majors.isNotEmpty() && years.isNotEmpty() && groups.isNotEmpty()){
                            repo.readWeekFromFile(workbookState.value, 0, majors[selectedMajor], years[selectedYear], groups[selectedGroup])
                        }
                        else null
                    }
                }



                Column(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = {
                        lifecycleScope.launch {
                            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                addCategory(Intent.CATEGORY_OPENABLE)
                                type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                            }

                            openFileLauncher.launch(intent)
                        }
                    }) {
                        Text(text = "Open")
                    }
                    majors.forEachIndexed { index, major ->
                        Text(
                            modifier = Modifier
                                .clickable {
                                    selectedYear = 0
                                    selectedGroup = 0
                                    selectedMajor = index
                                },
                            text = major
                        )
                    }
                    Text("---------${if(majors.isNotEmpty())majors[selectedMajor] else "-"}---------")
                    years.forEachIndexed { index, year ->
                        Text(
                            modifier = Modifier
                                .clickable {
                                    selectedGroup = 0
                                    selectedYear = index
                                },
                            text = year
                        )
                    }
                    Text("---------${if(years.isNotEmpty())years[selectedYear] else "-"}---------")
                    groups.forEachIndexed { index, group ->
                        Text(
                            modifier = Modifier
                                .clickable {
                                    selectedGroup = index
                                },
                            text = group
                        )
                    }
                    Text("---------${if(groups.isNotEmpty())groups[selectedGroup] else "-"}---------")
                    if(week != null){
                        week!!.days[0].numeratorLessons.forEach { lesson ->
                            if(lesson == null){
                                Text("-")
                            }
                            else{
                                Text(lesson.lessonName)
                            }
                        }
                    }
                }*/
            }


        }
    }
}