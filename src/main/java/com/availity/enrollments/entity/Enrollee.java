package com.availity.enrollments.entity;

import lombok.Data;

@Data
public class Enrollee implements Comparable<Enrollee> {
    private String userId;
    private String firstName;
    private String lastName;
    private int version;
    private String insuranceCompany;

    public Enrollee(){}

    public Enrollee(String userId, String firstName, String lastName, int version, String insuranceCompany) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.version = version;
        this.insuranceCompany = insuranceCompany;
    }

    // Implementing Comparable interface to compare Enrollees
    @Override
    public int compareTo(Enrollee other) {
        if (this.userId.equals(other.userId)) {
            return Integer.compare(other.version, this.version);
        }
        int lastNameComparison = this.lastName.compareTo(other.lastName);
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }
        return this.firstName.compareTo(other.firstName);
    }

    public String toCSVString() {
        return userId + "," + firstName + "," + lastName + "," + version + "," + insuranceCompany;
    }
}
