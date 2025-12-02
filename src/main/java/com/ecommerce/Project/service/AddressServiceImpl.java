package com.ecommerce.Project.service;

import com.ecommerce.Project.exceptions.ResourceNotFoundException;
import com.ecommerce.Project.model.Address;
import com.ecommerce.Project.model.User;
import com.ecommerce.Project.payload.AddressDto;
import com.ecommerce.Project.repositories.AddressRepository;
import com.ecommerce.Project.repositories.UserRepository;
import com.ecommerce.Project.util.AuthUtil;
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

    @Autowired
    UserRepository userRepository;

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

    @Override
    public List<AddressDto> getAllAddress() {
       List<Address> addressList = addressRepository.findAll();
       List<AddressDto> addressDtos = addressList.stream()
               .map(address -> modelMapper.map(address,AddressDto.class))
               .toList();
       return addressDtos;
    }

    @Override
    public AddressDto getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()->new ResourceNotFoundException("Address","addressId",addressId));
        AddressDto addressDto = modelMapper.map(address,AddressDto.class);
        return addressDto;
    }

    @Override
    public List<AddressDto> getUserAddresses(User user) {
        List<Address> addressList = user.getAddresses();
        List<AddressDto> addressDtos = addressList.stream()
                .map(address->modelMapper.map(address,AddressDto.class))
                .toList();
        return addressDtos;
    }

    @Override
    public AddressDto updateAddressById(Long addressId, AddressDto addressDto) {
        Address addressFromDatabase = addressRepository.findById(addressId)
                .orElseThrow(()->new ResourceNotFoundException("Address","addressId",addressId));

        addressFromDatabase.setStreet(addressDto.getStreet());
        addressFromDatabase.setBuildingName(addressDto.getBuildingName());
        addressFromDatabase.setCity(addressDto.getCity());
        addressFromDatabase.setState(addressDto.getState());
        addressFromDatabase.setCountry(addressDto.getCountry());
        addressFromDatabase.setPincode(addressDto.getPincode());

        Address updatedAddress = addressRepository.save(addressFromDatabase);

        User user = addressFromDatabase.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);
        userRepository.save(user);

        return modelMapper.map(updatedAddress,AddressDto.class);
    }

    @Override
    public String deleteAddressById(Long addressId) {
        Address addressFromDatabase = addressRepository.findById(addressId)
                .orElseThrow(()->new ResourceNotFoundException("Address","addressId",addressId));
        User user = addressFromDatabase.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        userRepository.save(user);
        addressRepository.delete(addressFromDatabase);
        return "Address deleted successfully with addressId :"+addressId;

    }
}
