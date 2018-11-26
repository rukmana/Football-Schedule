package com.jelekong.footballmatchschedule.util

import android.support.test.espresso.IdlingResource

object EspressoIdlingResource {

    private val RESOURCE = "GLOBAL"

    private val mCountingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

    val idlingResource: IdlingResource
        get() = mCountingIdlingResource

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        mCountingIdlingResource.decrement()
    }
}