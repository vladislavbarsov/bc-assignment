package com.example.bcassignment.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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
                    Text(text = stringResource(R.string.topbar_title_hello))
                },
                navigationIcon = {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_menu),
                            contentDescription = stringResource(R.string.topbar_menu_icon_desc)
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isSheetVisible = !isSheetVisible }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                    contentDescription = stringResource(R.string.icon_desc_add)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                "Your message are listed below"
            )
        }

        if (isSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    isSheetVisible = false
                },
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(top = 64.dp)
                    .fillMaxHeight(),
                sheetState = modalBottomSheetState,
                scrimColor = Color.Transparent,
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextField(
                        value = userInput,
                        onValueChange = { newInput -> userInput = newInput },
                        modifier = Modifier
                            .weight(1f)
                            .windowInsetsPadding(WindowInsets.navigationBars)
                            .focusRequester(focusRequester),
                        placeholder = {
                            Text(text = stringResource(R.string.text_field_placeholder))
                        },
                        colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                    // Not using IconButton because it has default paddings.
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                        contentDescription = stringResource(R.string.icon_desc_clear),
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .padding(end = 16.dp)
                            .clickable {
                                userInput = ""
                            },
                    )
                }

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