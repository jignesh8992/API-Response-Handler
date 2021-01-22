package com.example.responsehandler

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.example.jdrodi.utilities.isOnline
import com.example.jdrodi.utilities.showSnackbar
import com.example.responsehandler.network.APIInterface
import com.example.responsehandler.network.APIService
import com.example.responsehandler.network.utilites.METHOD_APK_VERSION
import com.example.responsehandler.network.dao.ResponseForceUpdateModel
import com.example.responsehandler.network.utilites.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * BaseActivity.kt - A simple class contains some modifications to the native Activity.
 * @author  Jignesh N Patel
 * @date 22-Jan-2021
 */
class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getContext(): Activity {
        return this@MainActivity
    }

    override fun initActions() {

    }

    override fun onClick(view: View) {
        super.onClick(view)
        when (view) {
            btn_retry -> {
                isNeedToUpdateApp()
            }
        }
    }

    override fun initData() {
        isNeedToUpdateApp()
    }

    private fun isNeedToUpdateApp() {

        tv_status.text = getString(R.string.please_wait)
        btn_retry.visibility = View.GONE

        // Make sure the Internet is working before making any API call
        if (!isOnline()) {
            showNoInternetAlert(object : OnPositive {
                override fun onYes() {
                    isNeedToUpdateApp()
                }
            })
            tv_status.text = getString(R.string.network_offline)
            btn_retry.visibility = View.VISIBLE
            return
        }

        // Internet is working so now you can make API call
        launch {
            jpShow()
            try {
                val pkgName = "com.emoji.maker.funny.face.animated.avatar"
                val version = BuildConfig.VERSION_NAME.toDouble()

                // Retrofit API client
                val apiClient: APIInterface = APIService().getClient()
                request(METHOD_APK_VERSION)

                //  Make request and wait until get response
                val reqConfigurations = apiClient.isNeedToForceUpdateAsync(pkgName, version)
                val resConfigurations = reqConfigurations.await()

                // Validating API response
                if (isValidResponse(resConfigurations)) {
                    btn_retry.visibility = View.GONE
                    // Success: Perform your further task
                    response("$METHOD_APK_VERSION ==> ${resConfigurations.body().toString()}")
                    manageResponse(resConfigurations)
                } else {
                    // Generic exception occurred

                    errorAlert(object : OnPositive {
                        override fun onYes() {
                            isNeedToUpdateApp()
                        }
                    })
                    tv_status.text = getString(R.string.went_wrong)
                }
                jpDismiss()
            } catch (exception: Exception) {
                // Exception occurred
                jpDismiss()
                btn_retry.visibility = View.VISIBLE
                val errorMessage = apiExceptionHandler(exception, object : OnPositive {
                    override fun onYes() {
                        isNeedToUpdateApp()
                    }
                })
                tv_status.text = errorMessage
            }
        }

    }

    private fun manageResponse(resConfigurations: Response<ResponseForceUpdateModel>) {
        val resForceUpdate = resConfigurations.body()!!
        showSnackbar(resForceUpdate.message)
        tv_status.text = resForceUpdate.message
    }

}