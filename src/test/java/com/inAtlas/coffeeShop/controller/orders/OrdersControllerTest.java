package com.inAtlas.coffeeShop.controller.orders;


import com.inAtlas.coffeeShop.controller.AbstractRestControllerTest;
import com.inAtlas.coffeeShop.repository.entity.StatusOrderEnum;
import com.inAtlas.coffeeShop.service.order.OrderRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {OrdersController.class})
public class OrdersControllerTest extends AbstractRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private OrderRequestService orderRequestService;

    @BeforeEach
    void setUp() {
        // set required methods
        orderRequestDto = setUpOrderDto();
    }

    @Test
    public void testGetAllOrders() throws Exception {
        orderRequestDto = setUpOrderWithItemsDto();
        when(orderRequestService.getAll()).thenReturn(Arrays.asList(orderRequestDto));
        mockMvc.perform(get(OrdersController.ORDER_CONTROLER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
        // TODO: add more fields
    }

    @Test
    public void testGetOrderById() throws Exception {
        orderRequestDto = setUpOrderWithItemsDto();
        when(orderRequestService.getById(1)).thenReturn(orderRequestDto);
        mockMvc.perform(get(OrdersController.ORDER_CONTROLER_BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));
        // TODO: add more fields
    }

    @Test
    void testOpenOrder() throws Exception {
        when(orderRequestService.openOrder()).thenReturn(orderRequestDto);
        mockMvc.perform(post(OrdersController.ORDER_CONTROLER_BASE_URL + "/open")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    void testAddProduct() throws Exception {
        orderRequestDto = setUpOrderWithItemsDto();
        when(orderRequestService.addProduct(1, 1)).thenReturn(orderRequestDto);
        mockMvc.perform(put(OrdersController.ORDER_CONTROLER_BASE_URL+"/1/add/1").
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItems", hasSize(1)))
                .andExpect(jsonPath("$.orderItems[0].product.id", equalTo(1)))
                .andExpect(jsonPath("$.status", is(StatusOrderEnum.OPEN.toString())));
        //TODO: add moer fields
    }

    @Test
    void testRemoveProduct() throws Exception {
        when(orderRequestService.removeProduct(1, 1)).thenReturn(orderRequestDto);
        mockMvc.perform(put(OrdersController.ORDER_CONTROLER_BASE_URL+"/1/remove/1").
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItems", hasSize(0)))
                .andExpect(jsonPath("$.status", is(StatusOrderEnum.OPEN.toString())));
        //TODO: add more fields
    }


    @Test
    void testPrintOrder()  throws Exception {
        printReceiptDto = setUpPrintReceiptDto();
        when(orderRequestService.printOrder( 1)).thenReturn(printReceiptDto);
        mockMvc.perform(get(OrdersController.ORDER_CONTROLER_BASE_URL+"/1/receipt").
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.receiptItems", hasSize(1)))
                .andExpect(jsonPath("$.total", equalTo(12.0)));

    }

    @Test
    public void testDelete() throws Exception{
        orderRequestDto = setUpOrderWithItemsDto();
        orderRequestDto.setStatus(StatusOrderEnum.DELETE.toString());

        when(orderRequestService.delete(1)).thenReturn(orderRequestDto);
        mockMvc.perform(put(OrdersController.ORDER_CONTROLER_BASE_URL+"/1/delete").
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(StatusOrderEnum.DELETE.toString())));
    }


}
