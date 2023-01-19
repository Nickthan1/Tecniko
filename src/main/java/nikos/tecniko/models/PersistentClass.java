package nikos.tecniko.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author legeo
 */

@Getter
@Setter
@MappedSuperclass
public class PersistentClass {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private boolean isActive=true;
}
