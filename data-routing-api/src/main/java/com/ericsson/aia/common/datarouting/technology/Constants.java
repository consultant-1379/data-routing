/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2016
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.aia.common.datarouting.technology;

/**
 * Defines system constants
 */
public final class Constants {

    /** Represents the DOT character. */
    public static final String DOT = ".";

    /** Represents the COLON character. */
    public static final String COLON = ":";

    /** Represents the COMMA character. */
    public static final String COMMA = ",";

    /** The maximum valid port value for the Operational System. */
    public static final int MAXIMUM_VALID_PORT_VALUE = 65536;

    /** The character that separates the value from the property in the global.properties file. */
    public static final String GLOBAL_PROPERTIES_FILE_VALUE_SEPARATOR = "=";

    /** UTF-8 encoding type. */
    public static final String UTF_8_ENCODING_TYPE = "UTF-8";

    private Constants() {
    }
}
