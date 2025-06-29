package com.example.bcassignment.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bcassignment.R
import com.example.bcassignment.ui.theme.BCAssignmentTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetScreen(
    modifier: Modifier = Modifier
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    var isSheetVisible by rememberSaveable { mutableStateOf(true) }
    var userInput by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.topbar_title_hello)
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_menu),
                        contentDescription = stringResource(R.string.topbar_menu_icon_desc)
                    )
                },
            )
        },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        isSheetVisible = !isSheetVisible
                    }
                ) {
                    Text(
                        text = "Toggle bottom sheet"
                    )
                }
            }
        }

        if (isSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    isSheetVisible = false
                },
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(top = 64.dp)
                ,
                sheetState = modalBottomSheetState
            ) {
                TextField(
                    value = userInput,
                    onValueChange = { newInput -> userInput = newInput },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .focusRequester(focusRequester)
                )

                LaunchedEffect(modalBottomSheetState.isVisible) {
                    if (modalBottomSheetState.isVisible) {
                        focusRequester.requestFocus()
                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun SheetScreenPreview() {
    BCAssignmentTheme {
        SheetScreen()
    }
}