package com.pichincha.automationtest.model.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Customer {
    private String name;
    private String country;
    private String city;
    private String carNumber;
    private String expirationMonth;
    private String expirationYear;
}