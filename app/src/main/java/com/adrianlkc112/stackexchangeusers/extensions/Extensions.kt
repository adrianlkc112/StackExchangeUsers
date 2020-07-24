package com.adrianlkc112.stackexchangeusers.extensions

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

inline fun<T> Observable<T>.afterObserveOn(func: Observable<T>.() -> Disposable) : Disposable {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).func()
}