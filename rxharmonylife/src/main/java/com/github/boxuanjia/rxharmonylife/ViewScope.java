package com.github.boxuanjia.rxharmonylife;

import io.reactivex.disposables.Disposable;
import ohos.agp.components.Component;

public final class ViewScope implements Scope, Component.BindStateChangedListener {

    private final Component view;

    private Disposable disposable;

    private ViewScope(Component view) {
        this.view = view;
    }

    static ViewScope from(Component view) {
        return new ViewScope(view);
    }

    @Override
    public void onScopeStart(Disposable d) {
        disposable = d;
        final Component view = this.view;
        if (view == null) return;
        view.setBindStateChangedListener(this);
    }

    @Override
    public void onScopeEnd() {
        final Component view = this.view;
        if (view == null) return;
        view.removeBindStateChangedListener(this);
    }

    @Override
    public void onComponentBoundToWindow(Component view) {

    }

    @Override
    public void onComponentUnboundFromWindow(Component view) {
        disposable.dispose();
        view.removeBindStateChangedListener(this);
    }
}