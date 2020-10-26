package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.repository.PriceRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * Creates a Spring Boot Application to run the Pricing Service.
 */
@SpringBootApplication
@EnableEurekaClient
public class PricingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);
    }

    @Bean
    ApplicationRunner init(PriceRepository repository) {
        return args -> LongStream
            .range(1, 20).forEach(i -> repository.save(new Price("USD", randomPrice(), i)));
    }

    private static BigDecimal randomPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
            .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }

}
