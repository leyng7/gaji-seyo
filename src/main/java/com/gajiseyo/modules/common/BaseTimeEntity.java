package com.gajiseyo.modules.common;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class BaseTimeEntity {

    @CreationTimestamp
    @Column(name = "reg_dt", updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "last_up_dt")
    private LocalDateTime lastUpdatedDate;

}