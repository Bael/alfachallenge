package com.github.bael.alfa.task5.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PromoModel {

    public static final int NET_POLICY_ID = -1;
    private Map<Integer, Double> loyalCards = new HashMap<>();

    public void addLoyalDiscount(Integer shopId, double discount) {
        loyalCards.put(shopId, discount);
    }

    public double getLoyalDiscount(Integer shopId) {
        double totalDiscount = loyalCards.getOrDefault(-1, 0d);
        if (shopId != null) {
            return loyalCards.getOrDefault(shopId, totalDiscount);
        } else {
            return totalDiscount;
        }
    }


    /**
     * Карта скидок по количеству в разрезе
     * [itemId, [shopId, rule]]
     * */
    private Map<String, Map<Integer, QuantityDiscountRule>> quantityDiscountMap = new HashMap<>();
    public void addItemCountRule(Integer shopId, String itemId, Integer triggerQuantity, Integer bonusQuantity) {
        QuantityDiscountRule rule = QuantityDiscountRule.builder()
                .shopId(shopId)
                .itemId(itemId)
                .triggerQuantity(triggerQuantity)
                .bonusQuantity(bonusQuantity)
                .build();
        Map<Integer, QuantityDiscountRule> itemQuantityDiscounts = quantityDiscountMap.getOrDefault(itemId, new HashMap<>());
        itemQuantityDiscounts.put(shopId, rule);
        quantityDiscountMap.put(itemId, itemQuantityDiscounts);

    }

    public Integer maxFreeQuantity(Integer shopId, String itemId, Integer quantity) {
        if (!quantityDiscountMap.containsKey(itemId)) {
            return 0;
        }

        Map<Integer, QuantityDiscountRule>
                shopQuantityDiscountRuleMap = quantityDiscountMap.get(itemId);

        // локальная политика приоритетнее
        if (shopQuantityDiscountRuleMap.containsKey(shopId)) {
            return shopQuantityDiscountRuleMap.get(shopId).calculateFreeCount(quantity);
        }

        // применяем общесетевые скидки если есть
        if (shopQuantityDiscountRuleMap.containsKey(-1)) {
            return shopQuantityDiscountRuleMap.get(-1).calculateFreeCount(quantity);
        }
        return 0;
    }


    /**
     * скидки на группы - в разрезе магазинов
     */
    private Map<String, Map<Integer, Double>> groupDiscountMap = new HashMap<>();

    public void addGroupDiscount(Integer shopId, String groupId, Double discount) {
        Map<Integer, Double> shopDiscountMap = groupDiscountMap.getOrDefault(groupId, new HashMap<>());

        // сохраняем максимальную скидку
        double newDiscount = Math.max(discount, shopDiscountMap.getOrDefault(shopId, 0d));
        shopDiscountMap.put(shopId, newDiscount);
        groupDiscountMap.put(groupId, shopDiscountMap);
    }

    public Double getMaxGroupDiscount(Integer shopId, String groupId) {
        Map<Integer, Double> discountMap = groupDiscountMap.get(groupId);
        if (discountMap != null) {
            if (discountMap.containsKey(shopId)) {
                return discountMap.get(shopId);
            }
            if (discountMap.containsKey(NET_POLICY_ID)) {
                return discountMap.get(NET_POLICY_ID);
            }
        }
        return 0d;
    }



}
