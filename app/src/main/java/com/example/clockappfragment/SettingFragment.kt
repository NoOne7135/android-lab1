import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.clockappfragment.DataModel
import com.example.clockappfragment.R
import com.example.clockappfragment.databinding.FragmentClockBinding
import com.example.clockappfragment.databinding.FragmentSettingBinding
import java.util.*

class SettingFragment : Fragment() {

    private val dataModel: DataModel by activityViewModels()
    private lateinit var binding: FragmentSettingBinding
    private var isFirstSelection = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        val view = binding.root


        val timeZones = TimeZone.getAvailableIDs()
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            timeZones
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerTimeZone.adapter = adapter

        binding.spinnerTimeZone.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (isFirstSelection) {
                    isFirstSelection = false
                } else {
                    val selectedTimeZoneID = timeZones[position]
                    val selectedTimeZone = TimeZone.getTimeZone(selectedTimeZoneID)
                    dataModel.timeZone.value = selectedTimeZone
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }
}