package com.gajiseyo.modules.life.dto;

import com.gajiseyo.modules.life.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter @Setter
public class LifeForm {

    @Enumerated(EnumType.STRING)
    private Category category;

    private String contents;

}
