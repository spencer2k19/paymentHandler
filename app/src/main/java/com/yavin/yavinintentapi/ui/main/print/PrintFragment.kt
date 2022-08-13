package com.yavin.yavinintentapi.ui.main.print

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.yavin.yavinintentapi.databinding.FragmentPrintBinding
import com.yavin.yavinintentapi.ui.main.api.v4.print.PrintRequestV4
import com.yavin.yavinintentapi.ui.main.api.v4.print.PrintResponseV4
import com.yavin.yavinintentapi.ui.main.model.ApiVersion

class PrintFragment : Fragment() {

    private var _binding: FragmentPrintBinding? = null
    private val binding get() = _binding!!

    private val gson = Gson()

    lateinit var apiVersion: ApiVersion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPrintBinding.inflate(inflater, container, false)
        val root = binding.root

        apiVersion = ApiVersion.V1
        binding.apiSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                apiVersion = ApiVersion.fromCode(parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.printButton.setOnClickListener {
            when(apiVersion) {
                ApiVersion.V4 -> printApiV4()
                else -> Toast.makeText(requireContext(), "Implementation ${apiVersion.version} doesn't exist", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun printApiV4() {
        val dataToPrint = binding.dataEditText.text.toString()

        if(dataToPrint.isEmpty()) {
            Toast.makeText(requireContext(), "Missing data to print", Toast.LENGTH_SHORT).show()
            return
        }

        val request = PrintRequestV4(
            data = dataToPrint,
            format = binding.formatSpinner.selectedItem.toString()
        )

        val jsonData = gson.toJson(request)
        val queryParams = Uri.encode(jsonData)

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("yavin://com.yavin.macewindu/v4/print?data=$queryParams")
        }

        startActivityForResult(intent, REQUEST_CODE_PRINT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data == null) return

        if (requestCode == REQUEST_CODE_PRINT) {
            when (apiVersion) {
                ApiVersion.V4 -> {
                    getPrintResponseV4(data)
                }
                else -> {}
            }
        }
    }

    private fun getPrintResponseV4(data: Intent) {
        val json = data.extras?.getString("response")
        val response = gson.fromJson(json, PrintResponseV4::class.java)

        Log.d("PrintFragment", response.toString())
        binding.resultTextView.text = response.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE_PRINT = 124

        @JvmStatic
        fun newInstance(): PrintFragment {
            return PrintFragment()
        }
    }
}