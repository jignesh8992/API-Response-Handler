package com.example.responsehandler.network.utilites

import android.app.AlertDialog
import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.responsehandler.R
import com.example.responsehandler.utilites.fontPath
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.net.ssl.HttpsURLConnection

/**
 * ResponseHandler.kt - Response handler class for api calling
 * @author:  Jignesh N Patel
 * @date: 22-Jan-2021 8:36 PM
 */


const val TAG_DEBUG_API = "DEBUG_API"

fun request(api: String): String {
    val request = "$TAG_DEBUG_API REQUEST: $api"
    println(request)
    return request

}

fun response(api: String): String {
    val response = "$TAG_DEBUG_API RESPONSE: $api"
    println(response)
    return response
}

fun failure(api: String): String {
    val failure = "$TAG_DEBUG_API FAILURE: $api"
    println(failure)
    return failure
}

fun exception(api: String): String {
    val exception = "$TAG_DEBUG_API EXCEPTION: $api"
    println(exception)
    return exception
}

fun isValidResponse(response: Response<*>): Boolean {
    return response.code() == 200 && response.isSuccessful && response.body() != null
}

fun Context.showNoInternetAlert(positive: OnPositive?) {
    showAlert(getString(R.string.network_offline), getString(R.string.please_try_later), getString(R.string.retry), getString(R.string.cancel), positive)
}

fun Context.showVpnAlert() {
    showAlert(getString(R.string.alert), getString(R.string.network_vpn), null, null, null)
}

fun Context.errorAlert(positive: OnPositive?) {
    showAlert(getString(R.string.went_wrong), getString(R.string.please_try_later), getString(R.string.retry), getString(R.string.cancel), positive)
}

fun Context.apiExceptionHandler(error: Exception?, positive: OnPositive?): String {
    val title: String
    val msg: String
    val positiveText: String = getString(R.string.retry)
    val negativeText: String = getString(R.string.cancel)

    if (error != null) {
        when {
            error.toString().contains("java.net.UnknownHostException") -> {
                title = getString(R.string.network_offline)
                msg = getString(R.string.please_try_later)
            }
            error is SocketTimeoutException || error is IOException -> {
                title = getString(R.string.connection_timeout)
                msg = getString(R.string.please_try_later)
            }
            error is HttpException -> {
                when (error.code()) {
                    HttpsURLConnection.HTTP_INTERNAL_ERROR -> {
                        title = getString(R.string.internal_server_error)
                        msg = getString(R.string.please_try_later)
                    }
                    HttpsURLConnection.HTTP_BAD_REQUEST -> {
                        title = getString(R.string.bad_request)
                        msg = getString(R.string.please_try_later)
                    }
                    else -> {
                        title = getString(R.string.network_error)
                        msg = getString(R.string.please_try_later)
                    }
                }
            }
            else -> {
                //Generic error handling
                title = getString(R.string.error)
                msg = getString(R.string.went_wrong)
            }
        }
    } else {
        //Generic error handling
        title = getString(R.string.network_error)
        msg = getString(R.string.went_wrong)
    }

    println("$TAG_DEBUG_API: $title")
    showAlert(title, msg, positiveText, negativeText, object : OnPositive {
        override fun onYes() {
            positive?.onYes()
        }
    })

    return msg
}


fun Context.showAlert(title: String?, msg: String?, positiveText: String?, negativeText: String?, positive: OnPositive?) {
    val dialog = AlertDialog.Builder(this)
    var alert: AlertDialog? = null

    dialog.setCancelable(false)
    if (title != null) {
        // Initialize a new foreground color span instance
        val foregroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        val ssBuilder = SpannableStringBuilder(title)
        ssBuilder.setSpan(foregroundColorSpan, 0, title.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        dialog.setTitle(ssBuilder)
    }
    if (msg != null) {
        dialog.setMessage(msg)
    }
    if (positiveText != null) {
        dialog.setPositiveButton(positiveText) { _, _ ->
            alert?.dismiss()
            positive?.onYes()

        }
    }
    if (negativeText != null) {
        dialog.setNegativeButton(negativeText) { _, _ ->
            alert?.dismiss()
        }
    }

    alert = dialog.create()
    alert.show()

    val textView = alert.findViewById<TextView>(android.R.id.message)
    try {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        val face = Typeface.createFromAsset(assets, fontPath)
        textView.typeface = face
    } catch (e: Exception) {
        Log.e(TAG_DEBUG_API, "showAlert: $e")
    }
}

interface OnPositive {
    fun onYes()
    fun onNo() {}
}