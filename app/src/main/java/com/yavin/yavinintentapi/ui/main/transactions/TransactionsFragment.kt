package com.yavin.yavinintentapi.ui.main.transactions

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
import com.yavin.yavinintentapi.databinding.FragmentTransactionsBinding
import com.yavin.yavinintentapi.ui.main.api.v4.transactions.TransactionsRequestV4
import com.yavin.yavinintentapi.ui.main.api.v4.transactions.TransactionsResponseV4
import com.yavin.yavinintentapi.ui.main.model.ApiVersion

class TransactionsFragment : Fragment() {

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!

    private val gson = Gson()

    lateinit var apiVersion: ApiVersion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        val root = binding.root

        apiVersion = ApiVersion.V1
        binding.apiSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                apiVersion = ApiVersion.fromCode(parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.fetchTransactionsButton.setOnClickListener {
            when (apiVersion) {
                ApiVersion.V4 -> fetchTransactionsApiV4()

                else -> Toast.makeText(
                    requireContext(),
                    "Implementation ${apiVersion.version} doesn't exist",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return root
    }

    private fun fetchTransactionsApiV4() {
        val request = TransactionsRequestV4()
        val jsonData = gson.toJson(request)
        val queryParams = Uri.encode(jsonData)

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("yavin://com.yavin.macewindu/v4/transactions?data=$queryParams")
        }

        startActivityForResult(intent, REQUEST_CODE_TRANSACTIONS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data == null) return

        if (requestCode == REQUEST_CODE_TRANSACTIONS) {
            when (apiVersion) {
                ApiVersion.V4 -> {
                    getTransactionsResponseV4(data)
                }
                else -> {}
            }
        }
    }

    private fun getTransactionsResponseV4(data: Intent) {
        val json = data.extras?.getString("response")
        val response = gson.fromJson(json, TransactionsResponseV4::class.java)

        Log.d("TransactionsFragment", response.toString())
        binding.resultTextView.text = response.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE_TRANSACTIONS = 125

        @JvmStatic
        fun newInstance(): TransactionsFragment {
            return TransactionsFragment()
        }
    }
}