package life.lixiaoyu.jetpackpractice.livedata2.student

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import life.lixiaoyu.jetpackpractice.R
import java.lang.Exception

class StudentActivity: AppCompatActivity() {

    private lateinit var studentViewModel: StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livedata2_student)
        val scoreTv = findViewById<TextView>(R.id.score)
        studentViewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        studentViewModel.scoreLiveData.observe(this) {
            scoreTv.text = "$it"
        }

        val searchScoreInput = findViewById<EditText>(R.id.id_input)
        val searchScoreBtn = findViewById<Button>(R.id.search_score)
        val searchScoreResultTv = findViewById<TextView>(R.id.search_result)

        searchScoreBtn.setOnClickListener {
            val idStr = searchScoreInput.text.trim().toString()
            try {
                val id = idStr.toInt()
                studentViewModel.setSearchStudentId(id)
            } catch (e: Exception) {
                Toast.makeText(this, "Input a number", Toast.LENGTH_SHORT).show()
            }
        }
        studentViewModel.searchStudentScore.observe(this) {
            searchScoreResultTv.text = "Result score: $it"
        }
    }

}