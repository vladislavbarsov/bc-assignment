package com.example.bcassignment.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bcassignment.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheetScreen(
    modifier: Modifier = Modifier
) {
    var userInput by remember { mutableStateOf("") }
    val bottomSheetIsVisible by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }

    val bottomSheetModifier = Modifier
        .imePadding()

    val imeHeight = with (LocalDensity.current) {
        WindowInsets.ime.getBottom(LocalDensity.current).toDp()
    }
    val statusBarHeight = with (LocalDensity.current) {
        WindowInsets.statusBars.getTop(LocalDensity.current).toDp()
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val topAppBarHeight = 56.dp

    val minBottomSheetHeight = 130.dp
    val maxBottomSheetHeight = if (imeHeight == 0.dp) {
        screenHeight - topAppBarHeight - statusBarHeight - 10.dp
    } else {
        screenHeight - topAppBarHeight - statusBarHeight - imeHeight - 10.dp
    }
    var currentBottomSheetHeight by remember { mutableStateOf(minBottomSheetHeight) }

    val animatedSheetHeight by animateDpAsState(
        targetValue = currentBottomSheetHeight,
        label = "bottomSheetHeightAnimation"
    )

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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Text("current viewport height - $screenHeight")
            Text("ime keyboard height - $imeHeight")
            Text("current height - $currentBottomSheetHeight")
            Text("current max bottom sheet height - $maxBottomSheetHeight")
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (bottomSheetIsVisible) {
                CustomBottomSheet(
                    modifier = bottomSheetModifier,
                    userInput = userInput,
                    onValueChange = { newInput -> userInput = newInput },
                    onValueCleared = { userInput = "" },
                    focusRequester = focusRequester,
                    sheetHeight = animatedSheetHeight,
                    onHeightChanged = { newHeight ->
                        val newTargetHeight = when {
                            imeHeight > 0.dp && newHeight > 250.dp -> maxBottomSheetHeight
                            currentBottomSheetHeight == maxBottomSheetHeight && newHeight < (maxBottomSheetHeight - 50.dp)  -> {
                                minBottomSheetHeight
                            }
                            imeHeight == 0.dp -> minBottomSheetHeight
                            else -> newHeight
                        }

                        currentBottomSheetHeight = newTargetHeight
                            .coerceIn(minBottomSheetHeight, maxBottomSheetHeight)
                    },
                )

                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
    modifier: Modifier = Modifier,
    userInput: String,
    onValueChange: (String) -> Unit,
    onValueCleared: () -> Unit,
    focusRequester: FocusRequester,
    sheetHeight: Dp,
    onHeightChanged: (Dp) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(sheetHeight)
            .drawBehind {
                drawRoundRect(
                    color = Color.Gray.copy(alpha = 0.2f),
                    topLeft = Offset(x = 0f, y = -10f),
                    cornerRadius = CornerRadius(
                        x = 16.dp.toPx(),
                        y = 16.dp.toPx()
                    ),
                )
            }
        ,
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        color = Color.Gray.copy(alpha = 0.2f)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            val newHeight = sheetHeight - (delta * 1.2).dp
                            onHeightChanged(newHeight)
                        }
                    )
                ,
                horizontalArrangement = Arrangement.Center
            ) {
                BottomSheetDefaults.DragHandle()
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                verticalAlignment = Alignment.Bottom
            ) {
                TextField(
                    value = userInput,
                    onValueChange = { newInput -> onValueChange(newInput) },
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
                Spacer(modifier = Modifier.width(16.dp))
                // Not using IconButton because it has default paddings.
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                    contentDescription = stringResource(R.string.icon_desc_clear),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clickable { onValueCleared() },
                )
            }
        }
    }
}

@Preview
@Composable
private fun CustomBottomSheetScreenPreview() {
    CustomBottomSheetScreen()
}