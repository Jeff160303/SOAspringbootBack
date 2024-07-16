package com.ventas.pagos;

import java.util.HashMap;
import java.util.Map;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody PaymentRequest paymentRequest) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("pen")
                                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName("Total de la compra")
                                                .build())
                                        .setUnitAmount(paymentRequest.getAmount())
                                        .build())
                                .setQuantity(1L)
                                .build())
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:4200/exito")
                .setCancelUrl("http://localhost:4200/carrito")
                .build();

        Session session = Session.create(params);

        Map<String, String> response = new HashMap<>();
        response.put("id", session.getId());

        return response;
    }
}