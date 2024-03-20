package com.demo.sdk.app;

import android.os.Bundle;

public interface SensitiveModuleServiceCallback {
    void onSuccess(Bundle bundle);
    void onError(String errorMsg);
}
