package com.github.bael.alfa.task5.domain;

import com.github.bael.alfa.task5.model.*;
import com.github.bael.alfa.task5.rest.JsonPromoMatrixTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PromoCalculator {

    private final ItemRepository itemRepository;
    private final GroupRepository groupRepository;


    PromoModel promoModel;
    public void reload(PromoModel promoModel) {
        this.promoModel = promoModel;
    }


    public FinalPriceReceipt calculateReceipt(ShoppingCart cart) {
        FinalPriceReceipt receipt = new FinalPriceReceipt();
        BigDecimal total = BigDecimal.valueOf(0.00D);
        BigDecimal discount = BigDecimal.valueOf(0.00D);




        OrderVariant variant = new OrderVariant();
        if (cart != null && cart.getPositions() != null) {
            for (ItemPosition position : cart.getPositions()) {
                Item item = itemRepository.getById(position.getItemId());
                variant.add(item, position.getQuantity(), item.getPrice());
                // вычисляем бесплатный сыр
                Integer free = promoModel.maxFreeQuantity(cart.getShopId(), item.getId(), position.getQuantity());
                variant.addFreeQuantity(item.getId(), free);
            }
            // применяем общую скидку
            double loyalDiscount = promoModel.getLoyalDiscount(cart.getShopId());
            variant.applyDiscountCard(loyalDiscount);

            // применяем групповые политики
            Map<String, Set<Item>> groupedItems = variant.getGroupedItems(2);
            for (Map.Entry<String, Set<Item>> entry : groupedItems.entrySet()) {
                Double maxGroupDiscount = promoModel.getMaxGroupDiscount(cart.getShopId(), entry.getKey());
                variant.applyGroupDiscount(entry.getKey(), maxGroupDiscount);
            }

            for (OrderVariant.FinalPricePositionRow row : variant.getRows()) {
                FinalPricePosition resultPosition = new FinalPricePosition();
                resultPosition.setId(row.getItemId());
                resultPosition.setName(itemRepository.getById(row.getItemId()).getName());
                resultPosition.setRegularPrice(row.getRegularPrice());
                resultPosition.setPrice(row.getPrice());
                receipt.addPositionsItem(resultPosition);
            }
            total = variant.getTotal();
            discount = variant.getDiscount();

        } else {
            receipt.setPositions(Collections.emptyList());

        }
        discount = discount.setScale(2, RoundingMode.HALF_EVEN);
        total = total.setScale(2, RoundingMode.HALF_EVEN);
        receipt.discount(discount);
        receipt.total(total);
        return receipt;
    }

//    public Map<String, Double> maxDiscounts(List<Item> items, boolean loyaltyCardApplied, String shopId) {
//
//
//    }


}
