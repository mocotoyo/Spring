package com.example.demo.controller;

import com.example.demo.model.Item;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    private List<Item> items = new ArrayList<>();

    public ItemController() {
        items.add(new Item(1, "Item uno"));
        items.add(new Item(2, "Item dos"));
    }

    @GetMapping
    public List<Item> getItems() {
        return items;
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable int id) {
        return items.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No encontrado"));
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        int id = items.stream().mapToInt(Item::getId).max().orElse(0) + 1;
        item.setId(id);
        items.add(item);
        return item;
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable int id, @RequestBody Item update) {
        Optional<Item> opt = items.stream().filter(i -> i.getId() == id).findFirst();
        if (opt.isEmpty()) throw new RuntimeException("No encontrado");
        Item item = opt.get();
        item.setName(update.getName());
        return item;
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable int id) {
        items.removeIf(i -> i.getId() == id);
    }
}
