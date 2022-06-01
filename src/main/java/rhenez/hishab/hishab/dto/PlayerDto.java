package rhenez.hishab.hishab.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PlayerDto {
    @NotBlank(message = "Name is required")
    String name;
    @NotNull(message = "Age parameter is required")
    @Min(1)
    Integer age;
}
