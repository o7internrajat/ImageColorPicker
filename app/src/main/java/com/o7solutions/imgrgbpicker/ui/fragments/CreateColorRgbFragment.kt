package com.o7solutions.imgrgbpicker.ui.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.o7solutions.imgrgbpicker.model.ColorCode
import com.o7solutions.imgrgbpicker.database.UserDataBase
import com.o7solutions.imgrgbpicker.databinding.FragmentCreateColorRgbBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateColorRgbFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateColorRgbFragment : Fragment() {

    lateinit var binding: FragmentCreateColorRgbBinding
    var list=ArrayList<ColorCode>()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var red=225
    var green =225
    var blue=225

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentCreateColorRgbBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sbRed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                red = progress
                binding.tvColorShow.setBackgroundColor(Color.rgb(red, green, blue))

                val colorStr = getColorString()
                binding.tvColorCode.setText(colorStr!!.replace("#", "").uppercase())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        binding.sbGreen.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                green = progress
                binding.tvColorShow.setBackgroundColor(Color.rgb(red, green, blue))
                val colorStr = getColorString()
                binding.tvColorCode.setText(colorStr!!.replace("#", "").uppercase())

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        binding.sbBlue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                blue = progress
                binding.tvColorShow.setBackgroundColor(Color.rgb(red, green, blue))
                val colorStr = getColorString()
                binding.tvColorCode.setText(colorStr!!.replace("#", "").uppercase())

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })


        val clipboardManager: ClipboardManager =
            context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        binding.tvColorCode.setOnClickListener {
            val clipData = ClipData.newPlainText("label", binding.tvColorCode.text.toString())
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context, "Hax code copy.", Toast.LENGTH_SHORT).show()

        }

        binding.btnSaveCode.setOnClickListener {
            if (binding.tvColorCode.text.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please Create Color Code",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                class getTask : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg params: Void?): Void? {

                        list.addAll(UserDataBase.getData(requireContext()).userDao().getAllUser())

                        UserDataBase.getData(requireContext()).userDao()
                            .addUser(ColorCode(code1 = binding.tvColorCode.text.toString()))
                        return null
                    }
                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        Toast.makeText(requireContext(), "Save Hex Code", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                getTask().execute()
            }
        }
    }
    private fun getColorString(): String{
        var r = Integer.toHexString(((255*binding.sbRed.progress)/binding.sbRed.max))
        if(r.length==1)
            r="0"+r
        var g = Integer.toHexString(((255*binding.sbGreen.progress)/binding.sbGreen.max))
        if(g.length==1)
            g="0"+g
        var b = Integer.toHexString(((255*binding.sbBlue.progress)/binding.sbBlue.max))
        if(b.length==1)
            b="0"+b
        return "#"+r+g+b

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateColorRgbFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}