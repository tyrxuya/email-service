package com.tinqinacademy.email.api;

public final class EmailRestApiPaths {
    private static final String BASE = "api/v1";
    private static final String EMAIL = BASE + "/email";

    public static final String SEND_CONFIRMATION = EMAIL + "/confirmation";
    public static final String SEND_RECOVERY = EMAIL + "/recovery";
}
