package com.parking.modelo.generic.base;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldThymeleaf {
    private String name;
    private String type = "text";
    private String size = "100";
    private String required = "false";
}
