package com.demo.client_sdk;

import android.os.Bundle;

import java.util.concurrent.Executor;

public class SDKProviderServiceCallbackProxy extends ISDKProviderServiceCallbackInterface.Stub{
    private final SDKProviderServiceCallback mCallback;
    private final Executor mExecutor;

    public SDKProviderServiceCallbackProxy(Executor executor, SDKProviderServiceCallback callback) {
        this.mExecutor = executor;
        this.mCallback = callback;
    }

    public void localOnSuccess(Bundle params) {
        this.mCallback.onSuccess(params);
    }

    @Override
    public void onSuccess(final Bundle params) {
        this.mExecutor.execute(() -> SDKProviderServiceCallbackProxy.this.localOnSuccess(params));
    }

    public void localOnError(String errorMsg) {
        this.mCallback.onError(errorMsg);
    }

    @Override
    public void onError(final String errorMsg) {
        this.mExecutor.execute(() -> SDKProviderServiceCallbackProxy.this.localOnError(errorMsg));
    }
}
