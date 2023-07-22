package com.bayu.springboot3.jwt.service;

import com.bayu.springboot3.jwt.dto.AuthenticationRequest;
import com.bayu.springboot3.jwt.dto.AuthenticationResponse;
import com.bayu.springboot3.jwt.dto.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
