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

import java.util.Collection;
import java.util.Map;

import com.ericsson.aia.common.datarouting.api.filter.MvelRecordMatcher;
import com.ericsson.aia.common.datarouting.api.filter.RecordMatcher;
import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;

/**
 * Matcher used if multiple MVEL expressions are defined.
 *
 * @param <V>
 *            The type of the record.
 */
public class AllMvelChecksMatcher<V> implements RecordMatcher<V> {
    private final RecordInterpreter<V> recordInterpreter;
    private final MvelRecordMatcher[] recordMatchers;

    /**
     * Instantiates a Matcher that evaluates multiple MVEL expressions.
     *
     * @param recordInterpreter
     *            Object used to interpret records.
     *
     * @param recordMatchers
     *            Collection of MVEL matchers.
     */
    public AllMvelChecksMatcher(final RecordInterpreter<V> recordInterpreter, final Collection<MvelRecordMatcher> recordMatchers) {
        final MvelRecordMatcher[] recordMatchersArray = new MvelRecordMatcher[recordMatchers.size()];
        this.recordMatchers = recordMatchers.toArray(recordMatchersArray);
        this.recordInterpreter = recordInterpreter;
    }

    @Override
    public boolean matches(final V record) {
        final Map<String, Object> mvelRecord = recordInterpreter.getMvelDataAccessor(record);
        for (final MvelRecordMatcher recordMatcher : recordMatchers) {
            if (!recordMatcher.matches(mvelRecord)) {
                return false;
            }
        }
        return true;
    }

}
