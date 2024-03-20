package com.demo.sdk.app;

import android.os.Bundle;

import com.demo.client_sdk.ISDKProviderServiceCallbackInterface;


import java.util.concurrent.Executor;

public class SensitiveModuleServiceCallbackProxy extends ISensitiveModuleSandboxServiceCallbackInterface.Stub {
    private final SensitiveModuleServiceCallback mCallback;
    private final Executor mExecutor;

    public SensitiveModuleServiceCallbackProxy(Executor executor, SensitiveModuleServiceCallback callback) {
        this.mExecutor = executor;
        this.mCallback = callback;
    }

    public void localOnSuccess(Bundle params) {
        this.mCallback.onSuccess(params);
    }

    @Override
    public void onSuccess(final Bundle params) {
        this.mExecutor.execute(() -> SensitiveModuleServiceCallbackProxy.this.localOnSuccess(params));
    }

    public void localOnError(String errorMsg) {
        this.mCallback.onError(errorMsg);
    }

    @Override
    public void onError(final String errorMsg) {
        this.mExecutor.execute(() -> SensitiveModuleServiceCallbackProxy.this.localOnError(errorMsg));
    }
}
