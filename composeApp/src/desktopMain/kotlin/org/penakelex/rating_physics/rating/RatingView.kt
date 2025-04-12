package org.penakelex.rating_physics.rating

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.penakelex.rating_physics.rating.components.ListRatingInformation
import org.penakelex.rating_physics.rating.components.MainRatingInformation

@Composable
fun rememberRatingStateHolder(): RatingStateHolder {
    val stateHolder = koinInject<RatingStateHolder>()

    val state = remember {
        stateHolder
    }

    DisposableEffect(Unit) {
        onDispose {
            state.cancel()
        }
    }

    return state
}

@Composable
fun RatingView(
    stateHolder: RatingStateHolder,
    modifier: Modifier = Modifier,
) {
    when (val data = stateHolder.data.value) {
        DataState.NotYetSearched -> {
            Spacer(modifier)
        }

        DataState.LoadedData -> {
            val ratingData = stateHolder.ratingData

            val dataList = listOf(
                "ФИО" to ratingData.fullName,
                "Группа" to ratingData.group,
                "Сумма" to ratingData.summary,
                "Рейтинг в группе" to ratingData.ratingGroup,
                "Рейтинг в потоке" to ratingData.ratingFlow,
                "Коллоквиум" to ratingData.colloquium,
                "РГЗ/Контрольные работы" to ratingData.cgtCw,
                "Лабораторные работы" to ratingData.lw,
                "Индивидуальные задания" to ratingData.it,
                "Реферат" to ratingData.essay,
                "НИРС" to ratingData.nirs,
                "Сумма по практикам" to ratingData.sumPractice,
                "Пропуски" to ratingData.omissions,
            ).map { (name, value) ->
                val dataString = value?.let {
                    when (value) {
                        is Float -> "%.2f".format(value)
                        else -> value.toString()
                    }
                } ?: "Нет данных"

                name to dataString
            }

            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MainRatingInformation(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    data = dataList,
                )

                ListRatingInformation(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    name = "Практические занятия",
                    data = ratingData.practicalLessons.map { (notAttend, tasks) ->
                        buildString {
                            append(tasks ?: "Нет данных")
                            if (notAttend) {
                                append(' ')
                                append("(Не был(-а))")
                            }
                        }
                    },
                )

                ListRatingInformation(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    name = "Практические занятия",
                    data = ratingData.cgts.map {
                        it?.toString() ?: "Нет данных"
                    },
                )
            }
        }

        else -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                when (data) {
                    DataState.LoadingData -> CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                    )

                    DataState.NoLoadedData -> Text(
                        text = "Не найден студент с данным паролем",
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    DataState.CantAccessServer -> Text(
                        text = "Не удаётся подключиться к серверу",
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    else -> System.err.println("Unreachable state")
                }
            }
        }
    }
}