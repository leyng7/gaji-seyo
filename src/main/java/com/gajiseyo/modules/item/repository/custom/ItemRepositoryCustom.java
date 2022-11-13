package com.gajiseyo.modules.item.repository.custom;

import com.gajiseyo.modules.item.domain.Item;
import com.gajiseyo.modules.item.dto.ItemSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> searchAll(Pageable pageable, ItemSearch search);
}
