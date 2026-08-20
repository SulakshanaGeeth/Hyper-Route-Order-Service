package com.hyperroute.orderservice.dto;

public record OrderRequest(String customerId, double latitude, double longitude) {

}
