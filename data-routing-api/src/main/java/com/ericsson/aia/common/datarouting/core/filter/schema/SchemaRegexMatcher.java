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

import java.util.regex.Pattern;

import com.ericsson.aia.common.datarouting.api.filter.RecordMatcher;
import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;

/**
 * This class is used to match the schema of a record.
 *
 * @param <V>
 *            The type of the record.
 */
public class SchemaRegexMatcher<V> implements RecordMatcher<V> {

    private final Pattern expectedSchema;
    private final RecordInterpreter<V> recordInterpreter;

    /**
     * Instantiates a Matcher that checks if the schema of a record is matches the specified regular expression.
     *
     * @param recordInterpreter
     *            Object used to interpret a record.
     *
     * @param schemaRegex
     *            The regular expression to match against the schema.
     */
    public SchemaRegexMatcher(final RecordInterpreter<V> recordInterpreter, final String schemaRegex) {
        expectedSchema = Pattern.compile(schemaRegex);
        this.recordInterpreter = recordInterpreter;
    }

    @Override
    public boolean matches(final V record) {
        final String schemaName = recordInterpreter.getSchemaName(record);
        return expectedSchema.matcher(schemaName).find();
    }
}
