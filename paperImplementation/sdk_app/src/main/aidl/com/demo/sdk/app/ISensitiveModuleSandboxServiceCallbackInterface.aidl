// ISensitiveModuleSandboxServiceCallbackInterface.aidl
package com.demo.sdk.app;

// Declare any non-default types here with import statements

interface ISensitiveModuleSandboxServiceCallbackInterface {
    void onSuccess(in Bundle bundle);
    void onError(String errorMsg);
}