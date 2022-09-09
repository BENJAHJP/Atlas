package com.example.atlas.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atlas.data.Post
import com.example.atlas.events.PostListEvents

@Composable
fun PostItem(
    post: Post,
    onEvent: (PostListEvents)-> Unit,
) {
    Card(
        modifier = Modifier
            .clickable {
                onEvent(PostListEvents.OnPostClicked(post))
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
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .size(50.dp)
                    .padding(5.dp)
            )
            Text(
                text = post.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete",
                modifier = Modifier.clickable {
                    onEvent(PostListEvents.OnDelete(post))
                }
            )
        }
    }
}