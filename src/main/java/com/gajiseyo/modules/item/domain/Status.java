package com.gajiseyo.modules.item.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

  SALE("판매"),
  RESERVED("예약"),
  SOLD_OUT("판매 완료");

  private final String displayValue;

}
