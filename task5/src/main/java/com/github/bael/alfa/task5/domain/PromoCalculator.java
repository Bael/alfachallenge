package com.github.bael.alfa.task5.domain;

import com.github.bael.alfa.task5.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PromoCalculator {

    private final ItemRepository itemRepository;
    private final GroupRepository groupRepository;

     public void reload(PromoMatrix promoMatrix) {

    }

    public FinalPriceReceipt calculateReceipt(ShoppingCart cart) {
        FinalPriceReceipt receipt = new FinalPriceReceipt();
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
        for (ItemPosition position : cart.getPositions()) {
            FinalPricePosition resultPosition = new FinalPricePosition();
            resultPosition.setId(position.getItemId());

            Item item = itemRepository.getById(position.getItemId());
            BigDecimal price = item.getPrice();
            BigDecimal totalRow = price.multiply(BigDecimal.valueOf(position.getQuantity()));
            resultPosition.setPrice(totalRow);
            resultPosition.setRegularPrice(price);
            resultPosition.setName(item.getName());
            receipt.addPositionsItem(resultPosition);
            total = total.add(totalRow);
        }
        receipt.discount(discount);
        receipt.total(total);
        return receipt;
    }

//    public Map<String, Double> maxDiscounts(List<Item> items, boolean loyaltyCardApplied, String shopId) {
//
//
//    }


}
