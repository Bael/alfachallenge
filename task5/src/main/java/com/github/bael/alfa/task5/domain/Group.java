package com.github.bael.alfa.task5.domain;

import com.github.bael.alfa.task5.csv.Transformable;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.function.Function;

@Builder
@Data
@ToString
public class Group implements Transformable {
    private String code;
    private String name;

    public static Function<String[], Group> constructor() {
        return (String[] parameters) -> builder().code(parameters[0])
                .name(parameters[1]).build();

    }
}
