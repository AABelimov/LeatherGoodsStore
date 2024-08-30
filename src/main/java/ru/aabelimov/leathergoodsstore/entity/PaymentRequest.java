package ru.aabelimov.leathergoodsstore.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    Amount amount = new Amount();
    boolean capture = true;
    Confirmation confirmation = new Confirmation();
    String description;

    @Data
    public static class Amount {
        BigDecimal value;
        String currency = "RUB";
    }

    @Data
    public static class Confirmation {
        String type = "redirect";
        String return_url = "http://localhost:8080"; // TODO :: returnUrl must camel case
    }

    public PaymentRequest(BigDecimal amountOfMoney, String description) {
        amount.setValue(amountOfMoney);
        this.description = description;
    }
}
