package com.ws.fastlib.network;


import com.ws.fastlib.network.error.RequestError;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;

public class Transformer {

    public static <T> SingleTransformer<Result<T>, T> resultFunc() {
        return new SingleTransformer<Result<T>, T>() {
            @Override
            public SingleSource<T> apply(Single<Result<T>> upstream) {
                return upstream.map(new Function<Result<T>, T>() {
                    @Override
                    public T apply(Result<T> tResult) throws Exception {
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
