package com.santimattius.basic.skeleton

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.santimattius.basic.skeleton.ui.component.BasicSkeletonContainer
import com.santimattius.basic.skeleton.ui.navigation.AppNavGraph

class MainActivity : ComponentActivity() {

    private var deepLinkIntent by mutableStateOf<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deepLinkIntent = intent.takeIf { savedInstanceState == null }
        setContent {
            BasicSkeletonContainer {
                AppNavGraph(deepLinkIntent = deepLinkIntent)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        deepLinkIntent = intent
    }
}
