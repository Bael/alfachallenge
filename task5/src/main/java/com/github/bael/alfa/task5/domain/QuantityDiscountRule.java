package com.github.bael.alfa.task5.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QuantityDiscountRule {
    private Integer shopId;
    private Integer triggerQuantity;
    private Integer bonusQuantity;
    private String itemId;

    public Integer calculateFreeCount(Integer quantityInCheck) {
        if (quantityInCheck <= triggerQuantity) {
            return 0;
        }
        return Math.min(quantityInCheck - triggerQuantity, bonusQuantity);
    }
}
