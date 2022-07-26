package cl.ey.desafiojava.ejerciciojavaey.model;

import cl.ey.desafiojava.ejerciciojavaey.entity.Phone;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private Set<PhoneDto> phones;
    private Date created;
    private Date modified;
    private Date lastLogin;
    private String token;
    private Boolean isActive;
}
