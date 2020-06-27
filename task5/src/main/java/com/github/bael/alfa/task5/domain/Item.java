package com.github.bael.alfa.task5.domain;

import com.github.bael.alfa.task5.csv.Transformable;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.function.Function;


@Builder
@Data
@ToString
public class Item implements Transformable {
    private String id;
    private String name;
    private String groupCode;
    private BigDecimal price;

    public static Function<String[], Item> constructor() {
        return (String[] parameters) -> builder()
                .id(parameters[0])
                .name(parameters[1])
                .groupCode(parameters[2])
                .price(BigDecimal.valueOf(Double.parseDouble(parameters[3])))
                .build();

    }
}
