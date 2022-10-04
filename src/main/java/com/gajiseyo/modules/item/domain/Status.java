package com.gajiseyo.modules.item.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

  SALE("�Ǹ�"),
  RESERVED("����"),
  SOLD_OUT("�Ǹ� �Ϸ�");

  private final String displayValue;

}
