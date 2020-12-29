package com.github.boxuanjia.rxharmonylife;

import io.reactivex.*;
import io.reactivex.parallel.ParallelFlowable;
import ohos.aafwk.ability.ILifecycle;
import ohos.aafwk.ability.Lifecycle.Event;
import ohos.agp.components.Component;

public final class RxLife {

    public static <T> RxConverter<T> as(ILifecycle owner) {
        return as(owner, Event.ON_STOP, false);
    }

    public static <T> RxConverter<T> as(Component view) {
        return as(ViewScope.from(view), false);
    }

    private static <T> RxConverter<T> as(ILifecycle owner, Event event, boolean onMain) {
        return as(LifecycleScope.from(owner, event), onMain);
    }

    private static <T> RxConverter<T> as(Scope scope, boolean onMain) {
        return new RxConverter<T>() {

            @Override
            public ObservableLife<T> apply(Observable<T> upstream) {
                return new ObservableLife<>(upstream, scope, onMain);
            }

            @Override
            public FlowableLife<T> apply(Flowable<T> upstream) {
                return new FlowableLife<>(upstream, scope, onMain);
            }

            @Override
            public ParallelFlowableLife<T> apply(ParallelFlowable<T> upstream) {
                return new ParallelFlowableLife<>(upstream, scope, onMain);
            }

            @Override
            public MaybeLife<T> apply(Maybe<T> upstream) {
                return new MaybeLife<>(upstream, scope, onMain);
            }

            @Override
            public SingleLife<T> apply(Single<T> upstream) {
                return new SingleLife<>(upstream, scope, onMain);
            }

            @Override
            public CompletableLife apply(Completable upstream) {
                return new CompletableLife(upstream, scope, onMain);
            }
        };
    }
}
