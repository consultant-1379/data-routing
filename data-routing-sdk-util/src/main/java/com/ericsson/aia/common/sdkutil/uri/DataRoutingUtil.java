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

import java.util.regex.Pattern;

/**
 * Class contains general util methods.
 *
 */
public class DataRoutingUtil {

    private static final Pattern regularExpression = Pattern.compile("\\\\|\\*|\\?|\\[|\\]|\\|");

    private DataRoutingUtil() {

    }

    /**
     * Method used to determine if an expression is a regular expression.
     *
     * @param expression
     *            The expression evaluate.
     *
     * @return True if the expression is a regular expression.
     */
    public static final boolean isRegex(final String expression) {
        return regularExpression.matcher(expression).find();
    }
}
