package com.github.boxuanjia.rxharmonylife;

import io.reactivex.disposables.Disposable;

public interface Scope {

    /**
     * 订阅事件时,回调本方法，即在onSubscribe(Disposable d)方法执行时回调本方法
     *
     * @param d Disposable
     */
    void onScopeStart(Disposable d);

    /**
     * onError/onComplete 时调回调此方法，即事件正常结束时回调
     */
    void onScopeEnd();
}
