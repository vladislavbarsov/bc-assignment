package com.example.bcassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bcassignment.ui.screens.BottomSheetScaffoldScreen
import com.example.bcassignment.ui.screens.CustomBottomSheetScreen
import com.example.bcassignment.ui.screens.SheetScreen
import com.example.bcassignment.ui.theme.BCAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BCAssignmentTheme {
                //SheetScreen()
                CustomBottomSheetScreen()
                //BottomSheetScaffoldScreen()
            }
        }
    }
}