package com.example.basicexample.utilities;

import com.example.basicexample.BuildConfig;

public class Constants {
    private static final String protocol = "https";
    public static final String hostDevelopment = "https://run.mocky.io/v3/";
    public static final String hostTesting = "";
    public static final String hostStaging = "";
    public static final String hostProduction = "";
    public static final String portDevelopment = "";
    public static final String portTesting = "";
    public static final String portStaging = "";
    public static final String apiKeyOpenWeather = "7913153ff1c484fa13e4767e1c0e6eff\n";

    @SuppressWarnings("SpellCheckingInspection")
    public static String getTokenAPI() {
        return String.format("Bearer %s", "");
    }

    @SuppressWarnings("ConstantConditions")
    public static String getBaseUrl() {
        switch(BuildConfig.BUILD_TYPE) {
            case "debug":
                return hostDevelopment;
                //return protocol.concat("://").concat(hostDevelopment).concat(":").concat(portDevelopment).concat("/api/");
            case "qatesting":
                return hostDevelopment;
                //return protocol.concat("://").concat(hostTesting).concat(":").concat(portTesting).concat("/api/");
            case "staging":
                return hostDevelopment;
                //return protocol.concat("://").concat(hostStaging).concat(":").concat(portStaging).concat("/api/");
            default:
            return hostDevelopment;
               // return protocol.concat("://").concat(hostProduction).concat("/api/");
        }
    }
}
