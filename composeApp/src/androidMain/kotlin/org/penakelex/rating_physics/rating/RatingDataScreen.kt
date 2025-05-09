package org.penakelex.rating_physics.rating

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import org.penakelex.rating_physics.R
import org.penakelex.rating_physics.rating.components.RatingInformation

@Composable
fun RatingDataScreen(
    navController: NavController,
    viewModel: RatingDataViewModel = koinViewModel(),
) {
    val dataState = viewModel.data.value

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        navController.navigateUp()
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .size(36.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Arrow back icon",
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = stringResource(R.string.back_button_text),
                    fontSize = 18.sp,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            when (dataState) {
                DataState.LoadedData -> {
                    RatingInformation(viewModel.ratingData)
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        when (dataState) {
                            DataState.LoadingData -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.width(64.dp),
                                )
                            }

                            DataState.NoLoadedData -> {
                                Text(text = stringResource(R.string.student_not_found_label))
                            }

                            else -> {
                                Text(text = stringResource(R.string.cant_access_server_label))
                            }
                        }
                    }
                }
            }
        }
    }
}