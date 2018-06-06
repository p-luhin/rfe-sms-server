package by.bsu.rfe.smsservice.common.dto.page;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by pluhin on 11/17/16.
 */
@Getter
@AllArgsConstructor
public class PageResponseDTO<T> {

    private List<T> items;
    private Long count;
}
