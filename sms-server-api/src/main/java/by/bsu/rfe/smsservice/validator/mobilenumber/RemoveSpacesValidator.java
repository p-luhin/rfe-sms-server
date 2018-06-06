package by.bsu.rfe.smsservice.validator.mobilenumber;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class RemoveSpacesValidator implements MobileNumberValidator {

    private static final String WHITESPACE = " ";

    @Override
    public String validate(String mobileNumber) {
        return StringUtils.remove(mobileNumber, WHITESPACE);
    }

    @Override
    public Boolean isValid(String mobileNumber) {
        return !mobileNumber.contains(WHITESPACE);
    }

    @Override
    public String errorString() {
        return "Mobile number must not contain spaces";
    }
}
