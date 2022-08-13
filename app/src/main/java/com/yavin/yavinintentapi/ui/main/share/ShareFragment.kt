package com.yavin.yavinintentapi.ui.main.share

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.yavin.yavinintentapi.databinding.FragmentShareBinding
import com.yavin.yavinintentapi.ui.main.api.v4.share.ShareMedium
import com.yavin.yavinintentapi.ui.main.api.v4.share.ShareRequestV4
import com.yavin.yavinintentapi.ui.main.api.v4.share.ShareResponseV4
import com.yavin.yavinintentapi.ui.main.model.ApiVersion
import com.yavin.yavinintentapi.ui.main.model.Customer
import com.yavin.yavinintentapi.ui.main.model.ReceiptTicket

class ShareFragment : Fragment() {

    private var _binding: FragmentShareBinding? = null
    private val binding get() = _binding!!

    private val gson = Gson()

    private lateinit var apiVersion: ApiVersion
    private lateinit var medium: ShareMedium

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentShareBinding.inflate(inflater, container, false)
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

        medium = ShareMedium.EMAIL
        binding.mediumSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                medium = ShareMedium.fromCode(parent?.getItemAtPosition(position).toString())

                when (medium) {
                    ShareMedium.EMAIL -> {
                        binding.emailEditText.visibility = View.VISIBLE
                        binding.phoneEditText.visibility = View.GONE
                    }

                    ShareMedium.SMS -> {
                        binding.emailEditText.visibility = View.GONE
                        binding.phoneEditText.visibility = View.VISIBLE
                    }

                    ShareMedium.PRINT -> {
                        binding.emailEditText.visibility = View.GONE
                        binding.phoneEditText.visibility = View.GONE
                    }
                    else -> {}
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.shareButton.setOnClickListener {
            when (apiVersion) {
                ApiVersion.V4 -> shareApiV4()
                else -> Toast.makeText(
                    requireContext(),
                    "Implementation ${apiVersion.version} doesn't exist",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return root
    }

    private fun shareApiV4() {
        val dataToPrint = binding.dataEditText.text.toString()

        if (dataToPrint.isEmpty()) {
            Toast.makeText(requireContext(), "Missing data to print", Toast.LENGTH_SHORT).show()
            return
        }

        val customer = when (medium) {
            ShareMedium.EMAIL -> {
                val email = binding.emailEditText.text.toString()
                if (isValidEmail(email)) {
                    Customer(email = email)
                } else {
                    null
                }
            }

            ShareMedium.SMS -> {
                val phone = binding.phoneEditText.text.toString()

                if (isValidPhone(phone)) {
                    if(phone.startsWith("+")) {
                        Customer(phone = phone)
                    } else {
                        Toast.makeText(requireContext(), "Phone number must starts with + symbol (international format)", Toast.LENGTH_SHORT).show()
                        return
                    }
                } else {
                    null
                }
            }

            ShareMedium.PRINT -> {
                null
            }

            else -> null
        }

        val request = ShareRequestV4(
            receiptTicket = ReceiptTicket(
                dataToPrint,
                binding.formatSpinner.selectedItem.toString()
            ),
            medium = medium.code,
            transactionId = binding.transactionIdEditText.text.toString(),
            customer = customer
        )

        val jsonData = gson.toJson(request)
        val queryParams = Uri.encode(jsonData)

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("yavin://com.yavin.macewindu/v4/share-receipt?data=$queryParams")
        }

        startActivityForResult(intent, REQUEST_CODE_SHARE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data == null) return

        if (requestCode == REQUEST_CODE_SHARE) {
            when (apiVersion) {
                ApiVersion.V4 -> {
                    getShareResponseV4(data)
                }
                else -> {}
            }
        }
    }

    private fun getShareResponseV4(data: Intent) {
        val json = data.extras?.getString("response")
        val response = gson.fromJson(json, ShareResponseV4::class.java)

        Log.d("ShareFragment", response.toString())
        binding.resultTextView.text = response.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun isValidEmail(email: String?): Boolean {
        return !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPhone(phone: String?): Boolean {
        return !phone.isNullOrEmpty() && Patterns.PHONE.matcher(phone).matches()
    }

    companion object {
        private const val REQUEST_CODE_SHARE = 125

        @JvmStatic
        fun newInstance(): ShareFragment {
            return ShareFragment()
        }
    }
}