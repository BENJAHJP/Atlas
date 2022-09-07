package com.example.atlas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.atlas.navigations.MainNavGraph
import com.example.atlas.ui.theme.AtlasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AtlasTheme {
                val navHostController: NavHostController = rememberNavController()
                MainNavGraph(navHostController = navHostController)
            }
        }
    }
}