package com.appdeveloperblog.app.ws.service.impl;

import com.appdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appdeveloperblog.app.ws.io.entity.UserEntity;
import com.appdeveloperblog.app.ws.io.repositories.AddressRespository;
import com.appdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appdeveloperblog.app.ws.service.AddressService;
import com.appdeveloperblog.app.ws.shared.dto.AddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRespository addressRepository;

    @Override
    public List<AddressDTO> getAddresses(String userId) {
        List<AddressDTO> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null) return returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for(AddressEntity addressEntity: addresses) {
            returnValue.add(modelMapper.map(addressEntity, AddressDTO.class));
        }
        return returnValue;
    }

    @Override
    public AddressDTO getAddress(String addressId) {
        AddressDTO returnValue = null;

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        if(addressEntity != null) {
            returnValue = new ModelMapper().map(addressEntity, AddressDTO.class);
        }
        return returnValue;
    }
}
