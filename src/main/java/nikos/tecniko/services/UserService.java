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
public interface UserService {
    /***
     * creates new account according to the json 
     * @param user
     * @return user
     */
    UserDto createNewAccount(UserDto user);
    /***
     * validates login with the according json
     * @param user
     * @return user
     */
    UserDto validateLogin(UserDto user);
    /***
     * updates the property according to the json
     * @param property
     * @param propertyId
     * @return 
     */
    boolean updateProperty(PropertyDto property,int propertyId);
    /***
     * updates the propertyRepair according to the json
     * @param propertyRepair
     * @param propertyRepairId
     * @return 
     */
    boolean updatePropertyRepair(PropertyRepairDto propertyRepair,int propertyRepairId); 
    /***
     * deletes account
     * @param id
     * @return 
     */
    boolean deleteAccount(int id);
    /***
     * safe deletes account
     * @param id
     * @return 
     */
    boolean safeDeleteAccount(int id);
    /***
     * deletes property
     * @param id
     * @return 
     */
    boolean deleteProperty(int id);
    /***
     * safe deletes account
     * @param id
     * @return 
     */
    boolean safeDeleteProperty(int id);
    /***
     * deletes PropertyRepair
     * @param id
     * @return 
     */
    boolean deletePropertyRepair(int id);
    /***
     * safe deletes PropertyRepair
     * @param id
     * @return 
     */
    boolean safeDeletePropertyRepair(int id);
    /***
     * read all propertyRepairs
     * @param userId
     * @return 
     */
    List<PropertyRepairDto> readAlldPropertyRepairs(int userId);
    /***
     * read all pending propertyRepairs
     * @param userId
     * @return 
     */
    List<PropertyRepairDto> readPendingPropertyRepairs(int userId);
}

