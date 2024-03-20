package com.demo.client_sdk;

import android.os.Bundle;

public interface SDKProviderServiceCallback {
    void onSuccess(Bundle bundle);
    void onError(String errorMsg);
}
