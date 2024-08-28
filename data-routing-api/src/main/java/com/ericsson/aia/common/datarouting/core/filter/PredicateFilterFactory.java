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
import com.google.common.base.Predicate;

/**
 * Factory used to convert a List of matchers into a predicate so it can be easily applied to a collection of records.
 *
 */
public class PredicateFilterFactory {

    private PredicateFilterFactory() {

    }

    /**
     * Convert a List of matchers into a predicate so it can be easily applied to a collection of records.
     *
     * @param recordMatchers
     *            List of all matchers that make up an accepted record.
     * @param <V>
     *            The type of the record.
     *
     * @return Predicate<V>
     */
    public static <V> Predicate<V> convertMatchersToPredicate(final List<RecordMatcher<V>> recordMatchers) {
        if (!recordMatchers.isEmpty()) {
            final RecordMatcher<V> recordMatcher = getSingleMatcher(recordMatchers);
            return new Predicate<V>() {
                @Override
                public boolean apply(final V record) {
                    return recordMatcher.matches(record);
                }
            };

        }
        return null;
    }

    private static <V> RecordMatcher<V> getSingleMatcher(final List<RecordMatcher<V>> recordMatchers) {
        if (recordMatchers.size() == 1) {
            return recordMatchers.get(0);
        } else {
            return new AllChecksMatcher<>(recordMatchers);
        }
    }
}
