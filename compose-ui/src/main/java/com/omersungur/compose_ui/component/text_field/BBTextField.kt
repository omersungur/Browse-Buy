package com.omersungur.compose_ui.component.text_field

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BBOutlinedTextField(
    modifier: Modifier = Modifier,
    textFieldValue: String,
    textFieldHint: String?,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLines: Int = 1,
    isError: Boolean = false,
    singleLine: Boolean = true,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    rowContent: @Composable () -> Unit = { /* sonar comment */ },
    trailingIconContent: @Composable () -> Unit = { /* sonar comment */ },
    columnContent: @Composable () -> Unit = { /* sonar comment */ },
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = textFieldValue)

            rowContent()
        }

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onValueChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                if (textFieldHint != null) {
                    Text(text = textFieldHint)
                }
            },
            maxLines = maxLines,
            singleLine = singleLine,
            trailingIcon = {
                trailingIconContent()
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            isError = isError,
            colors = colors,
            enabled = enabled,
            readOnly = readOnly,
            interactionSource = interactionSource,
            label = label,
            leadingIcon = leadingIcon,
            prefix = prefix,
            suffix = suffix,
            supportingText = supportingText,
        )

        Spacer(modifier = Modifier.height(4.dp))

        columnContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun DOutlinedTextFieldPreview() {
    val localFocusManager = LocalFocusManager.current
    var isPasswordShowing by remember { mutableStateOf(false) }
    var visualTransformation: VisualTransformation by remember { mutableStateOf(PasswordVisualTransformation()) }

    Column(Modifier.fillMaxSize()) {
        BBOutlinedTextField(
            textFieldValue = "Email Address",
            onValueChange = {
                // sonar comment
            },
            textFieldHint = "Rhebhek@gmail.com",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    localFocusManager.moveFocus(FocusDirection.Down)
                },
            ),
            isError = false,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Black,
            ),
        )

        BBOutlinedTextField(
            textFieldValue = "Password",
            onValueChange = {
                // sonar comment
            },
            textFieldHint = "*********",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    localFocusManager.moveFocus(FocusDirection.Down)
                },
            ),
            visualTransformation = visualTransformation,
            rowContent = {
                Text(text = "Forgot password")
            },
            isError = false,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Black,
            ),
            trailingIconContent = {
                if (!isPasswordShowing) {
                    Icon(
                        modifier = Modifier.clickable {
                            isPasswordShowing = !isPasswordShowing
                            visualTransformation = VisualTransformation.None

                        },
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "Visibility Off Icon",
                    )
                } else {
                    Icon(
                        modifier = Modifier.clickable {
                            isPasswordShowing = !isPasswordShowing
                            visualTransformation = PasswordVisualTransformation()
                        },
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "Visibility Icon",
                    )
                }
            },
            columnContent = {
                Text(text = "Please enter correct password!")
            },
        )
    }
}