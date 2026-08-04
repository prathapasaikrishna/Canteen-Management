package com.canteen.management.controller;

import com.canteen.management.dto.*;
import com.canteen.management.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.canteen.management.service.RazorpayService;

import java.util.List;

@RestController
@RequestMapping("/wallet")
@CrossOrigin("*")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private RazorpayService razorpayService;

    @GetMapping("/{studentId}")
    public WalletResponse getWallet(
            @PathVariable String studentId) {

        return walletService.getWallet(studentId);
    }

    @PostMapping("/add-money")
    public WalletResponse addMoney(
            @RequestBody AddMoneyRequest request) {

        return walletService.addMoney(request);
    }

    @PostMapping("/pay")
    public WalletResponse pay(
            @RequestBody WalletPaymentRequest request) {

        return walletService.pay(request);
    }

    @GetMapping("/history/{studentId}")
    public List<TransactionResponse> history(
            @PathVariable String studentId) {

        return walletService.getHistory(studentId);
    }

    @PostMapping("/create-order")
    public CreateOrderResponse createOrder(
            @RequestBody CreateOrderRequest request) throws Exception {

        return razorpayService.createOrder(request.getAmount());

    }

    @PostMapping("/verify-payment")
    public VerifyPaymentResponse verifyPayment(

            @RequestBody VerifyPaymentRequest request){

        return walletService.verifyPayment(request);

    }
}