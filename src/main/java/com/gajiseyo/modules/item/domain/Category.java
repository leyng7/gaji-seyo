package com.gajiseyo.modules.item.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

  DIGITAL_DEVICE("�����б��"),
  HOUSEHOLD_APPLIANCES("��Ȱ����"),
  FURNITURE("����"),
  LIVING("��Ȱ"),
  INFANT_CHILD("���Ƶ�"),
  CHILDREN_BOOKS("���� ����"),
  WOMEN_CLOTHING("���� �Ƿ�"),
  WOMEN_ACCESSORIES("���� ��ȭ"),
  MEN_FASHION_ACCESSORIES("���� �м���ȭ"),
  BEAUTY("�̿�"),
  SPORTS("������"),
  HOBBY_GAME_RECORD("��� ���� ����"),
  BOOKS("����"),
  USED_CAR("�߰���"),
  TICKET("Ƽ��"),
  PROCESSED_FOOD("������ǰ"),
  PET_SUPPLIES("�ݷ�������ǰ"),
  PLANT("�Ĺ�"),
  ETC("��Ÿ"),
  BUY("��ϴ�");

  private final String displayValue;

}
