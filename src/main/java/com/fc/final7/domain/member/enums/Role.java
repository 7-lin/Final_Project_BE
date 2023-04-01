package com.fc.final7.domain.member.enums;

public enum Role {

    MEMBER("회원"),
    ADMIN("관리자");


    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
