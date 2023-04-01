package com.fc.final7.domain.member.enums;

public enum Gender {
    MALE("남성"), FEMALE("여성");

    private final String sex;

    Gender(String sex) {
        this.sex = sex;
    }

    public String getSex(){
        return sex;
    }
}
