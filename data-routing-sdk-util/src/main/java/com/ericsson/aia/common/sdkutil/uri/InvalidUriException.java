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
package com.ericsson.aia.common.sdkutil.uri;

/**
 * This exception is thrown when a URI cannot be parsed.
 *
 */
public class InvalidUriException extends RuntimeException {

    private static final long serialVersionUID = -937086552179021886L;

    /**
     * Instantiates a new exception for when a URI cannot be parsed.
     *
     * @param exception
     *            The exception which was originally
     */
    public InvalidUriException(final Exception exception) {
        super(exception);
    }
}
