package net.ucoz.testcompose.presentation.jobList

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import net.ucoz.testcompose.domain.common.NetworkResult
import net.ucoz.testcompose.domain.interactor.JobInteractor
import net.ucoz.testcompose.presentation.base.BaseViewModel
import net.ucoz.testcompose.presentation.models.JobUI
import net.ucoz.testcompose.presentation.util.ConnectionState
import net.ucoz.testcompose.presentation.util.currentConnectivityState
import javax.inject.Inject

@HiltViewModel
class JobListViewModel  @Inject constructor(
    private val interactor: JobInteractor,
    @ApplicationContext context: Context,
) :
    BaseViewModel<
            JobListContract.Event,
            JobListContract.State,
            JobListContract.Effect>() {

    init {
        if (context.currentConnectivityState == ConnectionState.Available) {
            viewModelScope.launch {
                setState { copy(isLoading = true, isError = false) }
                when (val networkResult = interactor.getJobList()) {
                    is NetworkResult.SuccessWithData -> {
                        Log.d("MyTag", networkResult.data[0].pauseName)
                        setState {
                            copy(
                                isLoading = false,
                                isError = false,
                                jobList = networkResult.data.map { JobUI(it) })
                        }
                    }

                    is NetworkResult.ServerError -> {
                        setState {
                            copy(
                                isLoading = false, isError = true, errorMessage = networkResult.message
                            )
                        }

                    }

                    is NetworkResult.Error -> {
                        setState {
                            copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = networkResult.exception?.message
                            )
                        }
                    }
                }
            }
        } else {
            setState {
                copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "no internet connection"
                )
            }
        }

    }

    override fun setInitialState() = JobListContract.State()

    override fun handleEvents(event: JobListContract.Event) {
        when (event) {
        }
    }


}