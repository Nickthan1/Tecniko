
package nikos.tecniko.services;

import java.io.FileNotFoundException;

/**
 *
 * @author legeo
 */
public interface ReportService {

    /**
     * Print all owners in a csv file
     *
     * @param filename
     * @throws FileNotFoundException
     */
    void printAllOwners(String filename) throws FileNotFoundException;

    /**
     * Print all properties in a csv file
     *
     * @param filename
     * @throws FileNotFoundException
     */
    void printAllProperties(String filename) throws FileNotFoundException;

    /**
     * Print all repairs in a csv file
     *
     * @param filename
     * @throws FileNotFoundException
     */
    void printAllRepairs(String filename) throws FileNotFoundException;

    /**
     * Print all repairs in a csv file
     *
     * @param filename
     * @throws FileNotFoundException
     */
    void printPendingRepairs(String filename) throws FileNotFoundException;

    /**
     * Print all accepted repairs in a csv file
     *
     * @param filename
     * @throws FileNotFoundException
     */
    void printAcceptedRepairs(String filename) throws FileNotFoundException;

    /**
     * Print all declined repairs in a csv file
     *
     * @param filename
     * @throws FileNotFoundException
     */
    void printDeclinedRepairs(String filename) throws FileNotFoundException;

    /**
     * Print all in progress repairs in a csv file
     *
     * @param filename
     * @throws FileNotFoundException
     */
    void printInProgressRepairs(String filename) throws FileNotFoundException;

    /**
     * Print all completed repairs in a csv file
     *
     * @param filename
     * @throws FileNotFoundException
     */
    void printCompletedRepairs(String filename) throws FileNotFoundException;

 
}
