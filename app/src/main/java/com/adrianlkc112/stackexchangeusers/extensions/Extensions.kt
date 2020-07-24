package com.adrianlkc112.stackexchangeusers.extensions

import android.view.View
import android.view.animation.AlphaAnimation
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

inline fun<T> Observable<T>.afterObserveOn(func: Observable<T>.() -> Disposable) : Disposable {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).func()
}

fun <T: View> T.setFadeInAnimation() {
    val anim = AlphaAnimation(0.0f, 1.0f)
    anim.duration = 1000
    this.startAnimation(anim)
}