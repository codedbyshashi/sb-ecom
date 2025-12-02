package com.ecommerce.Project.controller;

import com.ecommerce.Project.model.User;
import com.ecommerce.Project.payload.AddressDto;
import com.ecommerce.Project.service.AddressService;
import com.ecommerce.Project.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private AddressService addressService;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDto> createAddress(@Valid @RequestBody AddressDto addressDto){

        User user = authUtil.loggedInUser();

        AddressDto savedAdressDto = addressService.createAddress(addressDto,user);

        return new ResponseEntity<AddressDto>(savedAdressDto, HttpStatus.CREATED);

    }
}
