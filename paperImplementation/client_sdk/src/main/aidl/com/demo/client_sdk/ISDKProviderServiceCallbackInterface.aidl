// ISDKProviderServiceCallbackInterface.aidl
package com.demo.client_sdk;

// Declare any non-default types here with import statements

interface ISDKProviderServiceCallbackInterface {
    void onSuccess(in Bundle bundle);
    void onError(String errorMsg);
}