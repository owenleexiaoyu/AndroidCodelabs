package life.lixiaoyu.helloandroid.archsample

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.mvrx.InternalMavericksApi
import com.airbnb.mvrx._internal
import com.airbnb.mvrx._internal1
import com.airbnb.mvrx.viewModel
import life.lixiaoyu.helloandroid.R

class TodoListActivity: AppCompatActivity() {

    private var todoListText: TextView? = null
    private var addTodoButton: Button? = null
    private var input: EditText? = null
    private val todoListViewModel by viewModel(TodoListViewModel::class)

    @OptIn(InternalMavericksApi::class)
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        todoListText = findViewById(R.id.todo_list)
        addTodoButton = findViewById(R.id.add_todo)
        input = findViewById(R.id.input)
        addTodoButton?.setOnClickListener {
            todoListViewModel.addTodo(input!!.text.toString())
        }
        todoListViewModel._internal1(this, TodoListState::todoList) { todoList ->
            todoListText?.text = todoList.joinToString("\n")
        }
    }
}