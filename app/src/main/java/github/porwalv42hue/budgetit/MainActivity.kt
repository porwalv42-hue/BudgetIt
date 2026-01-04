package github.porwalv42hue.budgetit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import github.porwalv42hue.budgetit.ui.theme.BudgetItTheme

class MainActivity : ComponentActivity() {
    private val budgetViewModel: BudgetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetItTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {FloatingActionContainer()}
                ) { innerPadding ->
                    Content(
                        modifier = Modifier.padding(innerPadding),
                        budgetViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun FloatingActionContainer() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Income", "Household", "Investment", "Donation", "Trip", "Grooming")
    var selectedIndex by remember { mutableStateOf(0) }
    var amount by remember { mutableStateOf("0") }

    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(10.dp)).fillMaxWidth(0.9f)) {

        Row(modifier = Modifier.padding(10.dp)) {

            Column(modifier = Modifier.weight(6.5f)) {

                TextField(
                    value = amount, singleLine = true,
                    onValueChange = { amount = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Box {
                    ElevatedButton(onClick = { expanded = true },
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.fillMaxWidth()) {
                        Row() {
                            Text(items[selectedIndex])
                            Icon(Icons.Default.ArrowDropDown,
                                modifier = Modifier.width(30.dp).height(30.dp),
                                contentDescription = "Drop Down Arrow")
                        }
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        items.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                text = { Text(s) },
                                onClick = {
                                    selectedIndex = index
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            ElevatedButton(onClick = {},
                modifier = Modifier.weight(3.5f).align(Alignment.CenterVertically)) {
                Text("Add")
            }
        }
    }
}

enum class DivisionType {
    INCOME, HOUSEHOLD, INVESTMENT, DONATION, TRIP, GROOMING
}

@Composable
fun Content(modifier: Modifier = Modifier, budgetViewModel: BudgetViewModel) {
    Column {
        Text(
            text = "Remaining Budget:",
            modifier = modifier
        )

        LazyColumn {
            items(DivisionType.entries) { divisionType ->
                DivisionEntry(divisionType, budgetViewModel)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun DivisionEntry(type: DivisionType, budgetViewModel: BudgetViewModel) {
    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(10.dp)).padding(10.dp).fillMaxWidth()) {
        Text("${getDisplayName(type)}: ${budgetViewModel.getBudget(type)}",
            color = MaterialTheme.colorScheme.onSecondary)
    }
}

fun getDisplayName(type: DivisionType): String {
    return type.name.lowercase().replaceFirstChar { it.titlecase() }
}