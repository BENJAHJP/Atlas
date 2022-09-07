package com.example.atlas.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atlas.events.AddEditPostEvents
import com.example.atlas.ui.theme.Purple40
import com.example.atlas.util.UiEvents
import com.example.atlas.view_models.AddEditPostViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditPostScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditPostViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Purple40, shape = RectangleShape)
                    .size(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (viewModel.isUpdate){
                    Text(
                        text = "update",
                        fontSize = 30.sp,
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "save",
                        fontSize = 30.sp,
                        color = Color.White
                    )
                }
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
                modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight(1f)
                ,
                placeholder = {
                    Text(text = "description")
                }
            )
        }
    }
}