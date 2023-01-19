
package nikos.tecniko.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nikos.tecniko.enums.UserRole;
import nikos.tecniko.models.User;

/**
 *
 * @author legeo
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDto {

    private int id;
    private boolean isActive;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private String vat;
    private UserRole role = UserRole.PROPERTY_OWNER;

    public UserDto(User user) {
        this.id = user.getId();
        this.isActive = user.isActive();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.vat = user.getVat();
        this.role = user.getRole();
    }

    public User asUser() {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setAddress(address);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setVat(vat);
        user.setRole(role);
        user.setId(id);
        user.setActive(isActive);
        return user;
    }
}
