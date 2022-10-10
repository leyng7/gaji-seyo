package com.gajiseyo.modules.item.service;

import com.gajiseyo.modules.item.domain.Item;
import com.gajiseyo.modules.item.dto.ItemForm;
import com.gajiseyo.modules.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  public void insert(ItemForm form) {
    Item item = Item.create(
        form.getTitle(),
        form.getCategory(),
        form.getPrice(),
        form.isSuggested(),
        form.isShared(),
        form.getContents()
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
