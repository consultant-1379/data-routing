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

package com.ericsson.aia.common.datarouting.core.filter.schema;

import com.ericsson.aia.common.datarouting.api.filter.RecordMatcher;
import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;

/**
 * This class is used to match the schema of a record.
 *
 * @param <V>
 *            The type of the record.
 */
public class SchemaEqualityMatcher<V> implements RecordMatcher<V> {

    private final String expectedSchemaName;
    private final RecordInterpreter<V> recordInterpreter;

    /**
     * Instantiates a Matcher that checks if the schema of a record is equal to a specified value.
     *
     * @param recordInterpreter
     *            Object used to interpret a record.
     *
     * @param expectedSchemaName
     *            The exact name of the schema.
     */
    public SchemaEqualityMatcher(final RecordInterpreter<V> recordInterpreter, final String expectedSchemaName) {
        this.expectedSchemaName = expectedSchemaName;
        this.recordInterpreter = recordInterpreter;
    }

    @Override
    public boolean matches(final V record) {
        return expectedSchemaName.equals(recordInterpreter.getSchemaName(record));
    }
}
