package com.github.boxuanjia.rxharmonylife;

import io.reactivex.disposables.Disposable;

abstract class RxSource<E> {

    Scope   scope;
    boolean onMain;

    RxSource(Scope scope, boolean onMain) {
        this.scope = scope;
        this.onMain = onMain;
    }

    public abstract Disposable subscribe();

    /**
     * Subscribes the given Observer to this ObservableSource instance.
     *
     * @param observer the Observer, not null
     * @throws NullPointerException if {@code observer} is null
     */
    public abstract void subscribe(E observer);

    public <O extends E> O subscribeWith(O observer) {
        subscribe(observer);
        return observer;
    }
}
