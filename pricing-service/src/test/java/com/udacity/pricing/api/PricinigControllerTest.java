package com.udacity.pricing.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PriceException;
import com.udacity.pricing.service.PricingService;
import java.math.BigDecimal;
import org.apache.http.Consts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implements testing of the PricingController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PricinigControllerTest {

  @Autowired
  private MockMvc mvc;


  @Before
  public void setup() {
    Price price = getPrice();
  }

  @Test
  public void getPriceTest() throws Exception{

    mvc.perform(get("/services/price")
        .param("vehicleId", "1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.currency").exists())
        .andExpect(jsonPath("$.currency").value("USD"));
  }

  @Test
  public void whenPriceNotFound_ThenThrowException() throws Exception {

    mvc.perform(get("/services/price")
        .param("vehicleId", "30"))
        .andDo(print())
        .andExpect(status().isNotFound());

  }



  private Price getPrice(){
    Price price = new Price();
    price.setCurrency("USD");
    price.setPrice(new BigDecimal(3000));
    price.setVehicleId(1L);
    return price;
  }

}
