package com.canteen.management.service;

import com.canteen.management.dto.*;
import com.canteen.management.entity.Wallet;

import java.util.List;

public interface WalletService {

    WalletResponse getWallet(String studentId);

    WalletResponse addMoney(AddMoneyRequest request);

    WalletResponse pay(WalletPaymentRequest request);

    List<TransactionResponse> getHistory(String studentId);



}