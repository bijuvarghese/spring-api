package com.codewithmosh.store.payments;

import com.codewithmosh.store.carts.CartService;
import com.codewithmosh.store.common.ErrorDto;
import com.codewithmosh.store.carts.CartEmptyException;
import com.codewithmosh.store.carts.CartNotFoundException;
import com.codewithmosh.store.orders.OrderRepository;
import com.codewithmosh.store.users.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final OrderRepository orderRepository;
    private UserService userService;
    private CartService cartService;
    @PostMapping
    public CheckoutResponse checkout(@Valid @RequestBody CheckOutRequest request) throws PaymentException {
        return checkoutService.checkout(request);
    }

    @PostMapping("/webhook")
    public void handleWebhook(
            @RequestHeader Map<String, String> headers,
            @RequestBody String payload

    ) {
        checkoutService.handleWebhookEvent(new WebhookRequest(headers, payload));
    }

    @ExceptionHandler({CartEmptyException.class, CartNotFoundException.class, PaymentException.class})
    public ResponseEntity<ErrorDto> handleException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(exception.getMessage()));
    }

    @ExceptionHandler({PaymentException.class})
    public ResponseEntity<ErrorDto> handlePaymentException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error creating a checkout session"));
    }

}
