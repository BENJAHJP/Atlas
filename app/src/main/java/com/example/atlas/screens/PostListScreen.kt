package com.example.atlas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
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
import com.example.atlas.events.PostListEvents
import com.example.atlas.ui.theme.Purple40
import com.example.atlas.util.UiEvents
import com.example.atlas.view_models.PostListViewModel


@Composable
fun PostListScreen(
    onNavigate: (UiEvents.OnNavigate) -> Unit,
    viewModel: PostListViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()

    val lottieCompositionSpec by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.walking_ball))

    val posts = viewModel.posts

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvents.ShowSnackBar->{
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed){
                        viewModel.onEvent(PostListEvents.OnUndoDelete)
                    }
                }
                is UiEvents.OnNavigate->onNavigate(event)
                else->Unit
            }
        }
    }

    Scaffold (
        scaffoldState = scaffoldState,
        floatingActionButton = {
            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                FloatingActionButton(onClick = { viewModel.onEvent(PostListEvents.OnGetPosts) }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "refresh")
                }
                Spacer(modifier = Modifier.weight(1f))
                FloatingActionButton(onClick = { viewModel.onEvent(PostListEvents.OnCreatePostClicked) }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "add")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        topBar = {
            TopAppBar (
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                backgroundColor = Purple40,
            ){
                LottieAnimation(
                    composition = lottieCompositionSpec,
                    iterations = Int.MAX_VALUE,
                    alignment = Alignment.Center
                )
            }
        }
    ) {
        LazyColumn {
            items(posts){ post ->
                if (post != null) {
                    PostItem(
                        post = post,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}