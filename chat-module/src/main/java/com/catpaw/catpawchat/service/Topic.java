package com.catpaw.catpawchat.service;

public enum Topic {

    INIT("/init"),
    ACTION("/action");

    private final String destination;

    Topic(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return this.destination;
    }
}
