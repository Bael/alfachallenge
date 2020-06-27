package com.github.bael.alfa.task5.rest;

import com.github.bael.alfa.task5.domain.PromoModel;
import com.github.bael.alfa.task5.model.ItemCountRule;
import com.github.bael.alfa.task5.model.ItemGroupRule;
import com.github.bael.alfa.task5.model.LoyaltyCardRule;
import com.github.bael.alfa.task5.model.PromoMatrix;

public class JsonPromoMatrixTransformer {
    public PromoModel convert(PromoMatrix promoMatrix) {
        PromoModel promoModel = new PromoModel();
        if (promoMatrix != null) {

            if (promoMatrix.getLoyaltyCardRules() != null) {
                for (LoyaltyCardRule rule : promoMatrix.getLoyaltyCardRules()) {
                    promoModel.addLoyalDiscount(rule.getShopId(), rule.getDiscount());
                }
            }

            if (promoMatrix.getItemCountRules() != null) {
                for (ItemCountRule rule : promoMatrix.getItemCountRules())
                {
                    promoModel.addItemCountRule(rule.getShopId(), rule.getItemId(), rule.getTriggerQuantity(), rule.getBonusQuantity());
                }
            }

            if (promoMatrix.getItemGroupRules() != null) {
                for (ItemGroupRule rule : promoMatrix.getItemGroupRules()) {
                    promoModel.addGroupDiscount(rule.getShopId(), rule.getGroupId(), rule.getDiscount());
                }
            }
        }

        return promoModel;
    }
}
