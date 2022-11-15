package com.gajiseyo.modules.life.repository.custom;

import com.gajiseyo.infra.querydsl.Querydsl4RepositorySupport;
import com.gajiseyo.modules.life.domain.Life;
import com.gajiseyo.modules.life.dto.LifeSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static com.gajiseyo.modules.life.domain.QLife.life;

@Transactional(readOnly = true)
public class LifeRepositoryImpl extends Querydsl4RepositorySupport implements LifeRepositoryCustom {

    public LifeRepositoryImpl() {
        super(Life.class);
    }

    @Override
    public Page<Life> searchAll(Pageable pageable, LifeSearch search) {
        return applyPagination(
                pageable,
                contentQuey -> contentQuey
                        .select(life)
                        .from(life)
        );
    }

}
