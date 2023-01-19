package nikos.tecniko.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nikos.tecniko.enums.UserRole;


/**
 *
 * @author timos
 */

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class User extends PersistentClass{
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    @Column(unique=true, nullable = false)
    private String email;
    @Column(unique=true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique=true, nullable = false)
    private String vat;
    private UserRole role=UserRole.PROPERTY_OWNER;
}
