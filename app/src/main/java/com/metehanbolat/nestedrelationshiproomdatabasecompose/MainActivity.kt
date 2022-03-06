package com.metehanbolat.nestedrelationshiproomdatabasecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.metehanbolat.nestedrelationshiproomdatabasecompose.ui.theme.NestedRelationshipRoomDatabaseComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NestedRelationshipRoomDatabaseComposeTheme {
                
            }
        }
    }
}