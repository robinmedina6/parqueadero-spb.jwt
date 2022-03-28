package com.parking.controller.generic.generic;

import lombok.Data;

@Data
public class GenericMessage {
    private String message;
    private String Error;
    private String urlRedirect;
    private int status=1;

}
