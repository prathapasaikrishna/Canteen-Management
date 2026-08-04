package com.canteen.management.service.impl;

import com.canteen.management.dto.*;
import com.canteen.management.entity.Wallet;
import com.canteen.management.entity.WalletTransaction;
import com.canteen.management.repository.WalletRepository;
import com.canteen.management.repository.WalletTransactionRepository;
import com.canteen.management.service.WalletService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.razorpay.Utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private com.canteen.management.service.NotificationService notificationService;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

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

        // Send real-time wallet recharge push notification
        try {
            notificationService.sendPushNotification(
                    request.getStudentId(),
                    "💰 Wallet Credited",
                    "₹" + String.format("%.2f", request.getAmount()) + " has been added to your Dining Card. New Balance: ₹" + String.format("%.2f", wallet.getBalance())
            );
        } catch (Exception ex) {
            System.err.println("Failed to send wallet recharge push notification: " + ex.getMessage());
        }

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


    @Override
    public VerifyPaymentResponse verifyPayment(VerifyPaymentRequest request) {
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", request.getRazorpayOrderId());
            options.put("razorpay_payment_id", request.getRazorpayPaymentId());
            options.put("razorpay_signature", request.getRazorpaySignature());

            Utils.verifyPaymentSignature(options, razorpayKeySecret);

            if (transactionRepository.existsByRazorpayPaymentId(request.getRazorpayPaymentId())) {
                return new VerifyPaymentResponse(true, "Payment Already Processed");
            }

            WalletTransaction transaction = new WalletTransaction();
            transaction.setStudentId(request.getStudentId());
            transaction.setAmount(request.getAmount());
            transaction.setType("CREDIT");
            transaction.setDescription("Wallet Recharge");
            transaction.setRazorpayOrderId(request.getRazorpayOrderId());
            transaction.setRazorpayPaymentId(request.getRazorpayPaymentId());
            transaction.setRazorpaySignature(request.getRazorpaySignature());
            transaction.setPaymentStatus("SUCCESS");
            transactionRepository.save(transaction);

            Wallet wallet = walletRepository.findByStudentId(request.getStudentId())
                    .orElseGet(() -> {
                        Wallet w = new Wallet();
                        w.setStudentId(request.getStudentId());
                        w.setBalance(0.0);
                        return w;
                    });
            wallet.setBalance(wallet.getBalance() + request.getAmount());
            walletRepository.save(wallet);

            // Send push notification for recharge success
            try {
                notificationService.sendPushNotification(
                        request.getStudentId(),
                        "💰 Wallet Recharge Success",
                        "₹" + String.format("%.2f", request.getAmount()) + " has been recharged. New Balance: ₹" + String.format("%.2f", wallet.getBalance())
                );
            } catch (Exception ex) {
                System.err.println("Recharge push notification failed: " + ex.getMessage());
            }

            return new VerifyPaymentResponse(true, "Verified");

        } catch (Exception e) {
            return new VerifyPaymentResponse(false, "Verification Failed: " + e.getMessage());
        }
    }
}