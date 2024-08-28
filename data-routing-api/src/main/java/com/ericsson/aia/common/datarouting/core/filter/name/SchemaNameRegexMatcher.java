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

import java.util.regex.Pattern;

import com.ericsson.aia.common.datarouting.api.filter.RecordMatcher;
import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;

/**
 * This class is used to match the name of a record
 *
 * @param <V>
 *            The type of the record.
 */
public class SchemaNameRegexMatcher<V> implements RecordMatcher<V> {

    private final RecordInterpreter<V> recordInterpreter;
    private final Pattern regexExpressions;

    /**
     * Instantiates a Matcher that checks the name of a record
     *
     * @param recordInterpreter
     *            Object used to interpret a record.
     *
     * @param regexExpressions
     *            Regular expression to match against record name.
     */
    public SchemaNameRegexMatcher(final RecordInterpreter<V> recordInterpreter, final String regexExpressions) {
        this.recordInterpreter = recordInterpreter;
        this.regexExpressions = Pattern.compile(regexExpressions);
    }

    @Override
    public boolean matches(final V record) {
        final String recordName = recordInterpreter.getName(record);
        if (recordName != null) {
            return regexExpressions.matcher(recordName).find();
        }
        return false;
    }

}
