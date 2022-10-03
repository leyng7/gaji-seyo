package com.gajiseyo.modules.item.domain;

import com.gajiseyo.modules.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "TB_ITEM")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id")
  private Long id;

  private String title;

  @Enumerated(EnumType.STRING)
  private Category category;

  private String contents;

  private long price;

  private int views;

  @Enumerated(EnumType.STRING)
  private Status status;

  private boolean removed;

  private boolean suggested;

  private boolean shared;

  public static Item create(String title) {
    Item item = new Item();
    item.setTitle(title);
    return item;
  }

}
