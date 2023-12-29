package com.example.toy_trello.column.entity;
import jakarta.persistence.*;


@Entity
public class Column {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Column() {
        // 생성자
    }

    public Column(String name) {
        this.name = name;
    }

    // 게터와 세터

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 보드 및 카드와 관계설정 필요
}

