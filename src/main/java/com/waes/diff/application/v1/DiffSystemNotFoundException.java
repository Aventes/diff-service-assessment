package com.waes.diff.application.v1;

/**
 * Defines a common Exception to be thrown within Application, when entities cannot be found within DB Repository
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
public class DiffSystemNotFoundException extends RuntimeException {

    public DiffSystemNotFoundException(final String message) {
        super(message);
    }

    public DiffSystemNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DiffSystemNotFoundException(final Throwable cause) {
        super(cause);
    }

    protected DiffSystemNotFoundException(final String message, final Throwable cause,
                                          final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
