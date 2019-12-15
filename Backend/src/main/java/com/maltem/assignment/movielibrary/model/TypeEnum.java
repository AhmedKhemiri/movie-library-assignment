package com.maltem.assignment.movielibrary.model;

public enum TypeEnum {
    ACTION("action"),
    SCIENCE_FICTION("Sci-Fi"),
    COMEDY("Comedy"),
    HORROR("Horror"),
    THRILLER("Thriller"),
    FANTASY("Fantasy");
    private String type;

    TypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
