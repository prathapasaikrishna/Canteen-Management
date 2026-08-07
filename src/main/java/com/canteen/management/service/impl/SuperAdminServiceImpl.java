package com.canteen.management.service.impl;

import com.canteen.management.dto.SuperAdminLoginRequest;
import com.canteen.management.dto.SuperAdminLoginResponse;
import com.canteen.management.entity.SuperAdmin;
import com.canteen.management.repository.SuperAdminRepository;
import com.canteen.management.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.canteen.management.dto.SuperAdminDashboardResponse;
import com.canteen.management.repository.OrganizationRepository;
import com.canteen.management.repository.BranchRepository;
import com.canteen.management.repository.StudentRepository;
import com.canteen.management.repository.FoodRepository;
import com.canteen.management.repository.OrderRepository;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    @Autowired
    private SuperAdminRepository repository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public SuperAdminLoginResponse login(SuperAdminLoginRequest request) {

        System.out.println("Email = " + request.getEmail());
        System.out.println("Password = " + request.getPassword());

        SuperAdmin admin = repository.findByEmail(request.getEmail()).orElse(null);

        System.out.println("Request Email : " + request.getEmail());
        System.out.println("Admin : " + admin);

        if(admin == null){
            throw new RuntimeException("Admin Not Found");
        }

        System.out.println("DB Password : " + admin.getPassword());

        if (!admin.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Wrong Password");
        }

        SuperAdminLoginResponse response = new SuperAdminLoginResponse();

        response.setId(admin.getId());
        response.setName(admin.getName());
        response.setEmail(admin.getEmail());
        response.setRole(admin.getRole());
        response.setStatus(admin.getStatus());
        response.setMessage("Login Successful");

        return response;
    }

    @Override
    public SuperAdminDashboardResponse getDashboard() {

        SuperAdminDashboardResponse response =
                new SuperAdminDashboardResponse();

        response.setTotalOrganizations(
                organizationRepository.count()
        );

        response.setTotalBranches(
                branchRepository.count()
        );

        response.setTotalStudents(
                studentRepository.countByRole("STUDENT")
        );

        response.setTotalFoods(
                foodRepository.count()
        );

        response.setTotalOrders(
                orderRepository.count()
        );

        Double revenue = orderRepository.getTotalRevenue();

        if (revenue == null) {
            revenue = 0.0;
        }

        response.setTotalRevenue(revenue);

        java.time.LocalDateTime dailyStart = java.time.LocalDateTime.now().with(java.time.LocalTime.MIN);
        java.time.LocalDateTime monthlyStart = java.time.LocalDateTime.now().withDayOfMonth(1).with(java.time.LocalTime.MIN);
        java.time.LocalDateTime yearlyStart = java.time.LocalDateTime.now().withDayOfYear(1).with(java.time.LocalTime.MIN);

        response.setDailyRegistrations(studentRepository.countByCreatedAtAfterAndRole(dailyStart, "STUDENT"));
        response.setMonthlyRegistrations(studentRepository.countByCreatedAtAfterAndRole(monthlyStart, "STUDENT"));
        response.setYearlyRegistrations(studentRepository.countByCreatedAtAfterAndRole(yearlyStart, "STUDENT"));

        return response;
    }
}