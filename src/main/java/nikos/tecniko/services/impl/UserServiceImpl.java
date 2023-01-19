/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.services.impl;

import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import nikos.tecniko.dto.PropertyDto;
import nikos.tecniko.dto.PropertyRepairDto;
import nikos.tecniko.dto.UserDto;
import nikos.tecniko.enums.StatusType;
import nikos.tecniko.exceptions.PropertyOwnerException;
import nikos.tecniko.models.Property;
import nikos.tecniko.models.PropertyRepair;
import nikos.tecniko.models.User;
import nikos.tecniko.repositories.PropertyOwnerRepository;
import nikos.tecniko.repositories.PropertyRepairRepository;
import nikos.tecniko.repositories.PropertyRepository;
import nikos.tecniko.services.RegisterService;
import nikos.tecniko.services.UserService;

/**
 *
 * @author legeo
 */
public class UserServiceImpl implements UserService {

    @Inject
    private PropertyRepository propertyRepo;
    @Inject
    private PropertyRepairRepository propertyRepairRepo;
    @Inject
    private PropertyOwnerRepository ownerRepo;
    @Inject
    private RegisterService registerService;

    @Override
    public UserDto createNewAccount(UserDto owner) {
        try {
            
            return (registerService.registerPropertyOwner(owner));
        } catch (PropertyOwnerException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public UserDto validateLogin(UserDto user) {
        try {
            return new UserDto(ownerRepo.validateLogin(user.getUsername(), user.getPassword()));
        } catch (PropertyOwnerException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean updateProperty(PropertyDto property, int propertyId) {
        return propertyRepo.update(property.asProperty(), propertyId);
    }

    @Override
    public boolean updatePropertyRepair(PropertyRepairDto propertyRepair, int propertyRepairId) {
        return propertyRepairRepo.update(propertyRepair.asPropertyRepair(), propertyRepairId);
    }

    @Override
    public boolean deleteProperty(int id) {
        Optional<Property> propertyOptional = propertyRepo.read(id);
        if (propertyOptional.isEmpty()) {
            return false;
        }
        deletePropertyRepairsOfProperty(id);
        return propertyRepo.delete(id);
    }

    @Override
    public boolean deletePropertyRepair(int id) {
        return propertyRepairRepo.delete(id);
    }

    @Override
    public List<PropertyRepairDto> readAlldPropertyRepairs(int userId) {
        List<Property> properties = propertyRepo.searchByOwnerId(userId);
        return propertyRepairRepo.read().stream()
                .filter(propertyRepair -> properties.stream()
                .anyMatch(property -> property.getId() == propertyRepair.getProperty().getId()))
                .map(PropertyRepairDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyRepairDto> readPendingPropertyRepairs(int userId) {
        return readPropertyRepairsWithStatus(userId, StatusType.PENDING);
    }

    private List<PropertyRepairDto> readPropertyRepairsWithStatus(int userId, StatusType status) {
        List<Property> properties = propertyRepo.searchByOwnerId(userId);
        return propertyRepairRepo.read().stream()
                .filter(propertyRepair -> properties.stream()
                .anyMatch(property -> property.getId() == propertyRepair.getProperty().getId() && propertyRepair.getStatus().equals(status)))
                .map(PropertyRepairDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteAccount(int id) {
        Optional<User> userOptional = ownerRepo.read(id);
        if (userOptional.isEmpty()) {
            return false;
        }
        //firstly delete all property repairs ordered by the user
        //then the properties owned by the user
        //and lastly delete the account
        deletePropertiesAndPropertyRepairsOfOwner(id);

        return ownerRepo.delete(id);
    }

    @Override
    public boolean safeDeleteAccount(int id) {
        Optional<User> userOptional = ownerRepo.read(id);
        if (userOptional.isEmpty()) {
            return false;
        }
        //firstly safely delete all property repairs ordered by the user
        //then the properties owned by the user
        //and lastly safely delete the account
        safeDeletePropertiesOfOwner(id);
        return ownerRepo.safeDelete(id);

    }

    @Override
    public boolean safeDeleteProperty(int id) {
        Optional<Property> propertyOptional = propertyRepo.read(id);
        if (propertyOptional.isEmpty()) {
            return false;
        }
        safeDeletePropertyRepairsOfProperty(id);
        return propertyRepo.safeDelete(id);
    }

    @Override
    public boolean safeDeletePropertyRepair(int id) {
        return propertyRepairRepo.safeDelete(id);

    }

    private void deletePropertyRepairsOfProperty(int propertyId) {
        List<PropertyRepair> propertyRepairs = getPropertyRepairsOfProperty(propertyId);
        for (PropertyRepair pr : propertyRepairs) {
            propertyRepairRepo.delete(pr.getId());
        }
    }

    private void safeDeletePropertyRepairsOfProperty(int propertyId) {
        List<PropertyRepair> propertyRepairs = getPropertyRepairsOfProperty(propertyId);
        for (PropertyRepair pr : propertyRepairs) {
            propertyRepairRepo.safeDelete(pr.getId());
        }
    }

    private void deletePropertiesAndPropertyRepairsOfOwner(int id) {
        List<Property> properties = getPropertiesOfOwner(id);
        for (Property p : properties) {
            int propertyId = p.getId();
            deletePropertyRepairsOfProperty(propertyId);
            propertyRepo.delete(propertyId);
        }
    }

    private void safeDeletePropertiesOfOwner(int ownerId) {
        List<Property> properties = getPropertiesOfOwner(ownerId);
        for (Property p : properties) {
            int propertyId = p.getId();
            safeDeletePropertyRepairsOfProperty(propertyId);
            propertyRepo.safeDelete(propertyId);
        }
    }

    private List<PropertyRepair> getPropertyRepairsOfProperty(int propertyId) {
        return propertyRepairRepo.searchByProperty(propertyId);
    }

    private List<Property> getPropertiesOfOwner(int ownerId) {
        return propertyRepo.searchByOwnerId(ownerId);
    }

}
