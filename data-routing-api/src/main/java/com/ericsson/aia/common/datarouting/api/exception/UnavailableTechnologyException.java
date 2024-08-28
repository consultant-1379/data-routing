/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2017
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.aia.common.datarouting.api.exception;

/**
 * This exception is thrown when the technology specified by the URI cannot be found.
 *
 */
public class UnavailableTechnologyException extends RuntimeException {

    private static final long serialVersionUID = -937086552179021886L;

    /**
     * Instantiates a new exception for when the technology specified by the URI cannot be found.
     *
     */
    public UnavailableTechnologyException() {
        super("Technology specified in the URI is not available");
    }

    /**
     * Instantiates a new exception for when the technology specified by the URI cannot be found.
     *
     * @param message
     *            The reason why the technology cannot be loaded.
     */
    public UnavailableTechnologyException(final String message) {
        super(message);
    }
}
