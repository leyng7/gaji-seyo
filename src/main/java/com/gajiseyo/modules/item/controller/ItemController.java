package com.gajiseyo.modules.item.controller;

import com.gajiseyo.modules.item.domain.Item;
import com.gajiseyo.modules.item.dto.ItemForm;
import com.gajiseyo.modules.item.dto.ItemSearch;
import com.gajiseyo.modules.item.service.ItemService;
import com.gajiseyo.modules.member.auth.CurrentUser;
import com.gajiseyo.modules.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/items")
  public String list(@SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                     @ModelAttribute("search") ItemSearch search,
                     Model model) {

    model.addAttribute("list", itemService.getItemPage(pageable, search));

    return "user/item/list";
  }

  @GetMapping("/items/form")
  public String form(@ModelAttribute("form") ItemForm form,
                     Model model) {

    model.addAttribute("form", form);
    return "user/item/form";
  }

  @PostMapping("/items/form")
  public String insert(@CurrentUser Member currentUser,
                       @ModelAttribute("form") ItemForm form,
                       RedirectAttributes rttr) {

    itemService.insert(form, currentUser);

    rttr.addFlashAttribute("message", "당신의 소중한 가지가 정상적으로 등록되었습니다.");
    return "redirect:/items";
  }

  @GetMapping("/items/{id}")
  public String view(@PathVariable("id") Long itemId,
                     @CurrentUser Member currentUser,
                     Model model) {

    Item item = itemService.findById(itemId).orElseThrow();
    model.addAttribute("item", item);

    return "user/item/view";
  }

}
