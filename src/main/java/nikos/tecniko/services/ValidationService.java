/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.services;

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
public interface ValidationService {

    /**
     * Validate the property date
     * @param property
     * @throws PropertyException
     */
    void validatePropertyData(Property property) throws PropertyException;

    /**
     * validate the owner data
     * @param owner
     * @throws PropertyOwnerException
     */
    void validateOwnerData(User owner) throws PropertyOwnerException;

    /**
     * validate the repair data
     * @param propertyRepair
     * @throws PropertyRepairException
     */
    void validatePropertyRepairData(PropertyRepair propertyRepair) throws PropertyRepairException;
    
}
