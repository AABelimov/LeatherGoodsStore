package ru.aabelimov.leathergoodsstore.entity;

import lombok.Data;

@Data
public class PaymentResponse {

    Confirmation confirmation = new Confirmation();

    @Data
    public static class Confirmation {
        String type;
        String confirmation_url;
    }
}
