package com.kofi.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request){
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest request){
        customerService.updateCustomer(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(){
        return ResponseEntity.ok(customerService.findAllCustomer());
    }
    @GetMapping("/exist/{customerId}")
    public ResponseEntity<Boolean> existById(@PathVariable("customer-id") String customerId){
        return ResponseEntity.ok(customerService.existsById(customerId));
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable("customer-id") String customerId){
        return ResponseEntity.ok(customerService.findById(customerId));
    }
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable("customer-id") String customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }



}
