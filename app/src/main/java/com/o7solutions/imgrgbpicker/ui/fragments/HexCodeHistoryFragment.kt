package com.o7solutions.imgrgbpicker.ui.fragments

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.o7solutions.imgrgbpicker.model.ColorCode
import com.o7solutions.imgrgbpicker.ui.adapter.ColorCodeAdapter
import com.o7solutions.imgrgbpicker.database.UserDataBase
import com.o7solutions.imgrgbpicker.databinding.FragmentHexCodeHistoryBinding
import com.o7solutions.imgrgbpicker.interfaces.ClickInterface


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HexCodeHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HexCodeHistoryFragment : Fragment(), ClickInterface {


    lateinit var binding: FragmentHexCodeHistoryBinding
    lateinit var userAdapter: ColorCodeAdapter
    var userList = ArrayList<ColorCode>()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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

        binding = FragmentHexCodeHistoryBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userAdapter = ColorCodeAdapter(userList,this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = userAdapter
        getData()

    }

    private fun getData() {
        userList.clear()
        class getTask : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                userList.addAll(UserDataBase.getData(requireContext()).userDao().getAllUser())
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                userAdapter.setData(userList)
            }
        }
        getTask().execute()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HexCodeHistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun deleteClick(colorCode: ColorCode, position: Int) {
        class getTask : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {

                UserDataBase.getData(requireContext()).userDao().deleteUser(colorCode)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(requireContext(), "Data Deleted", Toast.LENGTH_SHORT).show()
                getData()
            }
        }
        getTask().execute()
    }
}
