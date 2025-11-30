package Domain.Validator;

public interface Validator<T> {
    void validate(T t) throws ValidationException;
}