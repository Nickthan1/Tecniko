/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.dto;

import jakarta.json.bind.annotation.JsonbDateFormat;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nikos.tecniko.enums.RepairType;
import nikos.tecniko.enums.StatusType;
import nikos.tecniko.models.Property;
import nikos.tecniko.models.PropertyRepair;

/**
 *
 * @author legeo
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PropertyRepairDto {

    private Property property;
    private int id;
    private boolean isActive;
    private RepairType repairType;
    private String description;
    private Instant dateOfSubmission;
    private String workToBeDone;
    @JsonbDateFormat("yyyy-MM-dd HH:mm")   
    private Instant proposedStartDate;
    @JsonbDateFormat("yyyy-MM-dd HH:mm")
    private Instant proposedEndDate;
    private BigDecimal cost;
    private boolean ownerAcceptance;
    private StatusType status;
    private Instant actualStartDate;
    private Instant actualEndDate;

    public PropertyRepairDto(PropertyRepair propertyRepair) {
        this.id = propertyRepair.getId();
        this.isActive = propertyRepair.isActive();
        this.property = propertyRepair.getProperty();
        this.repairType = propertyRepair.getRepairType();
        this.description = propertyRepair.getDescription();
        this.dateOfSubmission = propertyRepair.getDateOfSubmission();
        this.workToBeDone = propertyRepair.getWorkToBeDone();
        this.proposedStartDate = propertyRepair.getProposedStartDate();
        this.proposedEndDate = propertyRepair.getProposedEndDate();
        this.cost = propertyRepair.getCost();
        this.ownerAcceptance = propertyRepair.isOwnerAcceptance();
        this.status = propertyRepair.getStatus();
        this.actualStartDate = propertyRepair.getActualStartDate();
        this.actualEndDate = propertyRepair.getActualStartDate();
    }

    public PropertyRepair asPropertyRepair() {
        PropertyRepair propertyRepair = new PropertyRepair();
        propertyRepair.setId(id);
        propertyRepair.setActive(isActive);
        propertyRepair.setProperty(property);
        propertyRepair.setRepairType(repairType);
        propertyRepair.setDescription(description);
        propertyRepair.setDateOfSubmission(dateOfSubmission);
        propertyRepair.setWorkToBeDone(workToBeDone);
        propertyRepair.setProposedStartDate(proposedStartDate);
        propertyRepair.setProposedEndDate(proposedEndDate);
        propertyRepair.setCost(cost);
        propertyRepair.setOwnerAcceptance(ownerAcceptance);
        propertyRepair.setStatus(status);
        propertyRepair.setActualStartDate(actualStartDate);
        propertyRepair.setActualEndDate(actualEndDate);
        return propertyRepair;
    }
}
