package org.penakelex.rating_physics.enter.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.penakelex.rating_physics.R

@Composable
fun DeveloperInformation(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            append(
                AnnotatedString(
                    text = "${stringResource(R.string.developer_label)}: ",
                    spanStyle = SpanStyle(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            )
            append(stringResource(R.string.developer_name))
        },
        fontSize = 16.sp,
    )
}