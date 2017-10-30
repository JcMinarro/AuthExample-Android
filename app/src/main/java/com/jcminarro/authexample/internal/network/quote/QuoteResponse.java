package com.jcminarro.authexample.internal.network.quote;

import com.google.gson.annotations.SerializedName;

public class QuoteResponse {
    private final @SerializedName("author") String author;
    private final @SerializedName("message") String message;

    public QuoteResponse(String author, String message) {
        this.author = author;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }
}
