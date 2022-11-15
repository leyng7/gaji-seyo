package com.gajiseyo.modules.life.repository.custom;

import com.gajiseyo.modules.life.domain.Life;
import com.gajiseyo.modules.life.dto.LifeSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LifeRepositoryCustom {
    Page<Life> searchAll(Pageable pageable, LifeSearch search);
}
