package com.github.bael.alfa.task5.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class OrderVariant {
    Map<String, Set<Item>> groupedItems = new HashMap<>();
    public Map<String, Set<Item>> getGroupedItems(Integer countInGroup) {
        Map<String, Set<Item>> map = new HashMap<>();
        for (Map.Entry<String, Set<Item>> entry : groupedItems.entrySet()) {
            if (entry.getValue().size() > countInGroup) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    Map<String, Integer> quantityMap = new HashMap<>();
    Map<String, BigDecimal> priceMap = new HashMap<>();
    Map<String, BigDecimal> totalPriceMap = new HashMap<>();


    public void add(Item item, Integer quantity, BigDecimal price) {
        Set<Item> itemSet = groupedItems.getOrDefault(item.getGroupCode(), new HashSet<>());
        itemSet.add(item);
        groupedItems.putIfAbsent(item.getGroupCode(), itemSet);
        quantityMap.put(item.getId(), quantity);
        priceMap.put(item.getId(), price);
        totalPriceMap.put(item.getId(), price.multiply(BigDecimal.valueOf(quantity)));
    }

    public BigDecimal getTotal() {
        return totalPriceMap.values().stream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    Map<String, Integer> freeQuantityMap = new HashMap<>();



    public void addFreeQuantity(String itemId, Integer freeQuantity) {
        freeQuantityMap.put(itemId, freeQuantity);
    }

    public void applyDiscountCard(double loyalDiscount) {
        for (Map.Entry<String, BigDecimal> entry : totalPriceMap.entrySet()) {
            totalPriceMap.put(entry.getKey(),
                    entry.getValue().multiply(
                            BigDecimal.valueOf(loyalDiscount)));
        }
    }

    public void applyGroupDiscount(String groupId, double loyalDiscount) {
        Set<Item> orDefault = groupedItems.getOrDefault(groupId, Collections.emptySet());
        orDefault.forEach(item -> applyDiscount(item.getId(), loyalDiscount));
    }

    private void applyDiscount(String itemId, double discount) {
        BigDecimal orDefault = totalPriceMap.getOrDefault(itemId, BigDecimal.ZERO);
        totalPriceMap.put(itemId,
                orDefault.multiply(BigDecimal.valueOf(discount)));
    }

    public BigDecimal getDiscount() {
        return getTotalRegularPrice().subtract(getTotal());
    }

    public List<FinalPricePositionRow> getRows() {
        return priceMap.entrySet().stream()
                .map(entry ->
                        FinalPricePositionRow.builder()
                                .itemId(entry.getKey())
                        .price(totalPriceMap.get(entry.getKey()))
                        .regularPrice(priceMap.get(entry.getKey()))
                        .build()
                ).collect(Collectors.toList());
    }
    public BigDecimal getTotalRegularPrice() {
        return priceMap.entrySet().stream().map(entry ->
                entry.getValue().multiply(BigDecimal.valueOf(quantityMap.get(entry.getKey()))))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Builder
    @Data
    public static class FinalPricePositionRow {
        private String itemId;
        private String name;
        private BigDecimal regularPrice;
        private BigDecimal price;
    }

}
