import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.clockappfragment.R
import com.example.clockappfragment.databinding.FragmentClockBinding
import com.example.clockappfragment.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {
    private lateinit var binding: FragmentTimerBinding

    private var handler: Handler? = null
    private val lapsList = ArrayList<String>()
    private var timerStart = false
    private var time = 0
    private var last_time = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(layoutInflater)
        val view = binding.root

        binding.startStopButton.setOnClickListener { startStopTapped() }
        binding.resetButton.setOnClickListener { resetTapped() }
        binding.lapButton.setOnClickListener { lapTapped() }

        return view
    }

    private fun lapTapped() {
        if (timerStart) {

            val seconds = time-last_time
            val minutes = seconds / 60
            val hours = minutes / 60

            lapsList.add(String.format(
                "%02d:%02d:%02d",
                hours, minutes % 60, seconds % 60
            ))
            last_time = time
            updateLapsListView()

        }
    }

    private fun resetTapped() {
        time = 0
        lapsList.clear()
        timerStart = false
        stopTimer()
        binding.startStopButton.text = "Start"
        binding.timerDisplay.text = "00:00:00"
    }

    private fun startStopTapped() {
        if (!timerStart) {
            timerStart = true
            binding.startStopButton.text = "Stop"
            startTimer()
        } else {
            handler?.removeCallbacksAndMessages(null)
            timerStart = false
            binding.startStopButton.text = "Start"
            stopTimer()
        }
    }

    private fun startTimer() {
        handler = Handler()
        handler?.post(object : Runnable {
            override fun run() {
                time++
                val seconds = time
                val minutes = seconds / 60
                val hours = minutes / 60
                binding.timerDisplay.text = String.format(
                    "%02d:%02d:%02d",
                    hours, minutes % 60, seconds % 60
                )
                if (timerStart) {
                    handler?.postDelayed(this, 1000)
                }
            }
        })
    }
    private fun stopTimer() {
        handler?.removeCallbacksAndMessages(null)
    }
    private fun updateLapsListView() {

        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, lapsList)

        binding.lapsListView.adapter = adapter
    }
    companion object {
        @JvmStatic
        fun newInstance() = TimerFragment()
    }
}

