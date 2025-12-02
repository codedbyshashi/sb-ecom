package com.ecommerce.Project.service;

import com.ecommerce.Project.model.Address;
import com.ecommerce.Project.model.User;
import com.ecommerce.Project.payload.AddressDto;
import com.ecommerce.Project.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AddressServiceImpl implements AddressService{
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public AddressDto createAddress(AddressDto addressDto, User user) {

        Address address = modelMapper.map(addressDto, Address.class);

        List<Address> addressList = user.getAddresses();

        addressList.add(address);
        user.setAddresses(addressList);

        address.setUser(user);

        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDto.class);


    }
}
