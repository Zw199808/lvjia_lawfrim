package com.xinou.lawfrim.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author: XGLLHZ
 * @date: 2020/6/18 上午10:02
 * @description:
 */
@Configuration
public class VersionConfig {

    private static String androidMinVersion;

    private static String androidNowVersion;

    private static String iosMinVersion;

    private static String iosNowVersion;

    private Integer appType;

    @Value("${app.version.androidMinVersion}")
    public void setAndroidMinVersion(String androidMinVersion) {
        VersionConfig.androidMinVersion = androidMinVersion;
    }

    @Value("${app.version.androidNowVersion}")
    public void setAndroidNowVersion(String androidNowVersion) {
        VersionConfig.androidNowVersion = androidNowVersion;
    }

    @Value("${app.version.iosMinVersion}")
    public void setIosMinVersion(String iosMinVersion) {
        VersionConfig.iosMinVersion = iosMinVersion;
    }

    @Value("${app.version.iosNowVersion}")
    public void setIosNowVersion(String iosNowVersion) {
        VersionConfig.iosNowVersion = iosNowVersion;
    }

    public static String getAndroidMinVersion() {
        return androidMinVersion;
    }

    public static String getAndroidNowVersion() {
        return androidNowVersion;
    }

    public static String getIosMinVersion() {
        return iosMinVersion;
    }

    public static String getIosNowVersion() {
        return iosNowVersion;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

}
