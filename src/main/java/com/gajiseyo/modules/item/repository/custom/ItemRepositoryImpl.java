package com.gajiseyo.modules.item.repository.custom;

import com.gajiseyo.infra.querydsl.Querydsl4RepositorySupport;
import com.gajiseyo.modules.item.domain.Item;
import com.gajiseyo.modules.item.dto.ItemSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static com.gajiseyo.modules.item.domain.QItem.item;

@Transactional(readOnly = true)
public class ItemRepositoryImpl extends Querydsl4RepositorySupport implements ItemRepositoryCustom {

    public ItemRepositoryImpl() {
        super(Item.class);
    }

    @Override
    public Page<Item> searchAll(Pageable pageable, ItemSearch search) {
        return applyPagination(
                pageable,
                contentQuery -> contentQuery
                        .select(item)
                        .from(item)
        );
    }

}
