package ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.Platinum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(clickHandler: (Boolean) -> Unit) {
    val checkedList = remember { mutableStateListOf<Int>() }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            "Sort by",
            modifier = Modifier.fillMaxWidth(),
            color = Platinum
        )
        SingleChoiceSegmentedButtonRow() {
            SegmentedButton(true, onClick = { }, SegmentedButtonDefaults.baseShape) {
                Text(
                    "<100m",
                    color = Platinum
                )
            }
            SegmentedButton(false, onClick = { }, SegmentedButtonDefaults.baseShape) {
                Text(
                    "<100m",
                    color = Platinum
                )
            }
            SegmentedButton(false, onClick = { }, SegmentedButtonDefaults.baseShape) {
                Text(
                    "<100m",
                    color = Platinum
                )
            }
        }
        Text(
            "Sort by",
            modifier = Modifier.fillMaxWidth(),
            color = Platinum
        )
        SingleChoiceSegmentedButtonRow() {
            SegmentedButton(
                true,
                onClick = { clickHandler.invoke(true) },
                SegmentedButtonDefaults.baseShape
            ) {
                Text(
                    "Newest first",
                    color = Platinum
                )
            }
            SegmentedButton(
                false,
                onClick = { clickHandler.invoke(false) },
                SegmentedButtonDefaults.baseShape
            ) {
                Text(
                    "Oldest first",
                    color = Platinum
                )
            }
        }
        Button(onClick = { }) {
            Text(
                "Apply",
                color = Platinum
            )
        }
    }
}