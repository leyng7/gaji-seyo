package com.gajiseyo.modules.item.service;

import com.gajiseyo.modules.item.domain.Item;
import com.gajiseyo.modules.item.dto.ItemForm;
import com.gajiseyo.modules.item.dto.ItemSearch;
import com.gajiseyo.modules.item.repository.ItemRepository;
import com.gajiseyo.modules.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    public Page<Item> getItemPage(Pageable pageable, ItemSearch search) {
        return itemRepository.searchAll(pageable, search);
    }
}
