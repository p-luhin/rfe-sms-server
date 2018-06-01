package by.bsu.rfe.smsservice.util;

import static java.util.stream.Collectors.toList;

import java.util.List;
import org.dozer.Mapper;

public class DozerUtil {

    public static <SRC, DEST> List<DEST> mapList(Mapper mapper, List<SRC> sourceList, Class<DEST> destClass) {
        return sourceList
            .stream()
            .map(elem -> mapper.map(elem, destClass))
            .collect(toList());
    }

}
