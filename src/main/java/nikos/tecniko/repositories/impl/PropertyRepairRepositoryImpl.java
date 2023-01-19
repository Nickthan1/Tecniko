/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.tecniko.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import nikos.tecniko.models.Property;
import nikos.tecniko.models.PropertyRepair;
import nikos.tecniko.models.User;
import nikos.tecniko.repositories.PropertyRepairRepository;

/**
 *
 * @author legeo
 */
public class PropertyRepairRepositoryImpl implements PropertyRepairRepository {

    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<PropertyRepair> searchDate(LocalDate date) {
        Instant searchDateInstant = localDateToInstant(date);
        Instant searchDateInstantEndOfDay = getNextDayInstant(searchDateInstant);
        return entityManager.createQuery("Select pr from PropertyRepair pr where pr.dateOfSubmission BETWEEN :searchDate and :searchDateNextDay", PropertyRepair.class)
                .setParameter("searchDate", searchDateInstant)
                .setParameter("searchDateNextDay", searchDateInstantEndOfDay)
                .getResultList();
    }

    private Instant getNextDayInstant(Instant instant) {
        return instant.plus(1, ChronoUnit.DAYS).minus(1, ChronoUnit.NANOS);

    }

    private Instant localDateToInstant(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    @Override
    @Transactional
    public List<PropertyRepair> searchDateRange(LocalDate startDate, LocalDate endDate) {
        Instant startDateInstant = localDateToInstant(startDate);
        Instant endDateInstant = localDateToInstant(endDate);
        Instant endDateInstantEndOfDay = getNextDayInstant(endDateInstant);
        return entityManager.createQuery("Select pr from PropertyRepair pr where pr.dateOfSubmission Between pr.dateOfSubmission=:date1 AND pr.dateOfSubmission=:date2", PropertyRepair.class)
                .setParameter("date1", startDateInstant)
                .setParameter("date2", endDateInstantEndOfDay)
                .getResultList();
    }

    @Override
    @Transactional
    public List<PropertyRepair> searchOwnerId(int ownerId) {
        return entityManager.createQuery("Select pr from PropertyRepair pr join Property p on p.id=pr.property.id join User o on o.id=p.owner.id Where o.id =:ownerId ", PropertyRepair.class)
                .setParameter("ownerId", ownerId)
                .getResultList();
    }

    @Override
    @Transactional
    public int create(PropertyRepair t) {
        entityManager.persist(t);
        return t.getProperty().getId();
    }

    @Override
    @Transactional
    public Optional<PropertyRepair> read(int id) {
        PropertyRepair propertyRepair = entityManager.find(PropertyRepair.class, id);
        return propertyRepair != null && propertyRepair.isActive() ? Optional.of(propertyRepair) : Optional.empty();
    }

    @Override
    @Transactional
    public List<PropertyRepair> read() {
        return entityManager.createQuery("Select pr from PropertyRepair pr where pr.isActive = true", PropertyRepair.class).getResultList();
    }

    @Override
    @Transactional
    public boolean safeDelete(int id) {
        PropertyRepair propertyRepair = entityManager.find(PropertyRepair.class, id);
        if (propertyRepair == null) {
            return false;
        }
        propertyRepair.setActive(false);
        entityManager.persist(propertyRepair);
        return true;
    }

    @Override
    @Transactional
    public boolean update(PropertyRepair t, int id) {
        PropertyRepair oldPropertyRepair = entityManager.find(PropertyRepair.class, id);
        if (oldPropertyRepair == null || !oldPropertyRepair.isActive()) {
            return false;
        }
        oldPropertyRepair.setRepairType(t.getRepairType());
        oldPropertyRepair.setDescription(t.getDescription());
        oldPropertyRepair.setWorkToBeDone(t.getWorkToBeDone());
        oldPropertyRepair.setProposedStartDate(t.getProposedStartDate());
        oldPropertyRepair.setProposedEndDate(t.getProposedEndDate());
        oldPropertyRepair.setCost(t.getCost());
        oldPropertyRepair.setStatus(t.getStatus());
        oldPropertyRepair.setOwnerAcceptance(t.isOwnerAcceptance());
        oldPropertyRepair.setActualStartDate(t.getActualStartDate());
        oldPropertyRepair.setActualEndDate(t.getActualEndDate());
        updateProperty(oldPropertyRepair);
        return true;
    }

    private void updateProperty(PropertyRepair propertyRepair) {
        entityManager.persist(propertyRepair);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        PropertyRepair propertyRepair = entityManager.find(PropertyRepair.class, id);
        Property property = entityManager.find(Property.class, propertyRepair.getProperty().getId());
        User propertyOwner = entityManager.find(User.class, propertyRepair.getProperty().getOwner().getId());
        if (property == null || propertyOwner == null) {
            return false;
        }
        entityManager.remove(propertyRepair);
        return true;

    }

    @Override
    @Transactional
    public List<PropertyRepair> searchOwnerVat(String vat) {
        return entityManager.createQuery("SELECT pr from PropertyRepair pr join property p on pr.property.id=p.id join Owner o on p.owner.id=o.id where o.vat=:vat ", PropertyRepair.class)
                .setParameter("vat", vat)
                .getResultList();
    }

    @Override
    @Transactional
    public List<PropertyRepair> searchByProperty(int propertyId) {
        return entityManager.createQuery("SELECT pr FROM PropertyRepair pr JOIN Property p ON pr.property.id = p.id WHERE p.id = :propertyId", PropertyRepair.class)
                .setParameter("propertyId", propertyId)
                .getResultList();
    }

    @Override
    @Transactional
    public List<PropertyRepair> searchByPropertyE9(String e9) {
        return entityManager.createQuery("SELECT pr from PropertyRepair pr join Property p on p.id=pr.property.id where p.propertyIdentificationNumber=:e9 ", PropertyRepair.class)
                .setParameter("e9", e9)
                .getResultList();
    }
}

