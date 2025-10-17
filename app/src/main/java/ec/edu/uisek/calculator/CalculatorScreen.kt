package ec.edu.uisek.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.calculator.ui.theme.Purple40

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = viewModel()
) {
    val state = viewModel.state
    val displayFontSize = if (state.currentInput.length > 9) 48.sp else 72.sp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = state.fullExpression,
                modifier = Modifier.padding(bottom = 8.dp),
                textAlign = TextAlign.End,
                fontSize = 40.sp,
                color = Color.Gray,
                maxLines = 1
            )
            Text(
                text = state.currentInput,
                textAlign = TextAlign.End,
                fontSize = displayFontSize,
                color = Color.White,
                fontWeight = FontWeight.Light,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(24.dp))

            // -- Botones de la Calculadora --
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Fila 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CalculatorButton(label = "AC", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.AllClear) }
                    CalculatorButton(label = "C", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.Clear) }
                    CalculatorButton(label = "÷", modifier = Modifier.weight(2f)) { viewModel.onEvent(CalculatorEvent.Operator("÷")) }
                }
                // Fila 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CalculatorButton(label = "7", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.Number("7")) }
                    CalculatorButton(label = "8", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.Number("8")) }
                    CalculatorButton(label = "9", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.Number("9")) }
                    CalculatorButton(label = "×", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.Operator("×")) }
                }
                // Fila 3
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CalculatorButton(label = "4", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.Number("4")) }
                    CalculatorButton(label = "5", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.Number("5")) }
                    CalculatorButton(label = "6", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.Number("6")) }
                    CalculatorButton(label = "−", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.Operator("−")) }
                }
                // Fila 4
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CalculatorButton(label = "1", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.Number("1")) }
                    CalculatorButton(label = "2", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.Number("2")) }
                    CalculatorButton(label = "3", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.Number("3")) }
                    CalculatorButton(label = "+", modifier = Modifier.weight(1f)) { viewModel.onEvent(CalculatorEvent.Operator("+")) }
                }
                // Fila 5
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CalculatorButton(
                        label = "0",
                        modifier = Modifier.weight(2f)
                    ) { viewModel.onEvent(CalculatorEvent.Number("0")) }
                    CalculatorButton(
                        label = ".",
                        modifier = Modifier.weight(1f)
                    ) { viewModel.onEvent(CalculatorEvent.Decimal) }
                    CalculatorButton(
                        label = "=",
                        modifier = Modifier.weight(1f)
                    ) { viewModel.onEvent(CalculatorEvent.Calculate) }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val backgroundColor = when (label) {
        in "0".."9", "." -> Color(0xFF343434)
        "AC", "C" -> Color(0xFFA5A5A5)
        else -> Purple40
    }

    val textColor = when (label) {
        "AC", "C" -> Color.Black
        else -> Color.White
    }

    Box(
        modifier = modifier
            .aspectRatio(if (label == "0") 2f else if (label == "÷") 2f else 1f)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorScreen()
}