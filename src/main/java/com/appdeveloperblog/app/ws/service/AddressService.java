package com.appdeveloperblog.app.ws.service;

import com.appdeveloperblog.app.ws.shared.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAddresses(String userId);
    AddressDTO getAddress(String addressId);
}
