package com.example.bank_card

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.bank_card.ViewModel.CardState
import com.example.bank_card.ViewModel.CardViewModel
import com.example.bank_card.ui.theme.Bank_cardTheme

@Composable
fun BackCard(
    state: CardState,
    viewModel: CardViewModel
) {
    Column(
        modifier = Modifier
            .width(450.dp)
            .height(265.dp)
            .padding(start = 100.dp, top = 50.dp)
            .background(
                color = Color.Gray.copy(alpha = 0.4f),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            EnterValue(
                value = state.cvv,
                placeholder = stringResource(R.string.cvv),
                modifier = Modifier
                    .padding(end = 20.dp)
                    .width(55.dp)
                    .height(40.dp),
                stopValue = 3,
                type = Type.NUMBER,
                error = state.cvvError,
                isCvv = true,
                cvvModifier = Modifier
                    .padding(4.dp)
                    .width(55.dp)
                    .height(55.dp),
                onValueChange = { input -> viewModel.onCvvChange(input) }
            )
        }
    }
}

@Composable
fun FrontCard(
    state: CardState,
    viewModel: CardViewModel
) {

    Column(
        modifier = Modifier
            .width(450.dp)
            .height(265.dp)
            .padding(end = 100.dp, bottom = 50.dp)
            .background(
                brush = Brush.linearGradient(colors = listOf(Color.Gray, Color.Black)),
                shape = RoundedCornerShape(20.dp)
            )
            .zIndex(1f)
    ) {

        Spacer(modifier = Modifier.height(15.dp))

        CardNumberField(
            number = state.number,
            placeholder = stringResource(R.string.zero),
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .height(40.dp),
            stopValue = 16,
            error = state.numberError,
            onValueChange = { viewModel.onNumberChange(it) }
        )

        Text(
            text = stringResource(R.string.term),
            modifier = Modifier
                .padding(start = 10.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(modifier = Modifier.padding(start = 10.dp)) {
            EnterValue(
                value = state.month,
                placeholder = stringResource(R.string.month),
                modifier = Modifier
                    .width(130.dp)
                    .height(25.dp),
                stopValue = 2,
                type = Type.DATE,
                error = state.monthError,
                onValueChange = { input -> viewModel.onMonthChange(input) }
            )

            Spacer(modifier = Modifier.width(10.dp))

            EnterValue(
                value = state.year,
                placeholder = stringResource(R.string.year),
                modifier = Modifier
                    .width(130.dp)
                    .height(25.dp),
                stopValue = 2,
                type = Type.DATE,
                error = state.yearError,
                onValueChange = { input -> viewModel.onYearChange(input) }
            )
        }

        EnterValue(
            value = state.name,
            placeholder = stringResource(R.string.name),
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .height(40.dp),
            stopValue = 24,
            type = Type.TEXT,
            error = state.nameError,
            onValueChange = { input -> viewModel.onNameChange(input) }
        )
    }
}

@Composable
fun SaveButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) Color.Green else Color.Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(bottom = 15.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = stringResource(R.string.save),
            fontSize = 18.sp,
            color = Color.White
        )
    }
}


@Composable
fun CardSum(
    viewModel: CardViewModel
) {
    val context = LocalContext.current

    Column {
        Spacer(modifier = Modifier.height(150.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(550.dp)
        ) {
            BackCard(viewModel.state.value, viewModel)
            FrontCard(viewModel.state.value, viewModel)
        }

        Spacer(Modifier.weight(1f))

        SaveButton(viewModel.state.value.isFormValid) { Toast.makeText(
            context,
            "Успешно сохранено",
            Toast.LENGTH_SHORT
        ).show() }
    }
}

@Composable
@Preview(showBackground = true)
fun ShowCards() {
    val viewModel = remember { CardViewModel() }
    Bank_cardTheme {

        CardSum(viewModel)

    }
}
