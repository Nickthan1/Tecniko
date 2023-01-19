/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.services.impl;

import jakarta.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import nikos.tecniko.dto.UserDto;
import nikos.tecniko.enums.PropertyType;
import nikos.tecniko.enums.RepairType;
import nikos.tecniko.enums.StatusType;
import nikos.tecniko.enums.UserRole;
import nikos.tecniko.exceptions.PropertyException;
import nikos.tecniko.exceptions.PropertyOwnerException;
import nikos.tecniko.exceptions.PropertyRepairException;
import nikos.tecniko.models.Property;
import nikos.tecniko.models.PropertyRepair;
import nikos.tecniko.models.User;
import nikos.tecniko.repositories.PropertyOwnerRepository;
import nikos.tecniko.repositories.PropertyRepository;
import nikos.tecniko.services.LoadDataService;
import nikos.tecniko.services.RegisterService;

/**
 *
 * @author legeo
 */
public class LoadDataServiceImpl implements LoadDataService {

    @Inject
    private  RegisterService registerService;
    @Inject
    private PropertyRepository propertyRepo;
    @Inject
    private PropertyOwnerRepository ownerRepo;

    @Override
    public void loadOwners(String filename) throws PropertyOwnerException {
        int rowsRead = 0;
        rowsRead++;
        List<String> lines = new ArrayList<>();

        try {
            lines = readFile(filename);
        } catch (IOException ex) {
            Logger.getLogger(LoadDataServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String line : lines) {
            try {
                String[] words = line.split(",");
                UserDto user = readUser(words);
                if (user != null) {
                    user.setPassword(String.valueOf(user.getPassword().hashCode()));
                    registerService.registerPropertyOwner(user);
                }
                rowsRead++;
            } catch (PropertyOwnerException e) {
                //print exception message
                //and the row that was skipped
                Logger.getLogger(LoadDataServiceImpl.class.getName()).log(Level.SEVERE, "While loading owners: The row: " + rowsRead + " has been dropped", e);
            } catch (Exception ex) {
                Logger.getLogger(LoadDataServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void loadPropertyRepairs(String filename) throws PropertyRepairException {
        int rowsRead = 0;
        rowsRead++;
        List<String> lines = new ArrayList<>();

        try {
            lines = readFile(filename);
        } catch (IOException ex) {
            Logger.getLogger(LoadDataServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String line : lines) {
            try {
                String[] words = line.split(",");
                PropertyRepair propertyRepair = readPropertyRepair(words);

                if (propertyRepair != null) {
                    registerService.registerPropertyRepair(propertyRepair);
                }
                rowsRead++;
            } catch (PropertyRepairException e) {
                //print exception message
                //and the row that was skipped
                Logger.getLogger(LoadDataServiceImpl.class.getName()).log(Level.SEVERE, "While loading propertyRepair: The row: " + rowsRead + " has been dropped", e);
            } catch (Exception ex) {
                Logger.getLogger(LoadDataServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void loadProperties(String filename) throws PropertyException {
        int rowsRead = 0;
        rowsRead++;
        List<String> lines = new ArrayList<>();

        try {
            lines = readFile(filename);
        } catch (IOException ex) {
            Logger.getLogger(LoadDataServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String line : lines) {
            try {
                String[] words = line.split(",");
                Property property = readProperty(words);

                if (property != null) {
                    registerService.registerProperty(property);
                }
                rowsRead++;
            } catch (PropertyException e) {
                //print exception message
                //and the row that was skipped
                Logger.getLogger(LoadDataServiceImpl.class.getName()).log(Level.SEVERE, "While loading propertyRepair: The row: " + rowsRead + " has been dropped", e);
            } catch (Exception ex) {
                Logger.getLogger(LoadDataServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<String> readFile(String filename) throws IOException {
        int rowsRead = 0;
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        //skip first line used for header
        try (Scanner scanner = new Scanner(file)) {
            //skip first line used for header
            scanner.nextLine();
            System.out.println("In read file");
            rowsRead++;
            while (scanner.hasNext()) {
                System.out.println("Reading");
                String line = scanner.nextLine();
                lines.add(line);
            }
        }
        return lines;
    }

    private Property readProperty(String[] words) throws Exception {
        Property property = new Property();
        property.setPropertyIdentificationNumber(words[0]);
        Optional<User> ownerOptional = ownerRepo.read(Integer.parseInt(words[1]));
        if (ownerOptional.isEmpty()) {
            throw new Exception();
        }
        property.setOwner(ownerOptional.get());
        property.setAddress(words[2]);
        property.setPropertyType(PropertyType.valueOf(words[3]));
        property.setYearOfConstruction(Integer.valueOf(words[4]));
        return property;
    }

    private PropertyRepair readPropertyRepair(String[] words) throws Exception {
        PropertyRepair propertyRepair = new PropertyRepair();
        Property property = propertyRepo.read(Integer.parseInt(words[0])).get();
        propertyRepair.setProperty(property);
        propertyRepair.setRepairType(RepairType.valueOf(words[1]));
        propertyRepair.setDescription(words[2].trim());
        propertyRepair.setDateOfSubmission(Instant.parse(words[3]));
        propertyRepair.setWorkToBeDone(words[4].trim());
        propertyRepair.setCost(new BigDecimal(words[5].trim()));
        propertyRepair.setOwnerAcceptance(Boolean.parseBoolean(words[6].trim()));
        propertyRepair.setStatus(StatusType.valueOf(words[7]));
        return propertyRepair;
    }

    private UserDto readUser(String[] words) throws Exception {
        UserDto user = new UserDto();
        user.setVat(words[0].trim());
        user.setName(words[1].trim());
        user.setSurname(words[2].trim());
        user.setEmail(words[3].trim());
        user.setUsername(words[4].trim());
        user.setPassword(words[5].trim());
        user.setPhoneNumber(words[6].trim());
        user.setAddress(words[7].trim());
        user.setRole(UserRole.valueOf(words[8].trim()));
        user.setActive(true);

        return user;
    }

}
