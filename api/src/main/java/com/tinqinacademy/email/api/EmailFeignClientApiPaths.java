package com.tinqinacademy.email.api;

public final class EmailFeignClientApiPaths {
    private static final String POST = "POST";
    private static final String SPACE_SYMBOL = " ";

    public static final String SEND_CONFIRMATION = POST + SPACE_SYMBOL + EmailRestApiPaths.SEND_CONFIRMATION;
    public static final String SEND_RECOVERY = POST + SPACE_SYMBOL + EmailRestApiPaths.SEND_RECOVERY;
}
