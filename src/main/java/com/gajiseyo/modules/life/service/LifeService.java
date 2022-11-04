package com.gajiseyo.modules.life.service;

import com.gajiseyo.modules.life.domain.Life;
import com.gajiseyo.modules.life.repository.LifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LifeService {

    private final LifeRepository lifeRepository;

    public List<Life> findAll() {
        return lifeRepository.findAll();
    }

}
