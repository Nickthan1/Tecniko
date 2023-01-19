/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.services;

import nikos.tecniko.dto.UserDto;
import nikos.tecniko.exceptions.PropertyException;
import nikos.tecniko.exceptions.PropertyOwnerException;
import nikos.tecniko.exceptions.PropertyRepairException;
import nikos.tecniko.models.Property;
import nikos.tecniko.models.PropertyRepair;
import nikos.tecniko.models.User;

/**
 *
 * @author legeo
 */
public interface RegisterService {

    /**
     * Register property owner to CVS file
     *
     * @param owner
     * @return 
     * @throws nikos.tecniko.exceptions.PropertyOwnerException
     */
     UserDto registerPropertyOwner(UserDto ownerDto) throws PropertyOwnerException ;

    /**
     * Register properties to CVS file
     *
     * @param property
     * @return 
     * @throws nikos.tecniko.exceptions.PropertyException
     */
    Property registerProperty(Property property) throws PropertyException;

    /**
     * Register property repairs to CVS file
     *
     * @param repair
     * @return 
     * @throws nikos.tecniko.exceptions.PropertyRepairException
     */
    PropertyRepair registerPropertyRepair(PropertyRepair repair) throws PropertyRepairException;

}
