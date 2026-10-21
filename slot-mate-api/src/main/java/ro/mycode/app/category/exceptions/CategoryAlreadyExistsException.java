package ro.mycode.app.category.exceptions;

import static ro.mycode.app.constants.ExceptionConstants.CATEGORY_ALREADY_EXISTS_EXCEPTION;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException() {
        super(CATEGORY_ALREADY_EXISTS_EXCEPTION);
    }
}
