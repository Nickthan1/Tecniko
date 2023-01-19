/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.services;

import nikos.tecniko.exceptions.PropertyException;
import nikos.tecniko.exceptions.PropertyOwnerException;
import nikos.tecniko.exceptions.PropertyRepairException;

/**
 *
 * @author legeo
 */
public interface LoadDataService {
    /**
     * Read property owner from CVS file
     *
     * @param filename
     * @throws nikos.tecniko.exceptions.PropertyOwnerException
     */
    void loadOwners(String filename) throws PropertyOwnerException;
    /**
     * Read properties from CVS file
     *
     * @param filename
     * @throws PropertyException
     */
    void loadProperties(String filename) throws PropertyException;
    /**
     * Read property repairs from CVS file
     *
     * @param filename
     * @throws PropertyRepairException
     */
    void loadPropertyRepairs(String filename) throws PropertyRepairException;
}
