package by.bsu.rfe.smsservice.validator;

/**
 * Created by pluhin on 5.2.17.
 */
public interface Validator<T> {
    T validate(T t);
    Boolean isValid(T t);
    String errorString();
}
