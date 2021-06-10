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
            public SingleSource<T> apply(@NotNull Single<Result<T>> upstream) {
                return upstream.map(new Function<Result<T>, T>() {
                    @Override
                    public T apply(@NotNull Result<T> tResult) throws Exception {
                        if (tResult.getCode() == 1) {
                            if (tResult.getResult() == null) {
                                tResult.setResult((T) new Object());
                            }
                            return tResult.getResult();
                        }
                        throw new RequestError(tResult.getMsg());
                    }
                });
            }
        };
    }
}
