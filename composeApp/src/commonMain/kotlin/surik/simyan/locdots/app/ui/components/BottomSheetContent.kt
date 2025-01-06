package surik.simyan.locdots.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import surik.simyan.locdots.app.ui.EerieBlack
import surik.simyan.locdots.app.ui.Gray
import surik.simyan.locdots.app.ui.Jet
import surik.simyan.locdots.app.ui.Platinum
import surik.simyan.locdots.shared.data.DotSort

@Composable
fun BottomSheetContent(clickHandler: (DotSort) -> Unit) {
    var selectedIndex by remember { mutableStateOf(0) }
    val options = listOf(
        DotSort.PostDistance, DotSort.PostDate
    )
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            "Sort by",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            color = Platinum,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        ) {
            options.forEachIndexed { index, option ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                    onClick = { selectedIndex = index },
                    selected = index == selectedIndex,
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = Jet,
                        activeContentColor = Platinum,
                        activeBorderColor = Color.Transparent,
                        inactiveContainerColor = Gray,
                        inactiveContentColor = Platinum,
                        inactiveBorderColor = Color.Transparent
                    )
                ) {
                    Text(option.value)
                }
            }
        }
        Button(
            onClick = { clickHandler(options[selectedIndex]) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Platinum
            )
        ) {
            Text(
                "Apply",
                modifier = Modifier
                    .fillMaxWidth(),
                color = EerieBlack,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}