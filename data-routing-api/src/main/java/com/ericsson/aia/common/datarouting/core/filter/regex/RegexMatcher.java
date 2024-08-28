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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import com.ericsson.aia.common.datarouting.api.filter.RecordMatcher;
import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;

/**
 * This class is used to match attributes in a message to values in the record being sent.
 *
 * @param <V>
 *            The type of the record.
 */
public class RegexMatcher<V> implements RecordMatcher<V> {

    private final RecordInterpreter<V> recordInterpreter;
    private final Map<String, Set<Pattern>> regexExpressions;

    /**
     * Instantiates a Matcher that check
     *
     * @param recordInterpreter
     *            Object used to interpret a record.
     *
     * @param regexExpressions
     *            The map of regular expression to match against a attributes in the record.
     */
    public RegexMatcher(final RecordInterpreter<V> recordInterpreter, final Map<String, Set<Pattern>> regexExpressions) {
        this.recordInterpreter = recordInterpreter;
        this.regexExpressions = regexExpressions;
    }

    @Override
    public boolean matches(final V record) {
        for (final Entry<String, Set<Pattern>> equalityChecks : regexExpressions.entrySet()) {
            final Object recordValue = recordInterpreter.getAttributeValueFromRecord(record, equalityChecks.getKey());
            if (recordValue != null) {
                for (final Pattern pattern : equalityChecks.getValue()) {
                    if (!pattern.matcher(recordValue.toString()).find()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
