
package nikos.tecniko.repositories;

import java.util.Optional;
import nikos.tecniko.exceptions.PropertyOwnerException;
import nikos.tecniko.models.User;
public interface PropertyOwnerRepository extends Repository<User> {

    /**
     *  Get the owner with the given vat number
     * @param vat the vat number
     * @return optional of user
     */
    Optional<User> readOwnerByVat(String vat);

    /**
     *  Get the owner with the given email address
     * @param email the email number
     * @return optional of user
     */
    Optional<User> readOwnerByEmail(String email);

    /**
     *  Get the user with the given username
     * @param username user's username
     * @return optional of user
     * @throws nikos.tecniko.exceptions.PropertyOwnerException
     */
    Optional<User> readOwnerByUsername(String username) throws PropertyOwnerException;

    /**
     * Validate the credentials of the user
     * @param username user's username
     * @param password user's password
     * @return the user if found 
     * @throws PropertyOwnerException exception if any given info is incorrect
     */
    User validateLogin(String username, String password) throws PropertyOwnerException;
    
}
