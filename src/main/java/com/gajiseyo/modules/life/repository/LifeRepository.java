package com.gajiseyo.modules.life.repository;

import com.gajiseyo.modules.life.domain.Life;
import com.gajiseyo.modules.life.repository.custom.LifeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LifeRepository extends JpaRepository<Life, Long>, LifeRepositoryCustom {
}
