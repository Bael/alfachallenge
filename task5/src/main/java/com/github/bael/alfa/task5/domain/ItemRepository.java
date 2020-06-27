package com.github.bael.alfa.task5.domain;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemRepository {
    private Map<String, Item> stringItemMap = new HashMap<>();

    public Optional<Item> findById(String id) {
        return Optional.ofNullable(stringItemMap.get(id));
    }

    public void addItem(Item item) {
        stringItemMap.put(item.getId(), item);
    }

    public Item getById(String id) {
        return Optional.ofNullable(stringItemMap.get(id)).orElseThrow(RuntimeException::new);
    }


}
