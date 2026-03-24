package ch.samt.gardenwherehouse.controller;

import ch.samt.gardenwherehouse.domain.Item;
import ch.samt.gardenwherehouse.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/items")
@Controller
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String loadItems(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "itemList";
    }

    @GetMapping("/{code}")
    public String loadItemByCode(@PathVariable String code, Model model) {
        Item itemByCode = itemService.findByCode(code);
        model.addAttribute("items", itemByCode);
        return "itemList";
    }

    @GetMapping("/sell")
    public String sellItem(Model model,
                       @RequestParam (value = "code", required = false) String code) {
        itemService.sellByCode(code);

        return "redirect:/items/" + code;
    }


    @GetMapping("/add")
    public String addItem(Model model,
                          @RequestParam (value = "code", required = false) String code,
                          @RequestParam (value = "number", required = false) Integer number) {

        itemService.addByCodeAndNumber(code, number);

        return "redirect:/items/" + code;
    }

    @GetMapping("/insert")
    public String loadInsertPage(@ModelAttribute Item item) {
        return "insertItem";
    }

    @PostMapping("/insert")
    public String insertItem (@Valid Item item, Errors errors) {
        if (errors.hasErrors()) {
            return "itemList";
        }
        else if(itemService.findByCode(item.getCode()) == null) {
            Item savedItem = itemService.save(item);
            if (savedItem == null) {
                throw new RuntimeException("Item not saved");
            }
        }
        else {
            throw new RuntimeException("Duplicate item code");
        }
        return "redirect:/items";

    }


}
