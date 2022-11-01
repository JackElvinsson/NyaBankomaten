package com.example.nyabankomaten;

public enum Interest {
    HOUSE("House", 1.05),
    VEHICLE("Vehicle", 1.08),
    PERSONAL("Personal", 1.10),
    EDUCATION("Education", 1.01),
    BUSINESS("Business", 1.20),
    NONE("No loan", 0);

    final String type;
    final double interest;
    Interest(String type, double interest) {
        this.type = type;
        this.interest = interest;
    }
}
