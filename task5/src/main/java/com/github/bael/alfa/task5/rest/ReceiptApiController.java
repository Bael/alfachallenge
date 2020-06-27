package com.github.bael.alfa.task5.rest;

import com.github.bael.alfa.task5.api.ReceiptApi;
import com.github.bael.alfa.task5.domain.PromoCalculator;
import com.github.bael.alfa.task5.model.FinalPriceReceipt;
import com.github.bael.alfa.task5.model.ShoppingCart;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-06-27T16:49:06.845+07:00")

@Controller
public class ReceiptApiController implements ReceiptApi {

    @Autowired
    public void setPromoCalculator(PromoCalculator promoCalculator) {
        this.promoCalculator = promoCalculator;
    }


    private PromoCalculator promoCalculator;

    private static final Logger log = LoggerFactory.getLogger(ReceiptApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ReceiptApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<FinalPriceReceipt> calculate(@ApiParam(value = "Данное о магазине и товарах в корзине" ,required=true )  @Valid @RequestBody ShoppingCart cart) {
        FinalPriceReceipt receipt = promoCalculator.calculateReceipt(cart);
        return new ResponseEntity<FinalPriceReceipt>(receipt, HttpStatus.OK);
    }

}
