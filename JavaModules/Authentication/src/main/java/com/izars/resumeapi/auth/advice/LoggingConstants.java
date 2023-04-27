package com.izars.resumeapi.auth.advice;

public class LoggingConstants {
    //    public static final String OBSERVED_PKGS = "execution(* com.izars.resumeapi.*.*(..) )";
    public static final String OBSERVED_PKGS = "within(com.izars.resumeapi..*)";
    public static final String EXCLUDED_PKGS = " && !execution(* com.izars.resumeapi.auth.config.*.*(..) )" +
            " && !execution(* com.izars.resumeapi.auth.controller.CustomExceptionHandler.*(..))" +
            " && !execution(* com.izars.resumeapi.auth.monitor.*.*(..))";
}
