package com.example.atlas.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.atlas.screens.AddEditPostScreen
import com.example.atlas.screens.PostListScreen
import com.example.atlas.util.Routes

@Composable
fun MainNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.POST_LIST
    ){
        composable(route = Routes.POST_LIST){
            PostListScreen(
                onNavigate = {
                    navHostController.navigate(it.route)
                }
            )
        }
        composable(
            route = Routes.ADD_EDIT_POST + "?postId={postId}",
            arguments = listOf(
                navArgument(name = "postId"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            AddEditPostScreen(
                onPopBackStack = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}