package com.jcminarro;

class Dependencies {

    private static String KOTLIN_VERSION = '1.1.51'
    private static String ANDROID_BUILD_TOOL_VERSION = '3.0.0'
    private static String APP_COMPAT_VERSION = '26.1.0'
    private static String JUNIT_VERSION = '4.12'
    private static String ANDROID_TEST_RUNNER_VERSION = '1.0.1'
    private static String ESPRESSO_VERSION = '3.0.1'

    static String kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
    static String androidBuildToolGradlePlugin = "com.android.tools.build:gradle:$ANDROID_BUILD_TOOL_VERSION"
    static String kotlinSTDLib = "org.jetbrains.kotlin:kotlin-stdlib-jre7:$KOTLIN_VERSION"
    static String appCompat = "com.android.support:appcompat-v7:$APP_COMPAT_VERSION"
    static String jUnit = "junit:junit:$JUNIT_VERSION"
    static String AndroidTestRunner = "com.android.support.test:runner:$ANDROID_TEST_RUNNER_VERSION"
    static String espresso = "com.android.support.test.espresso:espresso-core:$ESPRESSO_VERSION"
}