package com.github.boxuanjia.rxharmonylife;

import io.reactivex.disposables.Disposable;
import io.reactivex.harmony.schedulers.HarmonySchedulers;
import ohos.eventhandler.EventRunner;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractLifecycle<T> extends AtomicReference<T> implements Disposable {

    private Scope scope;

    private final Object mObject = new Object();

    public AbstractLifecycle(Scope scope) {
        this.scope = scope;
    }

    private boolean isAddObserver;

    /**
     * 事件订阅时调用此方法
     */
    protected final void addObserver() throws Exception {
        //Lifecycle添加监听器需要在主线程执行
        if (isMainThread() || !(scope instanceof LifecycleScope)) {
            addObserverOnMain();
        } else {
            final Object object = mObject;
            HarmonySchedulers.mainThread().scheduleDirect(() -> {
                addObserverOnMain();
                synchronized (object) {
                    isAddObserver = true;
                    object.notifyAll();
                }
            });
            synchronized (object) {
                while (!isAddObserver) {
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void addObserverOnMain() {
        scope.onScopeStart(this);
    }

    /**
     * onError/onComplete 时调用此方法
     */
    final void removeObserver() {
        //Lifecycle移除监听器需要在主线程执行
        if (isMainThread() || !(scope instanceof LifecycleScope)) {
            scope.onScopeEnd();
        } else {
            HarmonySchedulers.mainThread().scheduleDirect(this::removeObserver);
        }
    }

    private boolean isMainThread() {
        return EventRunner.getMainEventRunner().isCurrentRunnerThread();
    }
}
