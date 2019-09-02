package com.appdeveloperblog.app.ws.ui.controller;

import com.appdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appdeveloperblog.app.ws.service.AddressService;
import com.appdeveloperblog.app.ws.service.UserService;
import com.appdeveloperblog.app.ws.shared.dto.AddressDTO;
import com.appdeveloperblog.app.ws.shared.dto.UserDto;
import com.appdeveloperblog.app.ws.ui.model.request.UserDetailRequestModel;
import com.appdeveloperblog.app.ws.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users") //http://localhost:8080/mobile-app-ws/users
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressService addressesService;

    @GetMapping(path = "/{id}",
    produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public UserRest createUser(@RequestBody UserDetailRequestModel userDetails) {

        if (userDetails.getFirstName().isEmpty())
            throw new NullPointerException("The required field is null");

//        UserDto userDto = new UserDto();
//        BeanUtils.copyProperties(userDetails, userDto);

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createUser = userService.createUser(userDto);
        UserRest returnValue = modelMapper.map(createUser, UserRest.class);
        return returnValue;
    }

    @PutMapping(path = "/{id}",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
    )
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailRequestModel userDetails) {

        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto updateUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updateUser, returnValue);
        return returnValue;
    }

    @DeleteMapping(path = "/{id}",
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
    )
    public OperationStatusModel deleteUser(@PathVariable String id) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @GetMapping(
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
    )
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "limit", defaultValue = "50") int limit) {
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page, limit);
        for(UserDto userDto: users) {
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto, userModel);
            returnValue.add(userModel);
        }
        return returnValue;
    }

    @GetMapping(path = "/{id}/addresses",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<AddressesRest> getUserAddresses(@PathVariable String id) {
        List<AddressesRest> returnValue = new ArrayList<>();
        List<AddressDTO> addressesDTOS = addressesService.getAddresses(id);
        if(addressesDTOS != null && !addressesDTOS.isEmpty()) {
            Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
            returnValue = new ModelMapper().map(addressesDTOS, listType);
        }
        return returnValue;
    }

    @GetMapping(path = "/{id}/addresses/{addressId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public AddressesRest getUserAddress(@PathVariable String addressId) {
        AddressDTO addressDTO = addressService.getAddress(addressId);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(addressDTO, AddressesRest.class);
    }


}
