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

package com.ericsson.aia.common.datarouting.core.filter.mvel;

import java.io.Serializable;
import java.util.Map;

import org.mvel2.MVEL;

import com.ericsson.aia.common.datarouting.api.filter.MvelRecordMatcher;

/**
 * Matcher that evaluates a MVEL function to a boolean.
 *
 */
public class MvelBooleanMatcher implements MvelRecordMatcher {

    private final Serializable stmt;

    /**
     * Instantiates a Matcher that evaluates a MVEL function to a boolean.
     *
     * @param expression
     *            The MVEL function.
     */
    public MvelBooleanMatcher(final String expression) {
        stmt = MVEL.compileExpression(expression);
    }

    @Override
    public boolean matches(final Map<String, Object> record) {
        return MVEL.executeExpression(stmt, record, Boolean.class);
    }
}
