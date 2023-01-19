
package nikos.tecniko.repositories;



import java.util.List;
import java.util.Optional;
import nikos.tecniko.enums.PropertyType;
import nikos.tecniko.models.Property;

public interface PropertyRepository extends Repository<Property> {

    /**
     * Get the list of properties owned by the owner with the given vat
     *
     * @param vat the vat of the owner
     * @return a list of properties
     */
    List<Property> searchByOwnerVat(String vat);
    
    /**
     *  Get the list of properties owned by the owner with the given id
     * @param id
     * @return
     */
    List<Property> searchByOwnerId(int id);

    /**
     * Update the address of a given property
     *
     * @param address The new address of the given property
     * @param id the id of the property
     * @return true if property was successfully updated, else false
     */
    boolean updateAddress(String address, int id);

    /**
     * Update the year the year of construction of the given property
     *
     * @param year the new year of construction
     * @param id the id of the property
     * @return true if property was successfully updated, else false
     */
    boolean updateYearOfConstruction(int year, int id);

    /**
     * Update the type of the given property
     *
     * @param propertyType the new property type
     * @param id the id of the property
     * @return true if property was successfully updated, else false
     */
    boolean updateTypeOfProperty(PropertyType propertyType, int id);

    /**
     * Get the property with a given property identification number
     *
     * @param propertyIdentificationNumber the property identification
     * number(E9) of the property
     * @return
     */
    Optional<Property> readByPropertyIdentificationNumber(String propertyIdentificationNumber);



}
