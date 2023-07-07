package net.ucoz.testcompose.presentation.jobList

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import net.ucoz.testcompose.presentation.jobList.composables.JobList
import net.ucoz.testcompose.ui.theme.Background

@Composable
fun JobListScreen(
    state: JobListContract.State,
    effectFlow: Flow<JobListContract.Effect>?,
    onEventSent: (event: JobListContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: JobListContract.Effect) -> Unit
) {

    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->

        }?.collect()
    }
    when {
        state.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize().background(Background),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
        state.isError -> {
            state.errorMessage?.let {
                Toast.makeText(LocalContext.current,it.toString(),Toast.LENGTH_SHORT).show()
                Log.d("MyTag", it.toString())
//                ErrorDialog(value = it, setShowDialog = {
//                    Log.d("MyTag", it.toString())
//                    onEventSent(
//                        SecondScreenContract.Event.CloseDialog
//                    )
//                })
            }

        }
        else -> {
            state.jobList?.let {
                JobList(it, onEventSent)
            }

        }

    }

}