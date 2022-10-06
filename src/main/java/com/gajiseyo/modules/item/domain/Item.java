package com.gajiseyo.modules.item.domain;

import com.gajiseyo.modules.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "TB_ITEM")
@Entity
@Getter
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

  public Item(String title, Category category, long price, boolean suggested, boolean shared, String contents, int views, Status status, boolean removed) {
    this.title = title;
    this.category = category;
    this.price = price;
    this.suggested = suggested;
    this.shared = shared;
    this.contents = contents;
    this.views = views;
    this.status = status;
    this.removed = removed;
  }

  public static Item create(String title, Category category, long price, boolean suggested, boolean shared, String contents, int views, Status status, boolean removed) {
    return new Item(title, category, price, suggested, shared, contents, views, status, removed);
  }

}
