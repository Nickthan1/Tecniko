/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import nikos.tecniko.enums.PropertyType;
import nikos.tecniko.models.Property;
import nikos.tecniko.repositories.PropertyRepository;

/**
 *
 * @author legeo
 */
public class PropertyRepositoryImpl implements PropertyRepository {

    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Property> searchByOwnerId(int id) {
        return entityManager
                .createQuery("select p from Property as p join User o on p.owner.id=o.id where o.id=:id and o.isActive=true and p.isActive=true")
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Property> searchByOwnerVat(String VAT) {
        return entityManager.createQuery("select p from Property p join User o on p.owner.id=o.id where o.vat=:vat and p.isActive=true and o.isActive=true", Property.class)
                .setParameter("vat", VAT)
                .getResultList();
    }

    @Override
    @Transactional
    public boolean updateAddress(String address, int id) {
        Optional<Property> propertyOptional = read(id);
        if (propertyOptional.isEmpty()) {
            return false;
        } else {
            Property property = propertyOptional.get();
            property.setAddress(address);
            updateProperty(property);
            return true;
        }
    }

    @Override
    @Transactional
    public boolean updateYearOfConstruction(int year, int id) {
        Optional<Property> propertyOptional = read(id);
        if (propertyOptional.isEmpty()) {
            return false;
        } else {
            Property property = propertyOptional.get();
            property.setYearOfConstruction(year);
            updateProperty(property);
            return true;
        }
    }

    @Override
    @Transactional
    public boolean updateTypeOfProperty(PropertyType propertyType, int id) {
        Optional<Property> propertyOptional = read(id);
        if (propertyOptional.isEmpty()) {
            return false;
        } else {
            Property property = propertyOptional.get();
            property.setPropertyType(propertyType);
            updateProperty(property);
            return true;
        }
    }

    @Override
    @Transactional
    public int create(Property property) {
        entityManager.persist(property);
        return property.getId();
    }

    @Override
    @Transactional
    public boolean update(Property t, int id) {
        Property oldProperty = entityManager.find(Property.class, id);
        if (oldProperty == null || !oldProperty.isActive()) {
            return false;
        }
        oldProperty.setAddress(t.getAddress());
        oldProperty.setPropertyType(t.getPropertyType());
        oldProperty.setYearOfConstruction(t.getYearOfConstruction());
        updateProperty(oldProperty);
        return true;
    }

    /**
     * update the property
     *
     * @param property
     */
    private void updateProperty(Property property) {
        entityManager.persist(property);
    }

    @Override
    @Transactional
    public Optional<Property> read(int id) {
        Property property = entityManager.find(Property.class, id);
        return optionalOfProperty(property);
    }

    /**
     * Create the optional of a property object
     *
     * @param property the property
     * @return
     */
    private Optional<Property> optionalOfProperty(Property property) {
        return property != null && property.isActive() ? Optional.of(property) : Optional.empty();
    }

    @Override
    @Transactional
    public List<Property> read() {
        return entityManager.createQuery("Select p from Property p where p.isActive = true", Property.class)
                .getResultList();

    }

    @Override
    @Transactional
    public boolean delete(int id) {
        Property property = entityManager.find(Property.class, id);
        if (property == null) {
            return false;
        }
        entityManager.remove(property);
        return true;
    }

    @Override
    @Transactional
    public boolean safeDelete(int id) {
        Property property = entityManager.find(Property.class, id);
        if (property == null) {
            return false;
        }
        property.setActive(false);
        entityManager.persist(property);
        return true;
    }

    @Override
    @Transactional
    public Optional<Property> readByPropertyIdentificationNumber(String propertyIdentificationNumber) {
        List<Property> propertyList = entityManager.createQuery("select p from Property p where p.propertyIdentificationNumber = :propertyIdentificationNumber").setParameter("propertyIdentificationNumber", propertyIdentificationNumber).getResultList();
        Property property = null;
        if (propertyList != null) {
            property = propertyList.get(0);
        }
        return optionalOfProperty(property);
    }

}
