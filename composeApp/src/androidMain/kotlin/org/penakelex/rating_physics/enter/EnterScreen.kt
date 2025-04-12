package org.penakelex.rating_physics.enter

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.penakelex.rating_physics.enter.components.DrawerContent
import org.penakelex.rating_physics.enter.components.FileSelectionButton
import org.penakelex.rating_physics.enter.components.MenuIcon
import org.penakelex.rating_physics.enter.components.PasswordInputField
import org.penakelex.rating_physics.enter.components.ThemeSelectionDialog
import org.penakelex.rating_physics.enter.components.ViewStudentDataButton
import org.penakelex.rating_physics.settings.SettingsEvent
import org.penakelex.rating_physics.settings.SettingsViewModel
import org.penakelex.rating_physics.util.Screen

const val TELEGRAM_URL = "https://t.me/penakelex"

@Composable
fun EnterScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel,
    viewModel: EnterViewModel = koinViewModel(),
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EnterViewModel.UIEvent.ValidateData -> {
                    val (studentPassword, fileName) = event
                    val route = Screen.RatingDataScreen.route

                    navController.navigate(
                        route = "${route}?password=${studentPassword}&filePath=${fileName}"
                    )
                }

                is EnterViewModel.UIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val password = viewModel.password.value
    val file = viewModel.file.value

    val fileSelectLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            viewModel.onEvent(EnterEvent.FileSelected(uri))
        }
    )

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val theme = settingsViewModel.themeState.collectAsState()

    val isThemeSelectionDialogOpened = remember {
        mutableStateOf(false)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            val version = context.packageManager
                .getPackageInfo(context.packageName, 0).versionName
                .toString()

            val openUrlLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
                onResult = {
                    it.data?.let { data ->
                        context.startActivity(data)
                    }
                }
            )

            val applicationIconPainter = rememberAsyncImagePainter(
                context.packageManager.getApplicationIcon(context.packageName)
            )

            DrawerContent(
                version = version,
                applicationIconPainter = applicationIconPainter,
                theme = theme.value,
                isThemeSelectionDialogOpened = isThemeSelectionDialogOpened,
                openUrlLauncher = openUrlLauncher,
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager.clearFocus()
                        },
                    )
                },
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                MenuIcon(
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        },
                )

                Spacer(modifier = Modifier.height(12.dp))

                PasswordInputField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onValueChange = {
                        viewModel.onEvent(EnterEvent.EnteredPassword(it))
                    },
                    password = password.value,
                    isPasswordIncorrect = !password.isCorrect,
                )

                Spacer(modifier = Modifier.height(12.dp))

                FileSelectionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .clickable {
                            fileSelectLauncher.launch(arrayOf("application/octet-stream"))
                        },
                    fileName = file.name,
                    isFileValid = file.isValid,
                )

                Spacer(modifier = Modifier.weight(1f))

                ViewStudentDataButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding(),
                    onClick = {
                        viewModel.onEvent(EnterEvent.ValidateData)
                    },
                )
            }
        }
    }

    if (isThemeSelectionDialogOpened.value) {
        ThemeSelectionDialog(
            theme = theme.value,
            onDismiss = {
                isThemeSelectionDialogOpened.value = false
            },
            onAccept = { newTheme ->
                if (newTheme != theme.value)
                    settingsViewModel.onEvent(SettingsEvent.ThemeChanged(newTheme))

                isThemeSelectionDialogOpened.value = false
            }
        )
    }
}