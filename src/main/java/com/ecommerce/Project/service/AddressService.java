package com.ecommerce.Project.service;

import com.ecommerce.Project.model.User;
import com.ecommerce.Project.payload.AddressDto;

public interface AddressService {

  AddressDto createAddress(AddressDto addressDto, User user);
}
