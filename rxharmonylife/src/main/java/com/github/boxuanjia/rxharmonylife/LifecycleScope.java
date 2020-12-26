package com.github.boxuanjia.rxharmonylife;

import io.reactivex.disposables.Disposable;
import ohos.aafwk.ability.ILifecycle;
import ohos.aafwk.ability.Lifecycle;
import ohos.aafwk.ability.Lifecycle.Event;
import ohos.aafwk.ability.LifecycleStateObserver;
import ohos.aafwk.content.Intent;

public final class LifecycleScope implements Scope, LifecycleStateObserver {

    private final Lifecycle lifecycle;
    private final Event event;
    private Disposable disposable;

    private LifecycleScope(Lifecycle lifecycle, Event event) {
        this.lifecycle = lifecycle;
        this.event = event;
    }

    static LifecycleScope from(ILifecycle owner, Event event) {
        return new LifecycleScope(owner.getLifecycle(), event);
    }

    @Override
    public void onScopeStart(Disposable d) {
        this.disposable = d;
        onScopeEnd();
        final Lifecycle lifecycle = this.lifecycle;
        if (lifecycle == null)
            throw new NullPointerException("lifecycle is null");
        lifecycle.addObserver(this);
    }

    @Override
    public void onScopeEnd() {
        final Lifecycle lifecycle = this.lifecycle;
        if (lifecycle == null)
            throw new NullPointerException("lifecycle is null");
        lifecycle.removeObserver(this);
    }

    @Override
    public void onStateChanged(Event event, Intent intent) {
        if (event.equals(this.event)) {
            disposable.dispose();
            lifecycle.removeObserver(this);
        }
    }
}
