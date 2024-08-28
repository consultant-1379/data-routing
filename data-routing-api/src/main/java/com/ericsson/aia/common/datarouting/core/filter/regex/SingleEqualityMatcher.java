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

package com.ericsson.aia.common.datarouting.core.filter.regex;

import com.ericsson.aia.common.datarouting.api.filter.RecordMatcher;
import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;

/**
 * This class is used to match attributes in a message to values in the record being sent.
 *
 * @param <V>
 *            The type of the record.
 */
public class SingleEqualityMatcher<V> implements RecordMatcher<V> {

    private final RecordInterpreter<V> recordInterpreter;
    private final Object equalityExpression;
    private final String key;

    /**
     * Instantiates a Matcher that checks if a value in a record is equal to a specified value.
     *
     * @param recordInterpreter
     *            Object used to interpret a record.
     *
     * @param key
     *            The name of the attribute in the record.
     *
     * @param equalityExpression
     *            The exact value of the attribute in the record.
     */
    public SingleEqualityMatcher(final RecordInterpreter<V> recordInterpreter, final String key, final Object equalityExpression) {
        this.recordInterpreter = recordInterpreter;
        this.equalityExpression = equalityExpression;
        this.key = key;
    }

    @Override
    public boolean matches(final V record) {
        final Object recordValue = recordInterpreter.getAttributeValueFromRecord(record, key);
        return equalityExpression.equals(recordValue);
    }

}
