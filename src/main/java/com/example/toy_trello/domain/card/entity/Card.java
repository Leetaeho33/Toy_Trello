package com.example.toy_trello.domain.card.entity;

import com.example.toy_trello.domain.card.dto.CardUpdateRequestDto;
import com.example.toy_trello.domain.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  //  @Column
//  private Userstate userstate;


  @Builder
  public Card(String cardName,String cardDescription,String cardColor){
    this.cardName = cardName;
    this.cardDescription = cardDescription;
    this.cardColor = cardColor;
  }

  public void update(CardUpdateRequestDto cardUpdateRequestDto) {
    this.cardName = cardUpdateRequestDto.cardName();
    this.cardDescription = cardUpdateRequestDto.cardDescription();
    this.cardColor = cardUpdateRequestDto.cardColor();
  }

//  @ManyToOne
//  @JoinColumn(name = "user_id", nullable = false)
//  private User user;

//  @ManyToOne
//  @JoinColumn(name = "column_id", nullable = false)
//  private ColumnEntity column;

//  @OneToMany(mappedBy = "card")
//  private List<Comment> commentList = new ArrayList<>();

}
