package com.canteen.management.controller;

import com.canteen.management.dto.ApplyCouponRequest;
import com.canteen.management.dto.ApplyCouponResponse;
import com.canteen.management.entity.Coupon;
import com.canteen.management.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coupon")
@CrossOrigin("*")
public class CouponController {

    @Autowired
    private CouponRepository couponRepository;

    @PostMapping("/apply")
    public ApplyCouponResponse applyCoupon(@RequestBody ApplyCouponRequest request) {
        Optional<Coupon> optionalCoupon = couponRepository
                .findByCodeIgnoreCaseAndActiveTrue(request.getCode());

        if (optionalCoupon.isEmpty()) {
            return new ApplyCouponResponse(false, 0.0, "Coupon Invalid or Expired");
        }

        Coupon coupon = optionalCoupon.get();

        // Check Minimum Order Amount
        if (request.getOrderAmount() < coupon.getMinOrderAmount()) {
            return new ApplyCouponResponse(false, 0.0, 
                    "Minimum order value required is ₹" + coupon.getMinOrderAmount());
        }

        // Check Expiry Date
        if (coupon.getExpiryDate() != null && !coupon.getExpiryDate().isBlank()) {
            try {
                LocalDate expiry = LocalDate.parse(coupon.getExpiryDate());
                if (LocalDate.now().isAfter(expiry)) {
                    return new ApplyCouponResponse(false, 0.0, "Coupon has expired");
                }
            } catch (Exception e) {
                // Ignore parsing errors and allow coupon
            }
        }

        double discountAmount = 0.0;
        if ("PERCENTAGE".equalsIgnoreCase(coupon.getDiscountType())) {
            discountAmount = request.getOrderAmount() * (coupon.getDiscountValue() / 100.0);
        } else {
            discountAmount = coupon.getDiscountValue();
        }

        // Discount cannot exceed order amount
        if (discountAmount > request.getOrderAmount()) {
            discountAmount = request.getOrderAmount();
        }

        // Round discount to 2 decimal places
        discountAmount = Math.round(discountAmount * 100.0) / 100.0;

        return new ApplyCouponResponse(true, discountAmount, "Coupon Applied: ₹" + discountAmount + " off");
    }

    @PostMapping("/create")
    public Coupon createCoupon(@RequestBody Coupon coupon) {
        // Force uppercase for promo code
        if (coupon.getCode() != null) {
            coupon.setCode(coupon.getCode().toUpperCase().trim());
        }
        return couponRepository.save(coupon);
    }

    @GetMapping("/active")
    public List<Coupon> getActiveCoupons() {
        return couponRepository.findByActiveTrue();
    }
}
