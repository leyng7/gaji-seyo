package com.gajiseyo.modules.item.repository;

import com.gajiseyo.modules.item.domain.Item;
import com.gajiseyo.modules.item.repository.custom.ItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

}
