package nikos.tecniko.models;

/**
 *
 * @author legeo
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nikos.tecniko.enums.PropertyType;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Property extends PersistentClass{
    @Column(unique=true)
    private String propertyIdentificationNumber;
    private String address;
    private Integer yearOfConstruction;
    private PropertyType propertyType;
    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;
    
}