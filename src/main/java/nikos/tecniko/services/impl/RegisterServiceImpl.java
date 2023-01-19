/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.services.impl;

import jakarta.inject.Inject;
import nikos.tecniko.dto.UserDto;
import nikos.tecniko.exceptions.PropertyException;
import nikos.tecniko.exceptions.PropertyOwnerException;
import nikos.tecniko.exceptions.PropertyRepairException;
import nikos.tecniko.models.Property;
import nikos.tecniko.models.PropertyRepair;
import nikos.tecniko.models.User;
import nikos.tecniko.repositories.PropertyOwnerRepository;
import nikos.tecniko.repositories.PropertyRepairRepository;
import nikos.tecniko.repositories.PropertyRepository;
import nikos.tecniko.services.RegisterService;
import nikos.tecniko.services.ValidationService;

/**
 *
 * @author legeo
 */
public class RegisterServiceImpl implements RegisterService {

    @Inject
    private PropertyRepository properties;
    @Inject
    private PropertyRepairRepository propertyRepairs;
    @Inject
    private PropertyOwnerRepository owners;
    @Inject
    private ValidationService validationService;

    @Override
    public UserDto registerPropertyOwner(UserDto ownerDto) throws PropertyOwnerException {
       // validationService.validateOwnerData(owner);
       User user = ownerDto.asUser();
       
        owners.create(user);
        return new UserDto(user);
    }

    @Override
    public Property registerProperty(Property property) throws PropertyException {
        validationService.validatePropertyData(property);
        properties.create(property);
        return property;
    }

    @Override
    public PropertyRepair registerPropertyRepair(PropertyRepair propertyRepair) throws PropertyRepairException {
        validationService.validatePropertyRepairData(propertyRepair);
        propertyRepairs.create(propertyRepair);
        return propertyRepair;
    }

}
