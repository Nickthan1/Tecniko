/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.services;

import java.util.List;
import nikos.tecniko.dto.PropertyDto;
import nikos.tecniko.dto.PropertyRepairDto;
import nikos.tecniko.dto.UserDto;

/**
 *
 * @author legeo
 */
public interface AdminService {

    /**
     * Read all properties
     * @return
     */
    List<PropertyDto> readProperties();

    /**
     * Read all owners
     * @return
     */
    List<UserDto> readOwners();

    /**
     *  Propose dates and costs for a repair
     * @param propertyRepair
     * @param id
     */
    void proposedRepair(PropertyRepairDto propertyRepair,int id); 

    /**
     * Check the starting of the repair
     * @param id
     * @return
     */
    boolean checkStart(int id); //check later

    /**
     * Check the end of the repair
     * @param id
     * @return
     */
    boolean checkEnd(int id); //check later
    /**
     * Read all property repairs
     * @return the repair info and some info about the property and the owner
     */
    List<PropertyRepairDto> readAllPropertyRepairs();
    /**
     * Read all accepted property repairs
     * @return the repair info and some info about the property and the owner
     */
    List<PropertyRepairDto> readAcceptedPropertyRepairs();
    /**
     * Read all declined property repairs
     * @return the repair info and some info about the property and the owner
     */
    List<PropertyRepairDto> readDeclinedPropertyRepairs();
    /**
     * Read all pending property repairs
     * @return the repair info and some info about the property and the owner
     */
    List<PropertyRepairDto> readPendingPropertyRepairs();
    /**
     * Read all property repairs in progress
     * @return the repair info and some info about the property and the owner
     */
    List<PropertyRepairDto> readInProgressPropertyRepairs();
    /**
     * Read all completed property repairs
     * @return the repair info and some info about the property and the owner
     */
    List<PropertyRepairDto> readCompletedPropertyRepairs();
    
    /**
     *  Search owner by vat number
     * @param vat
     * @return
     */
    UserDto searchOwnerByVat(String vat);

    /**
     * Search owner by email
     * @param email
     * @return
     */
    UserDto searchOwnerByEmail(String email);
    
    /**
     * Search Property by E9
     * @param propertyIdentificationNumber
     * @return
     */
   PropertyDto searchPropertyByPropertyIdentificationNumber(String propertyIdentificationNumber);

    /**
     * Get all properties of user by user's vat number
     * @param Vat
     * @return
     */
    List<PropertyDto> searchPropertyByOwnerVat(String Vat);
    
    
    
}
