package com.github.boxuanjia.rxharmonylife;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

final class LifeObserver<T> extends AbstractLifecycle<Disposable> implements Observer<T> {

    private Observer<? super T> downstream;

    LifeObserver(Observer<? super T> downstream, Scope scope) {
        super(scope);
        this.downstream = downstream;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (DisposableHelper.setOnce(this, d)) {
            try {
                addObserver();
                downstream.onSubscribe(d);
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                d.dispose();
                onError(ex);
            }
        }
    }

    @Override
    public void onNext(T t) {
        if (isDisposed()) return;
        try {
            downstream.onNext(t);
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            get().dispose();
            onError(e);
        }
    }

    @Override
    public void onError(Throwable t) {
        if (isDisposed()) {
            RxJavaPlugins.onError(t);
            return;
        }
        lazySet(DisposableHelper.DISPOSED);
        try {
            removeObserver();
            downstream.onError(t);
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            RxJavaPlugins.onError(new CompositeException(t, e));
        }
    }

    @Override
    public void onComplete() {
        if (isDisposed()) return;
        lazySet(DisposableHelper.DISPOSED);
        try {
            removeObserver();
            downstream.onComplete();
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            RxJavaPlugins.onError(e);
        }
    }

    @Override
    public boolean isDisposed() {
        return DisposableHelper.isDisposed(get());
    }

    @Override
    public void dispose() {
        DisposableHelper.dispose(this);
    }
}
