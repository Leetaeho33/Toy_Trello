package com.example.toy_trello.domain.member.entity;

public enum MemberRole {
    LEADER("leader"),
    MEMBER("member");

    private final String role;

     MemberRole(String role) {
        this.role = role;
    }
}
