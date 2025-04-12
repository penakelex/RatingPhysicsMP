package org.penakelex.rating_physics.rating.components

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MainRatingInformation(
    data: List<Pair<String, String>>,
    modifier: Modifier = Modifier,
) {
    val textStyle = MaterialTheme.typography.bodyMedium.toSpanStyle()

    val lazyListState = rememberLazyListState()

    Box(
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyListState,
        ) {
            items(
                count = data.size,
                key = { data[it].first },
            ) {
                val (name, data) = data[it]

                Text(
                    text = buildAnnotatedString {
                        append(
                            AnnotatedString(
                                text = "$name: ",
                                spanStyle = textStyle.copy(
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                        )

                        append(
                            AnnotatedString(
                                text = data,
                                spanStyle = textStyle,
                            )
                        )
                    }
                )

                if (it != data.lastIndex)
                    Spacer(modifier = Modifier.height(4.dp))
            }
        }

        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = lazyListState
            )
        )
    }
}