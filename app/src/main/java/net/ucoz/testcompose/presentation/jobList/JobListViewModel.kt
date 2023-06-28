package net.ucoz.testcompose.presentation.jobList

import net.ucoz.testcompose.presentation.base.BaseViewModel

class JobListViewModel :
    BaseViewModel<
            JobListContract.Event,
            JobListContract.State,
            JobListContract.Effect>() {

    override fun setInitialState() = JobListContract.State()

    override fun handleEvents(event: JobListContract.Event) {
        when (event) {
        }
    }


}