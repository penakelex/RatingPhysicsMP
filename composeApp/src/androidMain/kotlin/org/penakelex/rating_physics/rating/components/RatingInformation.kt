package org.penakelex.rating_physics.rating.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.penakelex.rating_physics.R
import org.penakelex.rating_physics.domain.model.RatingData

@Composable
fun RatingInformation(
    ratingData: RatingData,
) {
    val isPracticalLessonsCategoryOpened = remember {
        mutableStateOf(false)
    }

    val isCgtsCategoryOpened = remember {
        mutableStateOf(false)
    }

    val dataList = listOf(
        stringResource(R.string.full_name_title) to ratingData.fullName,
        stringResource(R.string.group_title) to ratingData.group,
        stringResource(R.string.summary_title) to ratingData.summary,
        stringResource(R.string.rating_group_title) to ratingData.ratingGroup,
        stringResource(R.string.rating_flow_title) to ratingData.ratingFlow,
        stringResource(R.string.colloquium_title) to ratingData.colloquium,
        stringResource(R.string.cgt_cw_title) to ratingData.cgtCw,
        stringResource(R.string.lw_title) to ratingData.lw,
        stringResource(R.string.it_title) to ratingData.it,
        stringResource(R.string.essay_title) to ratingData.essay,
        stringResource(R.string.nirs_title) to ratingData.nirs,
        stringResource(R.string.sum_practice_title) to ratingData.sumPractice,
        stringResource(R.string.omissions_title) to ratingData.omissions,
    ).map { (naming, data) ->
        val dataString = data?.let {
            when (data) {
                is Float -> "%.2f".format(data)
                else -> data.toString()
            }
        } ?: stringResource(R.string.no_data_label)

        naming to dataString
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(
            items = dataList,
            key = { _, it -> it.first },
        ) { index, (naming, value) ->
            Text(
                text = buildAnnotatedString {
                    append(
                        AnnotatedString(
                            "$naming: ",
                            SpanStyle(fontWeight = FontWeight.SemiBold),
                        )
                    )
                    append(value)
                },
                fontSize = 28.sp,
                color = Color.Black,
            )

            if (index != dataList.lastIndex)
                Spacer(modifier = Modifier.height(6.dp))
        }

        item {
            val values = ratingData.practicalLessons
                .map { (notAttend, tasks) ->
                    buildString {
                        append(tasks ?: stringResource(R.string.no_data_label))
                        if (notAttend) {
                            append(' ')
                            append(stringResource(R.string.was_not_in_class_label))
                        }
                    }
                }

            ListRatingInformation(
                modifier = Modifier
                    .padding(top = 6.dp),
                onClick = {
                    isPracticalLessonsCategoryOpened.value =
                        !isPracticalLessonsCategoryOpened.value
                },
                isOpened = isPracticalLessonsCategoryOpened.value,
                name = stringResource(R.string.practical_lessons_title),
                categoryValues = values,
            )
        }

        item {
            ListRatingInformation(
                modifier = Modifier
                    .padding(top = 6.dp),
                onClick = {
                    isCgtsCategoryOpened.value = !isCgtsCategoryOpened.value
                },
                isOpened = isCgtsCategoryOpened.value,
                name = stringResource(R.string.cgt_title),
                categoryValues = ratingData.cgts.map {
                    it?.toString() ?: stringResource(R.string.no_data_label)
                },
            )
        }
    }
}