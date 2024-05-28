package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import koinViewModel
import ui.EerieBlack
import ui.Gray
import ui.Platinum
import ui.components.MinimalDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(navController: NavHostController) {
    val viewModel = koinViewModel<MessageScreenViewModel>()
    val uploadState by viewModel.uploadState.collectAsState()
    val maxChar = 500
    var text by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = EerieBlack,
                    titleContentColor = Platinum,
                ),
                title = {
                    Text(
                        text = "${text.length} / $maxChar",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Platinum
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Platinum
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(Gray)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TextField(
                value = text,
                onValueChange = { if (it.length <= maxChar) text = it },
                modifier = Modifier
                    .heightIn(300.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = EerieBlack,
                    focusedContainerColor = Gray,
                    unfocusedContainerColor = Gray,
                    focusedTextColor = Platinum,
                    unfocusedTextColor = Platinum
                ),
            )
        }
        when (val result = uploadState) {
            MessageScreenViewModel.MessageScreenState.Idle -> Unit
            MessageScreenViewModel.MessageScreenState.Loading -> MinimalDialog(
                "Uploading dot",
                true
            )

            is MessageScreenViewModel.MessageScreenState.Error -> {
                MinimalDialog("Something went wrong. Please try again", false)
            }

            MessageScreenViewModel.MessageScreenState.Success -> MinimalDialog(
                "Dot successfully uploaded",
                false
            ) {
                navController.navigateUp()
            }
        }
    }
}