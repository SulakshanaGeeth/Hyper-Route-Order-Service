package com.hyperroute.orderservice.controller;


import com.hyperroute.orderservice.config.JacksonConfig;
import com.hyperroute.orderservice.dto.OrderRequest;
import com.hyperroute.orderservice.model.Order;
import com.hyperroute.orderservice.model.OrderStatus;
import com.hyperroute.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import(JacksonConfig.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder_ShouldReturnHttpStatusCreatedAndSavedOrder() throws Exception {
        // Arrange
        OrderRequest request = new OrderRequest("CS006", 37.7749, -122.4194);

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setCustomerId("CS006");
        savedOrder.setLatitude(37.7749);
        savedOrder.setLongitude(-122.4194);
        savedOrder.setStatus(OrderStatus.CREATED);

        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(savedOrder);

        // Act & Assert
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerId").value("CS006"))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }
}
