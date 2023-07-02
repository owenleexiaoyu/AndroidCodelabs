package life.lixiaoyu.jetpackpractice.livedata1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import life.lixiaoyu.jetpackpractice.R

class SeekBarFragmentOne: Fragment() {

    private lateinit var viewModel: SeekBarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_livedata1_seekbar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SeekBarViewModel::class.java]
        view.findViewById<TextView>(R.id.name).text = "Fragment_One"
        val seekbar = view.findViewById<SeekBar>(R.id.seekbar)
        viewModel.progress.observe(viewLifecycleOwner) {
            seekbar.progress = it
        }
        seekbar.setOnSeekBarChangeListener(object: OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.progress.value = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }
}