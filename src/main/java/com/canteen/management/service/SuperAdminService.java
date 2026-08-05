package com.canteen.management.service;

import com.canteen.management.dto.SuperAdminLoginRequest;
import com.canteen.management.dto.SuperAdminLoginResponse;

public interface SuperAdminService {

    SuperAdminLoginResponse login(SuperAdminLoginRequest request);

}