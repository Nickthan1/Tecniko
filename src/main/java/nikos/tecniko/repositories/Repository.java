package nikos.tecniko.repositories;


import java.util.List;
import java.util.Optional;


/**
 * Repository interface for CRUD actions
 *
 * @param <T> type of object in the repository
 */
public interface Repository<T> {

    /**
     * Insert into the database the object t
     *
     * @param t the object to be inserted
     * @return the key of type K of the object if it was successfully inserted
     */
    int create(T t);

    /**
     * Read the object inside the database specified by the id
     *
     * @param id a K-type id
     * @return the object, or null if the object was not found,
     */
    Optional<T> read(int id);

    /**
     * Read all items in the table
     *
     * @return a list of items
     */
    List<T> read();
    
    /**
     * Update the values of the object t in the database
     * 
     * @param t the object to be updated
     * @param id the id of the 
     * @return
     */
    boolean update(T t, int id);

    /**
     * Remove item with the given id from the database
     *
     * @param id
     * @return true if the item was successfully deleted, else false
     */
    boolean delete(int id);

    /**
     * Safely delete the item with the given id
     *
     * @param id
     * @return true if the item was successfully deleted, else false
     */
    boolean safeDelete(int id);
}
