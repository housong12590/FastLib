package com.hstmpl.net;


import com.hstmpl.net.error.RequestError;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;

@SuppressWarnings("unchecked")
public class Transformer {

    public static <T> SingleTransformer<Result<T>, T> resultFunc() {
        return new SingleTransformer<Result<T>, T>() {
            @NotNull
            @Override
            public SingleSource<T> apply(Single<Result<T>> upstream) {
                return upstream.map(new Function<Result<T>, T>() {
                    @Override
                    public T apply(Result<T> r) throws Exception {
                        if (r.isSuccess()) {
                            if (r.getData() == null) {
                                r.setData((T) new Object());
                            }
                            return r.getData();
                        }
                        throw new RequestError(r.getMsg());
                    }
                });
            }
        };
    }
}
