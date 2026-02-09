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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private AddressService addressService;

    @PostMapping("/address")
    public ResponseEntity<AddressDto> createAddress(@Valid @RequestBody AddressDto addressDto){

        User user = authUtil.loggedInUser();

        AddressDto savedAdressDto = addressService.createAddress(addressDto,user);

        return new ResponseEntity<AddressDto>(savedAdressDto, HttpStatus.CREATED);

    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDto>> getAllAddress(){
//        User user = authUtil.loggedInUser();
        List<AddressDto> addressDtos = addressService.getAllAddress();
        return new ResponseEntity<>(addressDtos,HttpStatus.OK);
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long addressId){
        AddressDto addressDto = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDto,HttpStatus.OK);
    }

    @GetMapping("/user/addresses")
    public ResponseEntity<List<AddressDto>> getUserAddresses(){
        User user = authUtil.loggedInUser();
        List<AddressDto> addressDtos = addressService.getUserAddresses(user);
        return new ResponseEntity<>(addressDtos,HttpStatus.OK);
    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<AddressDto> updateAddressById(@PathVariable Long addressId,@Valid @RequestBody AddressDto addressDto){
        AddressDto updatedAddress = addressService.updateAddressById(addressId,addressDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedAddress);
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long addressId){
        addressService.deleteAddressById(addressId);
        return ResponseEntity.noContent().build();
    }
}
