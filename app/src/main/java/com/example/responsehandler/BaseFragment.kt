package com.example.responsehandler

import android.os.Bundle
import com.example.jdrodi.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * BaseActivity.kt - A simple class contains some modifications to the native Activity.
 * @author  Jignesh N Patel
 * @date 22-Jan-2021
 */

abstract class BaseFragment : BaseFragment(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}