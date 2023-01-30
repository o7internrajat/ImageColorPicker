package com.o7solutions.imgrgbpicker.ui.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.viewbinding.BuildConfig
import com.o7solutions.imgrgbpicker.model.ColorCode
import com.o7solutions.imgrgbpicker.database.UserDataBase
import com.o7solutions.imgrgbpicker.databinding.FragmentImagePickerBinding
import java.io.File
import kotlin.math.max
import kotlin.math.min

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImagePickerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImagePickerFragment : Fragment() {

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    var scaleFactor=1.0f
    lateinit var binding: FragmentImagePickerBinding
    lateinit var bitmap: Bitmap
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var list=ArrayList<ColorCode>()

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
        binding = FragmentImagePickerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        scaleGestureDetector=
            context?.let { ScaleGestureDetector(it.applicationContext,ScaleListener()) }!!
        binding.btnImagePick.setOnClickListener {
            imagepicker()
        }
        binding.ivImage.isDrawingCacheEnabled = true
        binding.ivImage.buildDrawingCache(true)

        binding.ivImage.setOnTouchListener { _, event ->
            if(event.action== MotionEvent.ACTION_DOWN){
                bitmap=binding.ivImage.drawingCache
                val pixel = bitmap.getPixel(event.x.toInt(),event.y.toInt())
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)

                binding.tvColorCode.text="${Integer.toHexString(pixel)}"
                binding.tvColorShow.setBackgroundColor(Color.rgb(r,g,b))
            }
            onTouchEvent(event)
            true

        }


        val clipboardManager: ClipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        binding.tvColorCode.setOnClickListener {
            val clipData = ClipData.newPlainText("label",binding.tvColorCode.text.toString())
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context,"Hax code copy.", Toast.LENGTH_SHORT).show()
        }

        binding.btnSaveCode.setOnClickListener {
            if (binding.tvColorCode.text.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please Click on Image",
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
    private fun onTouchEvent(event: MotionEvent) : Boolean{
        scaleGestureDetector.onTouchEvent(event)
        return true
    }
    private val gallery=registerForActivityResult(ActivityResultContracts.GetContent()){
            uri: Uri? ->
        uri?.let {
            binding.ivImage.setImageURI(uri)
        }
        var latestUri: Uri?=null
        val view by lazy {
            binding.ivImage
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ImageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImagePickerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun imagepicker() = gallery.launch("image/*")

    private fun getFileUri():Uri {
        val file = File.createTempFile("image1",".png").apply {
            createNewFile()
            deleteOnExit()

        }
        return FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.BUILD_TYPE}.Provide",file
        )
    }
private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
    override fun onScale(scaleListener: ScaleGestureDetector): Boolean {
        scaleFactor *= scaleGestureDetector.scaleFactor
        scaleFactor = max(0.1f, min(scaleFactor,10.0f))
        binding.ivImage.scaleX = scaleFactor
        binding.ivImage.scaleY = scaleFactor
        return true
    }
    }

}


