package com.example.bank_card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.onFocusChanged

@Composable
fun EnterValue(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    stopValue: Int,
    type: Type,
    error: Int? = null,
    isCvv: Boolean = false,
    cvvModifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    Column {

        Box(
            modifier = modifier
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .border(
                    1.dp,
                    if (error != null) Color.Red else Color.Gray,
                    shape = RoundedCornerShape(10.dp)
                )
                .height(50.dp),

            contentAlignment = Alignment.CenterStart
        ) {

            BasicTextField(
                value = value,

                onValueChange = { input ->

                    val filtered = when (type) {

                        Type.NUMBER, Type.DATE -> {
                            input.filter { it.isDigit() }
                                .take(stopValue)
                        }

                        Type.TEXT -> {
                            input.filter {
                                it in 'A'..'Z' || it in 'a'..'z' || it == ' '
                            }
                                .let { text ->
                                    if (text.count { it == ' ' } > 1)
                                        text.dropLast(1)
                                    else text
                                }
                                .take(stopValue)
                        }
                    }

                    onValueChange(filtered)
                },

                singleLine = true,

                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                ),

                keyboardOptions = KeyboardOptions(
                    keyboardType =
                        if (type == Type.NUMBER || type == Type.DATE)
                            KeyboardType.Number
                        else
                            KeyboardType.Text
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .align(Alignment.Center)

                    .onFocusChanged { focusState ->

                        if (!focusState.isFocused) {

                            if (type == Type.DATE && value.length == 1) {
                                onValueChange("0$value")
                            }
                        }
                    }
            )

            // Placeholder
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .align(Alignment.Center)
                )
            }
        }

        // Ошибка или место под неё
        if (error != null) {
            Text(
                text = stringResource(error),
                color = Color.Red,
                fontSize = 10.sp,
                modifier = if (!isCvv)
                    modifier.padding(start = 4.dp)
                else
                    cvvModifier,
                softWrap = true,
                lineHeight = 12.sp
            )
        } else {
            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}



@Composable
fun CardNumberField(
    number: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    stopValue: Int = 16,
    error: Int? = null,
    onValueChange: (String) -> Unit
) {
    val formatted = number.chunked(4).joinToString(" ")

    var textFieldValue by remember(number) {
        mutableStateOf(
            androidx.compose.ui.text.input.TextFieldValue(
                text = formatted,
                selection = androidx.compose.ui.text.TextRange(formatted.length)
            )
        )
    }

    Column {
        Box(
            modifier = modifier
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .border(1.dp, if (error != null) Color.Red else Color.Gray, shape = RoundedCornerShape(10.dp))
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = textFieldValue,
                onValueChange = { input ->
                    val digits = input.text.filter { it.isDigit() }.take(stopValue)
                    val display = digits.chunked(4).joinToString(" ")

                    textFieldValue = androidx.compose.ui.text.input.TextFieldValue(
                        text = display,
                        selection = androidx.compose.ui.text.TextRange(display.length)
                    )

                    onValueChange(digits)
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            if (number.isEmpty()) {
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        if (error != null) {
            Text(
                text = stringResource(error),
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .height(20.dp)
                    .padding(start = 10.dp),
                softWrap = true
            )
        } else {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

