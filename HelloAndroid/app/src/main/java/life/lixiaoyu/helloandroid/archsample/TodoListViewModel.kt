package life.lixiaoyu.helloandroid.archsample

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel

data class TodoListState(
    val todoList: List<String> = emptyList(),
    val loading: Boolean = false,
): MavericksState

class TodoListViewModel(initialState: TodoListState): MavericksViewModel<TodoListState>(initialState) {

    fun addTodo(todo: String) {
        setState { copy(todoList = todoList + todo) }
    }

    fun removeTodo(todo: String) {
        setState { copy(todoList = todoList - todo) }
    }

    fun toggleLoading() {
        setState { copy(loading = !loading) }
    }
}