package com.github.bael.alfa.task5.rest;

import com.github.bael.alfa.task5.api.PromoApi;
import com.github.bael.alfa.task5.domain.PromoCalculator;
import com.github.bael.alfa.task5.model.PromoMatrix;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-06-27T16:49:06.845+07:00")

@Controller
public class PromoApiController implements PromoApi {

    private static final Logger log = LoggerFactory.getLogger(PromoApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public void setPromoCalculator(PromoCalculator promoCalculator) {
        this.promoCalculator = promoCalculator;
    }


    private PromoCalculator promoCalculator;

    @org.springframework.beans.factory.annotation.Autowired
    public PromoApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> setMatrix(@ApiParam(value = "Матрица промо-механик" ,required=true )  @Valid @RequestBody PromoMatrix body) {
        String accept = request.getHeader("Accept");
        promoCalculator.reload(body);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
