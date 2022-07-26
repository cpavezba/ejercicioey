package cl.ey.desafiojava.ejerciciojavaey.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneDto {

    private Integer id;
    private Integer number;
    private Integer citycode;
    private Integer countrycode;

}
