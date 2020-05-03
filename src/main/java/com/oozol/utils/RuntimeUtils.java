package com.oozol.utils;

public class RuntimeUtils {
    private static final String MODE_NORMAL = "NORMAL";
    private static final String MODE_MOCK = "MOCK";
    private static final String ENV_PROD = "PROD";
    private static final String ENV_STAG = "STAGING";
    private static final String ENV_BETA = "BETA";
    private static final String ENV_DEV = "DEV";
    private static String MODE = "NORMAL";
    private static String ENV = "DEV";
    private static String APP_KEY = "";

    public RuntimeUtils() {
    }

    public static boolean isMockMode() {
        return MODE.equals("MOCK");
    }

    public static boolean isNormalMode() {
        return !isMockMode();
    }

    public static boolean isProdEnv() {
        return ENV.equals("PROD");
    }

    public static boolean isStagEnv() {
        return ENV.equals("STAGING");
    }

    public static boolean isBetaEnv() {
        return ENV.equals("BETA");
    }

    public static boolean isDevEnv() {
        return ENV.equals("DEV");
    }

    public static String getEnv() {
        return ENV;
    }

    public static String getAppKey() {
        return APP_KEY;
    }

    public static String reformatAppKey(String appKey) {
        return appKey.replace(".", "_");
    }

    static {
        String mode = System.getenv("SERVICE_MODE");
        if ("MOCK".equals(mode)) {
            MODE = "MOCK";
        }

        String env = System.getenv("SERVICE_ENV");
        if ("STAGING".equals(env)) {
            ENV = "STAGING";
        } else if ("BETA".equals(env)) {
            ENV = "BETA";
        } else if ("PROD".equals(env)) {
            ENV = "PROD";
        }

        APP_KEY = System.getenv("APP_KEY");
    }
}
