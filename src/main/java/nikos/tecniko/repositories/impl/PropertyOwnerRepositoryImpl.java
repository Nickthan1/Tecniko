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
import nikos.tecniko.exceptions.PropertyOwnerException;
import nikos.tecniko.exceptions.codes.PropertyOwnerExceptionCodes;
import nikos.tecniko.models.User;
import nikos.tecniko.repositories.PropertyOwnerRepository;

/**
 *
 * @author legeo
 */
public class PropertyOwnerRepositoryImpl implements PropertyOwnerRepository {

    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;

    @Override
    @Transactional
    public int create(User t) {
        entityManager.persist(t);
        return t.getId();
    }

    @Override
    @Transactional
    public Optional<User> read(int id) {
        User user = entityManager.find(User.class, id);
        return user != null && user.isActive() ? Optional.of(user) : Optional.empty();
    }

    @Override
    @Transactional
    public List<User> read() {
        return entityManager.createQuery("Select u from User u where u.isActive = true", User.class).getResultList();
    }

    @Override
    @Transactional
    public boolean safeDelete(int id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            return false;
        }
        user.setActive(false);
        entityManager.persist(user);
        return true;
    }

    @Override
    @Transactional
    public User validateLogin(String username, String password) throws PropertyOwnerException {
        Optional<User> userOptional = readOwnerByUsername(username);
        if (userOptional.isEmpty()) {
            throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_USERNAME_NOT_FOUND);
        }
        User user = userOptional.get();
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_PASSWORD_INCORRECT);
    }

    @Override
    @Transactional
    public Optional<User> readOwnerByUsername(String username) throws PropertyOwnerException {
        List<User> propertyList = entityManager.createQuery("select o from User o where o.username = :username").setParameter("username", username).getResultList();
        User user=null;
        if (!propertyList.isEmpty()) {
           user = propertyList.get(0);
        }
        return optionalOfUser(user);
    }

    @Override
    @Transactional
    public boolean update(User t, int id) {
        User oldUser = entityManager.find(User.class, id);
        if (oldUser == null || !oldUser.isActive()) {
            return false;
        }
        oldUser.setAddress(t.getAddress());
        oldUser.setPassword(t.getPassword());
        oldUser.setEmail(t.getEmail());
        updateUser(oldUser);
        return true;
    }

    /**
     * update the user
     * @param user 
     */
    private void updateUser(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            return false;
        }
        entityManager.remove(user);
        return true;

    }

    @Override
    @Transactional
    public Optional<User> readOwnerByEmail(String email) {
        List<User> userList = entityManager.createQuery("select u from User u where u.email = :email").setParameter("email", email).getResultList();
        User user = null;
        if (!userList.isEmpty()) {
            user = userList.get(0);
        }
        return optionalOfUser(user);
    }

    @Override
    @Transactional
    public Optional<User> readOwnerByVat(String vat) {
        List<User> userList = entityManager.createQuery("select u from User u where u.vat = :vat").setParameter("vat", vat).getResultList();
        User user = null;
        if (!userList.isEmpty()) {
            user = userList.get(0);
        }
        return optionalOfUser(user);
    }

    /**
     * get the optional of the user
     * @param user
     * @return 
     */
    private Optional<User> optionalOfUser(User user) {
        return user != null && user.isActive() ? Optional.of(user) : Optional.empty();
    }

}
