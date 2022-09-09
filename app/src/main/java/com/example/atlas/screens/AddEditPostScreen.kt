package com.example.atlas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.atlas.R
import com.example.atlas.events.AddEditPostEvents
import com.example.atlas.ui.theme.Purple40
import com.example.atlas.util.UiEvents
import com.example.atlas.view_models.AddEditPostViewModel

@Composable
fun AddEditPostScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditPostViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val lottieCompositionSpec by rememberLottieComposition(spec =
    if (viewModel.isUpdate)
        LottieCompositionSpec.RawRes(R.raw.update)
    else
        LottieCompositionSpec.RawRes(R.raw.send)
    )

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvents.OnPopBackStack -> onPopBackStack()
                is UiEvents.ShowSnackBar->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }else->Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (viewModel.isUpdate){
                        viewModel.onEvent(AddEditPostEvents.OnUpdateClicked)
                    } else {
                        viewModel.onEvent(AddEditPostEvents.OnSaveClicked)
                    }
                }
            ) {
                if (viewModel.isUpdate){
                    Icon(imageVector = Icons.Default.Update, contentDescription = "add")
                } else {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "add")
                }
            }
        },
        topBar = {
            TopAppBar (
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                backgroundColor = Purple40
            ){
                LottieAnimation(
                    composition = lottieCompositionSpec,
                    iterations = Int.MAX_VALUE,
                    alignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = viewModel.title,
                onValueChange = {
                    viewModel.onEvent(AddEditPostEvents.OnTittleChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "title")
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = viewModel.description,
                onValueChange = {
                    viewModel.onEvent(AddEditPostEvents.OnDescriptionChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f)
                ,
                placeholder = {
                    Text(text = "description")
                }
            )
        }
    }
}