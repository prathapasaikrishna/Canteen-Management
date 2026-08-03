package com.canteen.management.service.impl;

import com.canteen.management.dto.*;
import com.canteen.management.entity.Wallet;
import com.canteen.management.entity.WalletTransaction;
import com.canteen.management.repository.WalletRepository;
import com.canteen.management.repository.WalletTransactionRepository;
import com.canteen.management.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository transactionRepository;

    @Override
    public WalletResponse getWallet(String studentId) {

        Optional<Wallet> wallet = walletRepository.findByStudentId(studentId);

        if (wallet.isEmpty()) {
            return new WalletResponse(studentId, 0.0, "Wallet Not Found");
        }

        return new WalletResponse(
                studentId,
                wallet.get().getBalance(),
                "Success"
        );
    }

    @Override
    public WalletResponse addMoney(AddMoneyRequest request) {

        Wallet wallet = walletRepository
                .findByStudentId(request.getStudentId())
                .orElseGet(() -> {

                    Wallet w = new Wallet();
                    w.setStudentId(request.getStudentId());
                    w.setBalance(0.0);
                    return w;
                });

        wallet.setBalance(wallet.getBalance() + request.getAmount());

        walletRepository.save(wallet);

        WalletTransaction transaction = new WalletTransaction();

        transaction.setStudentId(request.getStudentId());
        transaction.setAmount(request.getAmount());
        transaction.setType("CREDIT");
        transaction.setDescription("Money Added");

        transactionRepository.save(transaction);

        return new WalletResponse(
                request.getStudentId(),
                wallet.getBalance(),
                "Money Added Successfully"
        );
    }

    @Override
    public WalletResponse pay(WalletPaymentRequest request) {

        Wallet wallet = walletRepository
                .findByStudentId(request.getStudentId())
                .orElse(null);

        if (wallet == null) {
            return new WalletResponse(
                    request.getStudentId(),
                    0.0,
                    "Wallet Not Found"
            );
        }

        if (wallet.getBalance() < request.getAmount()) {

            return new WalletResponse(
                    request.getStudentId(),
                    wallet.getBalance(),
                    "Insufficient Balance"
            );
        }

        wallet.setBalance(wallet.getBalance() - request.getAmount());

        walletRepository.save(wallet);

        WalletTransaction transaction = new WalletTransaction();

        transaction.setStudentId(request.getStudentId());
        transaction.setAmount(request.getAmount());
        transaction.setType("DEBIT");
        transaction.setDescription("Order Payment");

        transactionRepository.save(transaction);

        return new WalletResponse(
                request.getStudentId(),
                wallet.getBalance(),
                "Payment Successful"
        );
    }

    @Override
    public List<TransactionResponse> getHistory(String studentId) {

        return transactionRepository
                .findByStudentIdOrderByCreatedAtDesc(studentId)
                .stream()
                .map(t -> new TransactionResponse(
                        t.getAmount(),
                        t.getType(),
                        t.getDescription(),
                        t.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }


}