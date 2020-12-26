package com.github.boxuanjia.rxharmonylife;

import io.reactivex.*;
import io.reactivex.parallel.ParallelFlowableConverter;

public interface RxConverter<T> extends
        ObservableConverter<T, ObservableLife<T>>,
        FlowableConverter<T, FlowableLife<T>>,
        ParallelFlowableConverter<T, ParallelFlowableLife<T>>,
        MaybeConverter<T, MaybeLife<T>>,
        SingleConverter<T, SingleLife<T>>,
        CompletableConverter<CompletableLife> {
}
