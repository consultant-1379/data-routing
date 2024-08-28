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

package com.ericsson.aia.common.datarouting.core.filter;

import java.util.List;

import com.ericsson.aia.common.datarouting.api.filter.RecordMatcher;

/**
 * Matcher that checks if all of the input matchers evaluate to true.
 *
 * @param <V>
 *            The type of the record.
 */
public class AllChecksMatcher<V> implements RecordMatcher<V> {

    private final RecordMatcher<V>[] recordMatchers;

    /**
     *
     * Instantiates a Matcher that checks if all of the input matchers evaluate to true.
     *
     * @param recordMatchers
     *            All the matchers to check.
     */
    public AllChecksMatcher(final List<RecordMatcher<V>> recordMatchers) {
        @SuppressWarnings("unchecked")
        final RecordMatcher<V>[] recordMatchersArray = new RecordMatcher[recordMatchers.size()];
        this.recordMatchers = recordMatchers.toArray(recordMatchersArray);
    }

    @Override
    public boolean matches(final V record) {
        for (final RecordMatcher<V> recordMatcher : recordMatchers) {
            if (!recordMatcher.matches(record)) {
                return false;
            }
        }
        return true;
    }

}
