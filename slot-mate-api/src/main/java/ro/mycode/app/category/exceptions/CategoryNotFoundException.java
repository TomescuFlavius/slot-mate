package ro.mycode.app.category.exceptions;

import static ro.mycode.app.constants.ExceptionConstants.CATEGORY_NOT_FOUND_EXCEPTION;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super(CATEGORY_NOT_FOUND_EXCEPTION);
    }
}
