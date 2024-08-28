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

package com.ericsson.aia.common.datarouting.api.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * This class is responsible for building a filter for a sink. The Filter is comprised of a collection of AcceptedRecordBuilder which determine the
 * types of records which are allowed through the filter.
 *
 * @param <V>
 *            The type of the record.
 */
public class FilterBuilder<V> {

    private final RecordInterpreter<V> recordInterpreter;
    private final Collection<AcceptedRecordBuilder<V>> acceptedRecordBuilders = new LinkedList<>();

    /**
     * Instantiates a new filter builder.
     *
     * @param recordInterpreter
     *            Object used to interpret records so that filters can be applied to multiple formats.
     */
    FilterBuilder(final RecordInterpreter<V> recordInterpreter) {
        this.recordInterpreter = recordInterpreter;
    }

    /**
     * Adds an AcceptedRecordBuilder which determines which messages will be allowed through this filter.
     *
     * @return AcceptedRecordBuilder<V>
     */
    public AcceptedRecordBuilder<V> acceptRecord() {
        final AcceptedRecordBuilder<V> acceptedRecordBuilder = new AcceptedRecordBuilder<>(this);
        acceptedRecordBuilders.add(acceptedRecordBuilder);
        return acceptedRecordBuilder;
    }

    /**
     * Build a predicate which is used to determine which records to publish.
     *
     * @return Predicate<V>
     */
    Predicate<V> build() {
        Predicate<V> filter = Predicates.alwaysTrue();
        if (!acceptedRecordBuilders.isEmpty()) {
            filter = getMultiPredicate();
        }
        return filter;
    }

    private Predicate<V> getMultiPredicate() {

        final List<Predicate<V>> acceptedRecords = new ArrayList<>();

        for (final AcceptedRecordBuilder<V> acceptedRecordBuilder : acceptedRecordBuilders) {
            final Predicate<V> acceptedRecord = acceptedRecordBuilder.build(recordInterpreter);
            if (acceptedRecord != null) {
                acceptedRecords.add(acceptedRecord);
            }
        }

        if (acceptedRecords.size() > 1) {
            return Predicates.or(acceptedRecords);
        } else {
            return acceptedRecords.get(0);
        }
    }
}
