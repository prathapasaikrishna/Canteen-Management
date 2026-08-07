package com.canteen.management.service.impl;

import com.canteen.management.dto.*;
import com.canteen.management.entity.BranchAdmin;
import com.canteen.management.repository.BranchAdminRepository;
import com.canteen.management.security.JwtUtil;
import com.canteen.management.service.BranchAdminService;
import com.canteen.management.service.OrderService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.canteen.management.entity.Order;
import com.canteen.management.repository.OrderRepository;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchAdminServiceImpl implements BranchAdminService {

    @Autowired
    private BranchAdminRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;




    @PostConstruct
    public void testPassword(){
        System.out.println(
                passwordEncoder.encode("123456")
        );
    }

    @Override
    public BranchAdminResponse addBranchAdmin(BranchAdminRequest request) {

        BranchAdmin admin = new BranchAdmin();

        if(repository.findByEmail(request.getEmail()).isPresent()){

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        admin.setOrganizationId(request.getOrganizationId());
        admin.setBranchId(request.getBranchId());
        admin.setAdminName(request.getAdminName());
        admin.setEmail(request.getEmail());
        admin.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        admin.setMobile(request.getMobile());
        admin.setRole(request.getRole());
        admin.setStatus(request.getStatus());

        repository.save(admin);

        BranchAdminResponse response = new BranchAdminResponse();

        response.setId(admin.getId());
        response.setOrganizationId(admin.getOrganizationId());
        response.setBranchId(admin.getBranchId());
        response.setAdminName(admin.getAdminName());
        response.setEmail(admin.getEmail());
        response.setMobile(admin.getMobile());
        response.setRole(admin.getRole());
        response.setStatus(admin.getStatus());
        response.setMessage("Branch Admin Added Successfully");

        return response;
    }

    @Override
    public BranchAdminResponse updateBranchAdmin(Long id,
                                                 BranchAdminRequest request) {

        BranchAdmin admin = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Branch Admin Not Found"));

        admin.setOrganizationId(request.getOrganizationId());
        admin.setBranchId(request.getBranchId());
        admin.setAdminName(request.getAdminName());
        admin.setEmail(request.getEmail());
        admin.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        admin.setMobile(request.getMobile());
        admin.setRole(request.getRole());
        admin.setStatus(request.getStatus());

        repository.save(admin);

        BranchAdminResponse response = new BranchAdminResponse();

        response.setId(admin.getId());
        response.setOrganizationId(admin.getOrganizationId());
        response.setBranchId(admin.getBranchId());
        response.setAdminName(admin.getAdminName());
        response.setEmail(admin.getEmail());
        response.setMobile(admin.getMobile());
        response.setRole(admin.getRole());
        response.setStatus(admin.getStatus());
        response.setMessage("Branch Admin Updated Successfully");

        return response;
    }

    @Override
    public String deleteBranchAdmin(Long id) {

        BranchAdmin admin =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Branch Admin Not Found"));

        admin.setStatus("INACTIVE");

        repository.save(admin);

        return "Branch Admin Deleted Successfully";
    }

    @Override
    public List<BranchAdminResponse> getAllBranchAdmins() {

        List<BranchAdmin> admins = repository.findAll();

        List<BranchAdminResponse> responseList = new ArrayList<>();

        for (BranchAdmin admin : admins) {

            BranchAdminResponse response = new BranchAdminResponse();

            response.setId(admin.getId());
            response.setOrganizationId(admin.getOrganizationId());
            response.setBranchId(admin.getBranchId());
            response.setAdminName(admin.getAdminName());
            response.setEmail(admin.getEmail());
            response.setMobile(admin.getMobile());
            response.setRole(admin.getRole());
            response.setStatus(admin.getStatus());

            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public List<BranchAdminResponse> getBranchAdminsByBranch(Long branchId) {

        List<BranchAdmin> admins =
                repository.findByBranchId(branchId);

        List<BranchAdminResponse> list = new ArrayList<>();

        for (BranchAdmin admin : admins) {

            BranchAdminResponse response = new BranchAdminResponse();

            response.setId(admin.getId());
            response.setOrganizationId(admin.getOrganizationId());
            response.setBranchId(admin.getBranchId());
            response.setAdminName(admin.getAdminName());
            response.setEmail(admin.getEmail());
            response.setMobile(admin.getMobile());
            response.setRole(admin.getRole());
            response.setStatus(admin.getStatus());

            list.add(response);
        }

        return list;
    }

    @Override
    public BranchAdminLoginResponse login(
            BranchAdminLoginRequest request) {



        BranchAdmin admin = repository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Email"));

        if(admin.getStatus() == null || !admin.getStatus().equalsIgnoreCase("ACTIVE")){
            throw new RuntimeException(
                    "Account Disabled"
            );
        }

        if(!passwordEncoder.matches(
                request.getPassword(),
                admin.getPassword())){

            throw new RuntimeException("Invalid Password");
        }


        BranchAdminLoginResponse response =
                new BranchAdminLoginResponse();

        String token =
                jwtUtil.generateToken(admin.getEmail());

        response.setToken(token);

        response.setId(admin.getId());
        response.setOrganizationId(admin.getOrganizationId());
        response.setBranchId(admin.getBranchId());
        response.setAdminName(admin.getAdminName());
        response.setEmail(admin.getEmail());
        response.setRole(admin.getRole());
        response.setStatus(admin.getStatus());
        response.setMessage("Login Successful");

        return response;


    }

    @Override
    public List<OrderResponse> getTodayOrders(Long branchId) {

        List<Order> orders =
                orderRepository.findByBranchIdAndOrderDate(
                        branchId,
                        LocalDate.now().toString()
                );

        return orders.stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getOrderNumber(),
                        order.getStudentId(),
                        order.getFoodId(),
                        order.getQuantity(),
                        order.getTotalPrice(),
                        order.getOrderDate(),
                        order.getOrderStatus(),
                        order.getQrCode(),
                        order.getPaymentStatus(),
                        order.getCanteenId(),
                        "Success",
                        order.getPaymentMethod()
                ))
                .toList();
    }


    @Override
    public List<OrderResponse> getPendingOrders(Long branchId) {

        return orderRepository
                .findByBranchIdAndOrderStatus(
                        branchId,
                        "PLACED"
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getPreparingOrders(Long branchId) {

        return orderRepository
                .findByBranchIdAndOrderStatus(
                        branchId,
                        "PREPARING"
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getReadyOrders(Long branchId) {

        return orderRepository
                .findByBranchIdAndOrderStatus(
                        branchId,
                        "READY"
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getCollectedOrders(Long branchId) {

        return orderRepository
                .findByBranchIdAndOrderStatus(
                        branchId,
                        "COLLECTED"
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getCancelledOrders(Long branchId) {

        return orderRepository
                .findByBranchIdAndOrderStatus(
                        branchId,
                        "CANCELLED"
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private OrderResponse convertToResponse(Order order){

        return new OrderResponse(

                order.getId(),
                order.getOrderNumber(),
                order.getStudentId(),
                order.getFoodId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getOrderDate(),
                order.getOrderStatus(),
                order.getQrCode(),
                order.getPaymentStatus(),
                order.getCanteenId(),
                "Success",
                order.getPaymentMethod()
        );
    }

    @Override
    public OrderResponse updateOrderStatus(UpdateOrderStatusRequest request) {


        return orderService.updateOrderStatus(
                request.getOrderId(),
                request.getStatus()
        );

    }
}