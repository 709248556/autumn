package com.autumn.util.excel;

/**
 * Excel产生的异常
 */
public class ExcelException extends RuntimeException {

    private static final long serialVersionUID = 8522477423715068109L;

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Exception innerException) {
        super(message, innerException);
    }
}
