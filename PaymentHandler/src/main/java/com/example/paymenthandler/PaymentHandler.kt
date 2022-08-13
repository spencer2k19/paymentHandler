package com.example.paymenthandler

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.paymenthandler.api.v1.payment.PaymentRequestV1
import com.example.paymenthandler.api.v1.payment.PaymentResponseV1
import com.example.paymenthandler.api.v4.payment.PaymentRequestV4
import com.example.paymenthandler.api.v4.payment.PaymentResponseV4
import com.example.paymenthandler.model.ApiVersion
import com.example.paymenthandler.model.Customer
import com.example.paymenthandler.model.ReceiptTicket
import com.google.gson.Gson

class PaymentHandler(val activity:Activity) {
    var apiVersion: ApiVersion = ApiVersion.V1
    private val gson = Gson()
    private  val REQUEST_CODE_PAYMENT = 123

    private fun paymentApiV4(amount:Int,giftAmount:Int,transactionType:String,
    firstName:String,lastName:String,email:String,phone:String,data:String,format:String,reference:String) {
        val receiptTicket = ReceiptTicket(data, format )
        val customer = Customer(firstName, lastName, email, phone)
        val paymentRequest = PaymentRequestV4(
            amount = amount,
            giftAmount = giftAmount,
            transactionType = transactionType,
            receiptTicket = receiptTicket,
            reference = reference,
            customer = customer
        )
        val jsonData = gson.toJson(paymentRequest)
        val queryParams = Uri.encode(jsonData)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("yavin://com.yavin.macewindu/v4/payment?data=$queryParams")
        activity.startActivityForResult(intent,REQUEST_CODE_PAYMENT)

    }




    private fun paymentApiV1(firstName: String,lastName: String,email:String,
                             phone:String,amount:String,
                             transactionType: String,
                             reference: String,
    ) {

        val paymentRequest = PaymentRequestV1(
            amount = amount,
            transactionType = transactionType,
            reference = reference,
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

        activity.startActivityForResult(intent, REQUEST_CODE_PAYMENT)
    }

    private fun getPaymentResponseV1(data: Intent):String {
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
        val result= gson.toJson(response,PaymentResponseV1::class.java)

        Log.d("PaymentFragment", response.toString())
        return result
    }


    private fun getPaymentResponseV4(data: Intent):String? {
        val json = data.extras?.getString("response")
//        val response = gson.fromJson(json, PaymentResponseV4::class.java)
//        Log.d("PaymentFragment", response.toString())
        return json
    }


}