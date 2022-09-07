package com.example.atlas.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atlas.events.PostListEvents
import com.example.atlas.ui.theme.Purple40
import com.example.atlas.util.UiEvents
import com.example.atlas.view_models.PostListViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PostListScreen(
    onNavigate: (UiEvents.OnNavigate) -> Unit,
    viewModel: PostListViewModel = hiltViewModel()
){
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event->
            when(event){
                is UiEvents.ShowSnackBar->{
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.message
                    )
                    if (result==SnackbarResult.ActionPerformed){
                        viewModel.onEvent(PostListEvents.OnUndoDelete)
                    }
                }
                is UiEvents.OnNavigate->onNavigate(event)
                else -> Unit
            }
        }
    }

    val posts = viewModel.posts

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(PostListEvents.OnCreatePostClicked) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Purple40, shape = RectangleShape)
                    .size(150.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome",
                    fontSize = 30.sp,
                    color = Color.White
                )
            }
            LazyColumn(
                modifier = Modifier.padding(2.dp)
            ){
                items(posts){ post->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(2.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .clickable {
                                    post
                                        ?.let { it1 -> PostListEvents.OnPostClicked(it1) }
                                        ?.let { it2 -> viewModel.onEvent(it2) }
                                }
                                .fillMaxWidth()
                                .padding(10.dp)
                                .size(100.dp)
                            ,
                            elevation = 5.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "person",
                                    modifier = Modifier.clip(CircleShape)
                                        .background(Color.LightGray)
                                        .size(50.dp)
                                        .padding(5.dp)
                                )
                                Text(
                                    text = post?.title ?: "",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "delete",
                                    modifier = Modifier.clickable {
                                        post?.let { it1 -> PostListEvents.OnDelete(it1) }
                                            ?.let { it2 -> viewModel.onEvent(it2) }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(onClick = { viewModel.onEvent(PostListEvents.OnGetPosts) }) {
            Text(text = "getPost")
        }
    }
}