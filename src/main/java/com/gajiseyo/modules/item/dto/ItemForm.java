package com.gajiseyo.modules.item.dto;

import com.gajiseyo.modules.item.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter @Setter
public class ItemForm {

  private String title;

  @Enumerated(EnumType.STRING)
  private Category category;

  private long price;

  private boolean suggested;

  private boolean shared;

  private String contents;

}
