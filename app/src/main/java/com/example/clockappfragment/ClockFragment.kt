import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.clockappfragment.DataModel
import com.example.clockappfragment.R
import com.example.clockappfragment.databinding.ActivityMainBinding
import com.example.clockappfragment.databinding.FragmentClockBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class ClockFragment : Fragment() {

    private val dataModel: DataModel by activityViewModels()
    private lateinit var binding: FragmentClockBinding
    private val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClockBinding.inflate(layoutInflater)
        val view = binding.root
        updateDateTime(dataModel.timeZone.value ?: TimeZone.getDefault())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataModel.timeZone.value?.let { selectedTimeZone ->
            updateDateTime(selectedTimeZone)
        }
        dataModel.timeZone.observe(activity as LifecycleOwner, { selectedTimeZone ->
            updateDateTime(selectedTimeZone)
        })
    }

    private fun updateDateTime(selectedTimeZone: TimeZone) {
        val dateFormat = SimpleDateFormat("HH:mm:ss \n yyyy-MM-dd", Locale.getDefault())
        dateFormat.timeZone = selectedTimeZone
        val calendar = Calendar.getInstance(selectedTimeZone)
        val formattedDate = dateFormat.format(calendar.time)

        binding.textTimeZone.text = "Selected Time Zone: ${selectedTimeZone.displayName}"
        binding.textDateTime.text = "$formattedDate"

        handler.postDelayed({ updateDateTime(selectedTimeZone) }, 1000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null) // Remove any remaining callbacks
    }

    companion object {
        @JvmStatic
        fun newInstance() = ClockFragment()
    }
}
