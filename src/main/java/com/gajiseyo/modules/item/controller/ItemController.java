package com.gajiseyo.modules.item.controller;

import com.gajiseyo.modules.item.domain.Item;
import com.gajiseyo.modules.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemRepository itemRepository;

  @GetMapping("/items")
  public String list(Model model) {

    model.addAttribute("list", itemRepository.findAll());

     return "user/item/list";
  }

  @GetMapping("/items/save")
  public void save() {
    Item item = Item.create("테스트 제목");
    itemRepository.save(item);
  }
}
