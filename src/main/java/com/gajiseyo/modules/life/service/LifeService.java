package com.gajiseyo.modules.life.service;

import com.gajiseyo.modules.life.domain.Life;
import com.gajiseyo.modules.life.dto.LifeForm;
import com.gajiseyo.modules.life.repository.LifeRepository;
import com.gajiseyo.modules.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LifeService {

    private final LifeRepository lifeRepository;

    public void insert(LifeForm form, Member createdBy) {

        Life life = Life.create(
                form.getCategory(),
                form.getContents(),
                createdBy
        );

        lifeRepository.save(life);
    }

    public void update(Long lifeId, LifeForm form) {
        Life life = lifeRepository.findById(lifeId).orElseThrow();

        life.update(
                form.getCategory(),
                form.getContents()
        );

    }

    public List<Life> findAll() {
        return lifeRepository.findAll();
    }

    public Optional<Life> findById(Long LifeId) {
        return lifeRepository.findById(LifeId);
    }

}
