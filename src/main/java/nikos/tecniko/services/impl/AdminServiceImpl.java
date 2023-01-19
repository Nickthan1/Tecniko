/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.services.impl;

import jakarta.inject.Inject;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import nikos.tecniko.dto.PropertyDto;
import nikos.tecniko.dto.PropertyRepairDto;
import nikos.tecniko.dto.UserDto;
import nikos.tecniko.enums.StatusType;
import nikos.tecniko.models.PropertyRepair;
import nikos.tecniko.repositories.PropertyOwnerRepository;
import nikos.tecniko.repositories.PropertyRepairRepository;
import nikos.tecniko.repositories.PropertyRepository;
import nikos.tecniko.services.AdminService;

/**
 *
 * @author legeo
 */
public class AdminServiceImpl implements AdminService {

    @Inject
    private PropertyOwnerRepository propertyOwnerRepo;
    @Inject
    private PropertyRepository propertyRepo;
    @Inject
    private PropertyRepairRepository propertyRepairRepo;

    @Override
    public List<PropertyDto> readProperties() {
        return propertyRepo.read()
                .stream()
                .map(property -> new PropertyDto(property))
                .toList();
    }

    @Override
    public List<UserDto> readOwners() {
        return propertyOwnerRepo.read()
                .stream()
                .map(user -> new UserDto(user))
                .toList();
    }

    @Override
    public List<PropertyRepairDto> readAllPropertyRepairs() {
        return propertyRepairRepo.read()
                .stream()
                .map(propertyRepair -> new PropertyRepairDto(propertyRepair))
                .toList();
    }

    @Override
    public void proposedRepair(PropertyRepairDto propertyRepair, int id) {
        propertyRepairRepo.update(propertyRepair.asPropertyRepair(), id);
    }

    @Override
    public boolean checkStart(int id) {
        int propertyRepair = id;
        Optional<PropertyRepair> repairOptional = propertyRepairRepo.read(propertyRepair);
        if (repairOptional.isEmpty()) {
            return false;
        }
        PropertyRepair repair = repairOptional.get();
        if (repair.getProposedStartDate().isBefore(Instant.now())) {
            repair.setActualStartDate(Instant.now());
            repair.setStatus(StatusType.IN_PROGRESS);
            propertyRepairRepo.update(repair, id);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean checkEnd(int id) {
        int propertyRepair = id;
        Optional<PropertyRepair> repairOptional = propertyRepairRepo.read(propertyRepair);
        if (repairOptional.isEmpty()) {
            return false;
        }
        PropertyRepair repair = repairOptional.get();
        System.out.println(repair.getProposedStartDate() + "/" + Instant.now());
        if (repair.getProposedEndDate().isBefore(Instant.now())) {
            repair.setActualEndDate(Instant.now());
            repair.setStatus(StatusType.COMPLETE);
            propertyRepairRepo.update(repair, id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<PropertyRepairDto> readAcceptedPropertyRepairs() {
        return readAllPropertyRepairs().stream()
                .filter(propertyRepair -> propertyRepair.getStatus().equals(StatusType.ACCEPTED))
                .toList();
    }

    @Override
    public List<PropertyRepairDto> readDeclinedPropertyRepairs() {
        return readAllPropertyRepairs().stream()
                .filter(propertyRepair -> propertyRepair.getStatus().equals(StatusType.DECLINED))
                .toList();
    }

    @Override
    public List<PropertyRepairDto> readPendingPropertyRepairs() {
        return readAllPropertyRepairs().stream()
                .filter(propertyRepair -> propertyRepair.getStatus().equals(StatusType.PENDING))
                .toList();
    }

    @Override
    public List<PropertyRepairDto> readInProgressPropertyRepairs() {
        return readAllPropertyRepairs().stream()
                .filter(propertyRepair -> propertyRepair.getStatus().equals(StatusType.IN_PROGRESS))
                .toList();
    }

    @Override
    public List<PropertyRepairDto> readCompletedPropertyRepairs() {
        return readAllPropertyRepairs().stream()
                .filter(propertyRepair -> propertyRepair.getStatus().equals(StatusType.COMPLETE))
                .toList();
    }

    @Override
    public UserDto searchOwnerByVat(String vat) {
        return new UserDto(propertyOwnerRepo.readOwnerByVat(vat).get());
    }

    @Override
    public UserDto searchOwnerByEmail(String email) {
        return new UserDto(propertyOwnerRepo.readOwnerByEmail(email).get());
    }

    @Override
    public PropertyDto searchPropertyByPropertyIdentificationNumber(String propertyIdentificationNumber) {
        return new PropertyDto(propertyRepo.readByPropertyIdentificationNumber(propertyIdentificationNumber).get());
    }

    @Override
    public List<PropertyDto> searchPropertyByOwnerVat(String vat) {
        return propertyRepo.searchByOwnerVat(vat)
                .stream()
                .map(property -> new PropertyDto(property))
                .toList();
    }
}
