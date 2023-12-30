package com.example.toy_trello.domain.comment.entity;


import com.example.toy_trello.domain.card.entity.Card;
import com.example.toy_trello.domain.comment.dto.CommentRequestDto;
import com.example.toy_trello.domain.user.User;
import com.example.toy_trello.domain.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    private String content;


    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "card_id")
    Card card;

    public Comment(CommentRequestDto commentRequestDto, Card card, User user){

        this.content = commentRequestDto.getContent();
        this.card = card;
        this.user = user;
    }
    public void updateComment(CommentRequestDto commentRequestDto){
        this.content = commentRequestDto.getContent();
    }

}
