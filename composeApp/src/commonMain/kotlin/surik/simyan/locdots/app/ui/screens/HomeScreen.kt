package surik.simyan.locdots.app.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import surik.simyan.locdots.app.koinViewModel
import surik.simyan.locdots.app.ui.EerieBlack
import surik.simyan.locdots.app.ui.Gray
import surik.simyan.locdots.app.ui.Platinum
import surik.simyan.locdots.app.ui.components.BottomSheetContent
import surik.simyan.locdots.app.ui.components.EmptyStateContent
import surik.simyan.locdots.app.ui.components.MessageCard
import surik.simyan.locdots.app.ui.components.MinimalDialog
import surik.simyan.locdots.shared.data.DotSort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel = koinViewModel<HomeScreenViewModel>()
    val dots by viewModel.dots.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var sortingType by remember { mutableStateOf(DotSort.PostDate) }
    Scaffold(
        bottomBar = {
            BottomAppBar(actions = {
                IconButton(
                    onClick = {
                        showBottomSheet = true
                    }, colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Platinum
                    )
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Sort,
                        contentDescription = "Localized description",
                    )
                }
                IconButton(
                    onClick = {
                        viewModel.getItems()
                    }, colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Platinum
                    )
                ) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = "Localized description",
                    )
                }
            }, floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("create") },
                    containerColor = Platinum,
                    contentColor = EerieBlack
                ) {
                    Icon(Icons.Filled.Edit, "Floating action button.")
                }
            }, containerColor = EerieBlack
            )
        },
        containerColor = Gray
    ) { innerPadding ->

        when (val result = dots) {
            HomeScreenViewModel.HomeScreenState.Idle -> Unit
            HomeScreenViewModel.HomeScreenState.Loading -> MinimalDialog("Loading", true)
            is HomeScreenViewModel.HomeScreenState.Error -> {
                MinimalDialog("Something went wrong. Please try again", false)
            }

            is HomeScreenViewModel.HomeScreenState.Success -> {
                if (result.items.isEmpty()) {
                    EmptyStateContent(
                        onCreateDot = {
                            navController.navigate("create")
                        },
                        innerPadding
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        items(result.items) {
                            MessageCard(it)
                        }
                    }
                    if (showBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                showBottomSheet = false
                            }, sheetState = sheetState, containerColor = EerieBlack
                        ) {
                            val clickHandler: (DotSort) -> Unit = { sortingType ->
                                viewModel.sortType.update { sortingType }
                                viewModel.getItems()
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                }
                            }
                            BottomSheetContent(clickHandler)
                        }
                    }
                }
            }
        }
    }
}