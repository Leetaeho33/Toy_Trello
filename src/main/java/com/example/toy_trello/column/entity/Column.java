package com.example.toy_trello.column.entity;
import com.example.toy_trello.board.entity.Board;
import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
public class Column {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "board_id") // 수정 전: "board_id"
    private Board board;

    @jakarta.persistence.Column(name = "column_order") // 정답이 없는 부분.
    private Long order;

    public Column() {
        // 생성자
    }

    public Column(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // 보드 및 카드와 관계설정

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setOrder(Long order) {
        this.order = order;
    }
}



