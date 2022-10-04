package com.gajiseyo.modules.item.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

  DIGITAL_DEVICE("디지털기기"),
  HOUSEHOLD_APPLIANCES("생활가전"),
  FURNITURE("가구"),
  LIVING("생활"),
  INFANT_CHILD("유아동"),
  CHILDREN_BOOKS("유아 도서"),
  WOMEN_CLOTHING("여성 의류"),
  WOMEN_ACCESSORIES("여성 잡화"),
  MEN_FASHION_ACCESSORIES("남성 패션잡화"),
  BEAUTY("미용"),
  SPORTS("스포츠"),
  HOBBY_GAME_RECORD("취미 게임 음반"),
  BOOKS("도서"),
  USED_CAR("중고차"),
  TICKET("티켓"),
  PROCESSED_FOOD("가공식품"),
  PET_SUPPLIES("반려동물용품"),
  PLANT("식물"),
  ETC("기타"),
  BUY("삽니다");

  private final String displayValue;

}
