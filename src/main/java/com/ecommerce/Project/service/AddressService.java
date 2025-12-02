package com.ecommerce.Project.service;

import com.ecommerce.Project.model.User;
import com.ecommerce.Project.payload.AddressDto;
import jakarta.validation.Valid;

import java.util.List;

public interface AddressService {

    AddressDto createAddress(AddressDto addressDto, User user);


    List<AddressDto> getAllAddress();

    AddressDto getAddressById(Long addressId);

    List<AddressDto> getUserAddresses(User user);

    AddressDto updateAddressById(Long addressId, @Valid AddressDto addressDto);

    String deleteAddressById(Long addressId);
}
