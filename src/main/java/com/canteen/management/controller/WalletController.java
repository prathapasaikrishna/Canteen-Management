package com.canteen.management.controller;

import com.canteen.management.dto.AddMoneyRequest;
import com.canteen.management.dto.TransactionResponse;
import com.canteen.management.dto.WalletPaymentRequest;
import com.canteen.management.dto.WalletResponse;
import com.canteen.management.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet")
@CrossOrigin("*")
public class WalletController {

    @Autowired
    private WalletService walletService;

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
}