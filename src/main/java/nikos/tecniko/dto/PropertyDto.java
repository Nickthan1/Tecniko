/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nikos.tecniko.enums.PropertyType;
import nikos.tecniko.models.Property;
import nikos.tecniko.models.User;

/**
 *
 * @author legeo
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PropertyDto {
    
    private int id;
    private boolean isActive;
    private String propertyIdentificationNumber;
    private String address;
    private Integer yearOfConstruction;
    private PropertyType propertyType;
    private User owner;

    public PropertyDto(Property property) {
        this.id = property.getId();
        this.isActive = property.isActive();
        this.propertyIdentificationNumber = property.getPropertyIdentificationNumber();
        this.address = property.getAddress();
        this.yearOfConstruction = property.getYearOfConstruction();
        this.propertyType = property.getPropertyType();
        this.owner = property.getOwner();
    }

    public Property asProperty() {
        Property property = new Property();
        property.setPropertyIdentificationNumber(propertyIdentificationNumber);
        property.setAddress(address);
        property.setYearOfConstruction(yearOfConstruction);
        property.setPropertyType(propertyType);
        property.setOwner(owner);
        property.setId(id);
        property.setActive(isActive);
        return property;
    }
}