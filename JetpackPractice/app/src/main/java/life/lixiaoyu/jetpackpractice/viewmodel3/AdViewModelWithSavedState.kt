package life.lixiaoyu.jetpackpractice.viewmodel3

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class AdViewModelWithSavedState(val savedState: SavedStateHandle) : ViewModel() {

    var millisInFuture: Long
        get() {
            val saved = savedState.get<Long>(MILLIS_IN_FUTURE_KEY)
            if (saved != null) {
                return saved
            } else {
                return 5000L
            }
        }
        set(value) {
            savedState[MILLIS_IN_FUTURE_KEY] = value
        }

    companion object {
        private const val MILLIS_IN_FUTURE_KEY = "millis_in_future"
    }
}