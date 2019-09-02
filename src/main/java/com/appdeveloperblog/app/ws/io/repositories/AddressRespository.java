package com.appdeveloperblog.app.ws.io.repositories;

import com.appdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appdeveloperblog.app.ws.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRespository extends CrudRepository<AddressEntity, Long> {
    List<AddressEntity> findAllByUserDetails(UserEntity user);
    AddressEntity findByAddressId(String addressId);
}
