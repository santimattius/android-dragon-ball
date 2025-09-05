package com.santimattius.basic.skeleton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.santimattius.basic.skeleton.ui.component.BasicSkeletonContainer
import com.santimattius.basic.skeleton.ui.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicSkeletonContainer {
                AppNavGraph()
            }
        }
    }
}
