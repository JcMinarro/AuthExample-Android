package com.jcminarro;

class Dependencies {

    private static String KOTLIN_VERSION = '1.1.51'
    private static String ANDROID_BUILD_TOOL_VERSION = '3.0.0'
    private static String APP_COMPAT_VERSION = '26.1.0'
    private static String JUNIT_VERSION = '4.12'
    private static String ANDROID_TEST_RUNNER_VERSION = '1.0.1'
    private static String ESPRESSO_VERSION = '3.0.1'
    private static String DAGGER_VERSION = '2.8'
    private static String BUTTERKNIFE_VERSION = '8.5.1'
    private static String RETROFIT_VERSION = '2.1.0'
    private static String OK_HTTP_VERSION = '3.5.0'
    private static String OK2CURL_VERSION = '0.3.2'

    static String kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
    static String androidBuildToolGradlePlugin = "com.android.tools.build:gradle:$ANDROID_BUILD_TOOL_VERSION"
    static String kotlinSTDLib = "org.jetbrains.kotlin:kotlin-stdlib-jre7:$KOTLIN_VERSION"
    static String appCompat = "com.android.support:appcompat-v7:$APP_COMPAT_VERSION"
    static String jUnit = "junit:junit:$JUNIT_VERSION"
    static String AndroidTestRunner = "com.android.support.test:runner:$ANDROID_TEST_RUNNER_VERSION"
    static String espresso = "com.android.support.test.espresso:espresso-core:$ESPRESSO_VERSION"
    static String dagger = "com.google.dagger:dagger:$DAGGER_VERSION"
    static String daggerCompiler = "com.google.dagger:dagger-compiler:$DAGGER_VERSION"
    static String butterKnife = "com.jakewharton:butterknife:$BUTTERKNIFE_VERSION"
    static String butterKnifeCompiler = "com.jakewharton:butterknife-compiler:$BUTTERKNIFE_VERSION"
    static String retrofit = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
    static String retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"
    static String okHttpLogger = "com.squareup.okhttp3:logging-interceptor:$OK_HTTP_VERSION"
    static String ok2curl = "com.github.mrmike:Ok2Curl:$OK2CURL_VERSION"
}