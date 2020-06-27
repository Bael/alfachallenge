package com.github.bael.alfa.task5.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityDiscountRuleTest {

    @Test
    void calculateFreeCount() {
        QuantityDiscountRule rule = QuantityDiscountRule.builder()
                .bonusQuantity(2).triggerQuantity(3).build();
        assertEquals(2, rule.calculateFreeCount(6));
    }

    @Test
    void calculateFreeCount2() {
        QuantityDiscountRule rule = QuantityDiscountRule.builder()
                .bonusQuantity(2).triggerQuantity(3).build();
        assertEquals(1, rule.calculateFreeCount(4));
    }

    @Test
    void calculateFreeCount3() {
        QuantityDiscountRule rule = QuantityDiscountRule.builder()
                .bonusQuantity(2).triggerQuantity(3).build();
        assertEquals(0, rule.calculateFreeCount(3));
    }
}