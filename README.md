# PESP
PESP is a practical, clean-slate design and end-to-end systems that enables
privacy-preserving social-media SDKs against the emerging
privacy threat. 

## Description
1. paperImplementation: An Android studio Project containing the client_app(developers' app in the paper) and an sdk_app(social medial SDK in the paper).
2. facebook-android-sdk-main: An Android studio Project containing the original implementation of FB SDK and Twitther SDK from offical Facebook and Twitter code release, using as baseline with logging enabled for evaulation purpose.
3. evaluation table data: Original logging and metric computation code in Python used to fills the tables: Table 2 Performance overhead (single social SDK per app) and Table 3 Performance overhead (multiple social SDKs per app).

## Prerequisites 
### Hardware
- Real Android device (minimum version 13, r16)
- For installing Android Studio,
we recommend a system with 16GB of memory and a
solid-state drive of at least 16GB
### System Requirements
- Linux OS/macOS/Windows, preferably Ubuntu 22.04
LTS, macOS 14 (Apple M1/M2 chips are compatible; compatibility with Windows is untested)
### Software Dependencies
- Android Studio, preferably latest version v2023.2.1, with Gradle version 7.2
- Android emulator/real Android devices, preferably Pixel or other devices with Android version ≥ 13, r16
- python 3.11
- numpy ≥ v1.24.3
- scipy ≥ v1.11.1
## Environment setup
Android Studio installation: download the latest version from https://developer.android.com/studio and refer to the pro-
vided documentation for guidance on installation across differ-
ent operating systems https://developer.android.com/
studio/install. Opt for the standard in-
stallation process, and follow the on-screen instructions to
download all required components.

If you don't have a real Android device, you can use the Android emulator in Android Studio, which will be automatically downloaded.

If you use Ubuntu 22.04, you may need to install python 3.11,  numpy and scipy. You can follow the commands:
```
sudo apt install python3.11
```
numpy ≥ v1.24.3
```
python3.11 -m pip install numpy=1.24.3
```

scipy ≥ v1.11.1
```
python3.11 -m pip install scipy==1.11.1
```

## Basic Test
- For Android emulator, Android Studio is with Google Pixel 3a set as the default. Locate the plug icon next to "Running Devices" and click it. Then select the first virtual device, Pixel3a, from the list. The virtual device should power on within a few minutes.
- For numpy and scipy, you can use the following commands in your terminal:
python3 -c "import numpy; print(numpy.version.version)"; python3 -c "import scipy; print(scipy.version.version)".

## Usage 
### Build and Run paperImplementation Project
To test scenrio One with single client app and single sdk
Requirements: Android Studio, Android Device.
1. Open the paperImplementation project in to Android Studio: "File" -> "Open..." -> select the paperImplementation -> Click "OK".
2. Install the sdk_app: select "sdk_app" from the build profile drop down menu -> Click the "green play button".
3. Install the client_app: select "client_app" from the build profile drop down menu -> Click the "green play button".
4. Following steps should be on the android device you choosed to install the two apps.
5. Open the client_app by finding an app named : "Demo" with default  app icon.
6. Click "FB PROFILE": Will get to Figure 3 , which is scenrio 1 in the paper. 

Note that building an Android project in Android Studio for the first time may take longer.

### Build and Run facebook-android-sdk-main Project
1. Open the facebook-android-sdk-main project in to Android Studio: "File" -> "Open..." - select the facebook-android-sdk-main -> Click "OK".
2. Change JDK version: Open settings in Android Studio, select Build, Execution, Deployment -> Build Tools -> Gradle, select Gradle JDK -> Download JDK -> select version 1.8 (the last one) -> click "Download" -> click "Apply" and "OK". This process may take a while.
3. Install the sample apps: select "sample.FBLoginSample" or other samples from the build profile drop down menu -> Click the "green play button". After building the original Facebook SDK and the sample app, on the Android emulator or the real device, you can use basic functions like login or display user profile.

Note that building an Android project in Android Studio for the first time may take longer.


To generate data in Table 2
 In evaluation table data/table2: 

    python3 gen_table2_fblogin.py
    python3 gen_table2_fbprofile.py
    python3 gen_table2_twitter.py

To generate data in Table 3
 In each folder under evaluation table data/table3: 

    python3 basline_fblogin_gen.py
    python3 basline_fbprofile_gen.py
    python3 basline_twlogin_gen.py
    python3 basline_fbtwlogin_gen.py
    python3 basline_fbtwlogin_gen.py

## Other materials
Dataset for Type I and Type II workflows of social SDK usage in 200 mobile apps: https://docs.google.com/spreadsheets/d/1bV0y3nN93ckKuIW_P_g5bO-LCesOnBB_/edit#gid=552520127