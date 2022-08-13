package com.yavin.yavinintentapi.ui.main.payment

import android.content.ComponentName
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
import com.yavin.yavinintentapi.databinding.FragmentPaymentBinding
import com.yavin.yavinintentapi.ui.main.api.v1.payment.PaymentRequestV1
import com.yavin.yavinintentapi.ui.main.api.v1.payment.PaymentResponseV1
import com.yavin.yavinintentapi.ui.main.api.v4.payment.PaymentRequestV4
import com.yavin.yavinintentapi.ui.main.api.v4.payment.PaymentResponseV4
import com.yavin.yavinintentapi.ui.main.model.ApiVersion
import com.yavin.yavinintentapi.ui.main.model.Customer
import com.yavin.yavinintentapi.ui.main.model.ReceiptTicket

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private val gson = Gson()

    lateinit var apiVersion: ApiVersion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
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

        binding.startPaymentButton.setOnClickListener {
            when (apiVersion) {
                ApiVersion.V4 -> paymentApiV4()
                ApiVersion.V1 -> paymentApiV1()
                else -> Toast.makeText(
                    requireContext(),
                    "Implementation doesn't exist",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return root
    }

    private fun paymentApiV4() {
        val amount = if (binding.amountEditText.text.toString().isEmpty()) {
            0
        } else {
            binding.amountEditText.text.toString().toInt()
        }

        if (amount == 0) {
            Toast.makeText(requireContext(), "Amount must be > 0", Toast.LENGTH_SHORT).show()
            return
        }

        val giftAmount = if (binding.giftAmountEditText.text.toString().isEmpty()) {
            0
        } else {
            binding.giftAmountEditText.text.toString().toInt()
        }

        val paymentRequest = PaymentRequestV4(
            amount = amount,
            giftAmount = giftAmount,
            transactionType = binding.transactionTypeSpinner.selectedItem.toString(),
            receiptTicket = ReceiptTicket(
                data = binding.dataEditText.text.toString(),
                format = binding.formatSpinner.selectedItem.toString()
            ),
            reference = binding.referenceEditText.text.toString(),
            customer = Customer(
                binding.firstnameEditText.text.toString(),
                binding.lastnameEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.phoneEditText.text.toString()
            )
        )

        val jsonData = gson.toJson(paymentRequest)
        val queryParams = Uri.encode(jsonData)

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("yavin://com.yavin.macewindu/v4/payment?data=$queryParams")
        }

        startActivityForResult(intent, REQUEST_CODE_PAYMENT)
    }

    private fun paymentApiV1() {
        val firstName = binding.firstnameEditText.text.toString()
        val lastName = binding.lastnameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val phone = binding.phoneEditText.text.toString()

        val amount = binding.amountEditText.text.toString().ifEmpty {
            "0"
        }

        if (amount.toInt() == 0) {
            Toast.makeText(requireContext(), "Amount must be > 0", Toast.LENGTH_SHORT).show()
            return
        }

        val paymentRequest = PaymentRequestV1(
            amount = amount,
            transactionType = binding.transactionTypeSpinner.selectedItem.toString(),
            reference = binding.referenceEditText.text.toString(),
            client = "{\"email\":\"$email\",\"firstName\":\"$firstName\",\"lastName\":\"$lastName\", \"phone\":\"$phone\"}"
        )

        val intent = Intent()
        intent.component =
            ComponentName("com.yavin.macewindu", "com.yavin.macewindu.PaymentActivity")
        intent.putExtra("vendorToken", paymentRequest.vendorToken)
        intent.putExtra("amount", paymentRequest.amount)
        intent.putExtra("transactionType", paymentRequest.transactionType)
        intent.putExtra("reference", paymentRequest.reference)
        intent.putExtra("client", paymentRequest.client)

        startActivityForResult(intent, REQUEST_CODE_PAYMENT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data == null) return

        if (requestCode == REQUEST_CODE_PAYMENT) {
            when (apiVersion) {
                ApiVersion.V1 -> {
                    getPaymentResponseV1(data)
                }

                ApiVersion.V4 -> {
                    getPaymentResponseV4(data)
                }
                else -> {}
            }
        }
    }

    private fun getPaymentResponseV4(data: Intent) {
        val json = data.extras?.getString("response")
        val response = gson.fromJson(json, PaymentResponseV4::class.java)

        Log.d("PaymentFragment", response.toString())
        binding.resultTextView.text = response.toString()
    }


    private fun getPaymentResponseV1(data: Intent) {
        val response = PaymentResponseV1(
            amount = data.getIntExtra("amount", 0),
            cardToken = data.getStringExtra("cardToken"),
            clientTicket = data.getStringExtra("clientTicket"),
            giftAmount = data.getIntExtra("giftAmount", 0),
            reference = data.getStringExtra("reference"),
            signatureRequired = data.getBooleanExtra("signatureRequired", false),
            status = data.getStringExtra("status"),
            totalAmount = data.getIntExtra("totalAmount", 0),
            transactionId = data.getStringExtra("transactionId")
        )

        Log.d("PaymentFragment", response.toString())
        binding.resultTextView.text = response.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE_PAYMENT = 123

        @JvmStatic
        fun newInstance(): PaymentFragment {
            return PaymentFragment()
        }
    }
}