package com.example.toy_trello.domain.card.entity;

import com.example.toy_trello.domain.card.dto.CardUpdateRequestDto;
import com.example.toy_trello.domain.comment.entity.Comment;
import com.example.toy_trello.domain.user.User;
import com.example.toy_trello.domain.util.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Card extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cardId;

  @Column
  private String cardName;

  @Column
  private String cardDescription;

  @Column
  private String cardColor;

  @Temporal(TemporalType.DATE)
  private Date dueDate;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;


  @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
  private List<Comment> commentList = new ArrayList<>();

  //  @Column
//  private Userstate userstate;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "column_id", nullable = false)
//  private Column column;


  @Builder
  public Card(String cardName,String cardDescription,String cardColor,User user,Date dueDate){
    this.cardName = cardName;
    this.cardDescription = cardDescription;
    this.cardColor = cardColor;
    this.user = user;
    this.dueDate = dueDate;
  }

  public void update(CardUpdateRequestDto cardUpdateRequestDto) {
    this.cardName = cardUpdateRequestDto.cardName();
    this.cardDescription = cardUpdateRequestDto.cardDescription();
    this.cardColor = cardUpdateRequestDto.cardColor();
  }

  public void userUpdate(User user){
    this.user = user;
  }




}
