package com.example.responsehandler.network.dao

import androidx.annotation.Keep

/**
 * ResponseForceUpdateModel.kt - Data class of Response Force Update.
 * @author:  Jignesh N Patel
 * @date: 22-Jan-2021 8:46 PM
 */

@Keep
data class ResponseForceUpdateModel(
    val is_need_to_update: Boolean = false,
    val message: String = "",
    val status: Boolean = false
)