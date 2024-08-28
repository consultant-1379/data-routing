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

package com.ericsson.aia.common.datarouting.core.filter.name;

import com.ericsson.aia.common.datarouting.api.filter.RecordMatcher;
import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;

/**
 * This class is used to match attributes in a message to values in the record being sent.
 *
 * @param <V>
 *            The type of the record.
 */
public class SchemaNameEqualityMatcher<V> implements RecordMatcher<V> {

    private final RecordInterpreter<V> recordInterpreter;
    private final String equalityExpression;

    /**
     * Instantiates a Matcher that checks if a value in a record is equal to a specified value.
     *
     * @param recordInterpreter
     *            Object used to interpret a record.
     *
     * @param equalityExpression
     *            The exact value of the attribute in the record.
     */
    public SchemaNameEqualityMatcher(final RecordInterpreter<V> recordInterpreter, final String equalityExpression) {
        this.recordInterpreter = recordInterpreter;
        this.equalityExpression = equalityExpression;
    }

    @Override
    public boolean matches(final V record) {
        return equalityExpression.equals(recordInterpreter.getName(record));
    }

}
