package net.ucoz.testcompose.presentation.thirdScreen

import net.ucoz.testcompose.presentation.base.BaseViewModel


class ThirdScreenViewModel :
    BaseViewModel<
            ThirdScreenContract.Event,
            ThirdScreenContract.State,
            ThirdScreenContract.Effect>() {

    override fun setInitialState() = ThirdScreenContract.State()

    init {

    }

    override fun handleEvents(event: ThirdScreenContract.Event) {
        when (event) {
            is ThirdScreenContract.Event.addClicked -> {
                setState {
                    copy(isUpdateScreen = false)
                }
                viewState.value.content.add("Item number ${viewState.value.content.size + 1}")
                setState {
                    copy(isUpdateScreen = true)
                }

            }

            is ThirdScreenContract.Event.completeClicked -> {
                setState {
                    copy(
                        isShowCompleteButton = false
                    )
                }
            }

            is ThirdScreenContract.Event.deleteClicked -> {
                setState {
                    copy(isUpdateScreen = false)
                }
                viewState.value.content.removeLast()
                setState {
                    copy(isUpdateScreen = true)
                }
            }

            is ThirdScreenContract.Event.nextClicked -> {
                setState {
                    copy(
                        isShowCompleteButton = true
                    )
                }

            }
        }
    }


}