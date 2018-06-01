package by.bsu.rfe.smsservice.common.dto;

import by.bsu.rfe.smsservice.common.enums.RecipientType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipientDTO {
    private Integer id;

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    private RecipientType recipientType;

    public RecipientDTO(
        @NotNull @Size(min = 1) String name,
        @NotNull RecipientType recipientType) {
        this.name = name;
        this.recipientType = recipientType;
    }
}
