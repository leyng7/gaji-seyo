package com.gajiseyo.modules.item.service;

import com.gajiseyo.modules.item.domain.Item;
import com.gajiseyo.modules.item.dto.ItemForm;
import com.gajiseyo.modules.item.repository.ItemRepository;
import com.gajiseyo.modules.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void insert(ItemForm form, Member createdBy) {

        Item item = Item.create(
                form.getTitle(),
                form.getCategory(),
                form.getPrice(),
                form.isSuggested(),
                form.isShared(),
                form.getContents(),
                createdBy
        );

        itemRepository.save(item);
    }

    public void update(Long itemId, ItemForm form) {
        Item item = itemRepository.findById(itemId).orElseThrow();

        item.update(
                form.getTitle(),
                form.getCategory(),
                form.getPrice(),
                form.isSuggested(),
                form.isShared(),
                form.getContents()
        );

    }
}
