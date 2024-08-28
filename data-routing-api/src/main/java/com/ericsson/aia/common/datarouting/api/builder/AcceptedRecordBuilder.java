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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import com.ericsson.aia.common.datarouting.api.filter.MvelRecordMatcher;
import com.ericsson.aia.common.datarouting.api.filter.RecordMatcher;
import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;
import com.ericsson.aia.common.datarouting.core.filter.PredicateFilterFactory;
import com.ericsson.aia.common.datarouting.core.filter.mvel.AllMvelChecksMatcher;
import com.ericsson.aia.common.datarouting.core.filter.mvel.MvelBooleanMatcher;
import com.ericsson.aia.common.datarouting.core.filter.name.SchemaNameEqualityMatcher;
import com.ericsson.aia.common.datarouting.core.filter.name.SchemaNameRegexMatcher;
import com.ericsson.aia.common.datarouting.core.filter.regex.RegexMatcher;
import com.ericsson.aia.common.datarouting.core.filter.regex.SingleEqualityMatcher;
import com.ericsson.aia.common.datarouting.core.filter.schema.SchemaEqualityMatcher;
import com.ericsson.aia.common.datarouting.core.filter.schema.SchemaRegexMatcher;
import com.ericsson.aia.common.sdkutil.uri.DataRoutingUtil;
import com.google.common.base.Predicate;

/**
 * This builder is used to specify one type of record which should be allowed through a filter.
 *
 * @param <V>
 *            The type of the record.
 */
public class AcceptedRecordBuilder<V> {

    private String schemaExpression;
    private final Map<String, Object> equalityExpression = new HashMap<>();
    private final Map<String, Set<Pattern>> regexExpressions = new HashMap<>();
    private final Collection<MvelRecordMatcher> functions = new ArrayList<>();
    private String nameExpression;

    private final FilterBuilder<V> parentFilter;

    /**
     * Instantiates a new accepted record builder.
     *
     * @param parentFilter
     *            FilterBuilder to which this AcceptedRecordBuilder belongs.
     */
    AcceptedRecordBuilder(final FilterBuilder<V> parentFilter) {
        this.parentFilter = parentFilter;
    }

    /**
     * Checks that a MVEL function when evaluated with the contents of a record evaluates to true.
     *
     * @param mvelFunction
     *            The function to evaluate.
     *
     * @return AcceptedRecordBuilder<V>
     */
    public AcceptedRecordBuilder<V> function(final String mvelFunction) {
        functions.add(new MvelBooleanMatcher(mvelFunction));
        return this;
    }

    /**
     * Checks that the name of a record matches the defined regular expression.
     *
     * @param eventName
     *            The name of the event to allow through.
     *
     * @return AcceptedRecordBuilder<V>
     */
    public AcceptedRecordBuilder<V> name(final String eventName) {
        nameExpression = eventName;
        return this;
    }

    /**
     * Checks that the specified message in the record matches the defined regular expression.
     *
     * @param attributeName
     *            The name of the attribute in the message.
     *
     * @param regex
     *            The regular expression to use.
     *
     * @return AcceptedRecordBuilder<V>
     */
    public AcceptedRecordBuilder<V> regex(final String attributeName, final Object regex) {
        if ((regex instanceof String) && DataRoutingUtil.isRegex(regex.toString())) {
            addRegexPattern(attributeName, regex.toString());
        } else {
            equalityExpression.put(attributeName, regex);
        }

        return this;
    }

    /**
     * Checks that the schema of records matches the defined regular expression.
     *
     * @param regex
     *            The regular expression to check.
     *
     * @return AcceptedRecordBuilder<V>
     */
    public AcceptedRecordBuilder<V> schema(final String regex) {
        this.schemaExpression = regex;
        return this;
    }

    /**
     * Adds an AcceptedRecordBuilder which determines which messages will be allowed through this filter.
     *
     * @return AcceptedRecordBuilder<V>
     */
    public AcceptedRecordBuilder<V> acceptRecord() {
        return parentFilter.acceptRecord();
    }

    /**
     * Build a predicate which is used to determine which records to publish.
     *
     * @param recordInterpreter
     *            Object used to interpret records so that filters can be applied to multiple formats.
     *
     * @return Predicate<V>
     */
    Predicate<V> build(final RecordInterpreter<V> recordInterpreter) {
        final List<RecordMatcher<V>> recordMatchers = new ArrayList<>();

        addEqualityMatcherToList(recordMatchers, recordInterpreter);
        addRegexMatcherToList(recordMatchers, recordInterpreter);
        addSchemaMatcherToList(recordMatchers, recordInterpreter);
        addFunctionMatchersToList(recordMatchers, recordInterpreter);
        addNameMatchersToList(recordMatchers, recordInterpreter);

        return PredicateFilterFactory.convertMatchersToPredicate(recordMatchers);
    }

    private void addFunctionMatchersToList(final List<RecordMatcher<V>> recordMatchers, final RecordInterpreter<V> recordInterpreter) {
        if (!functions.isEmpty()) {
            recordMatchers.add(new AllMvelChecksMatcher<>(recordInterpreter, functions));
        }
    }

    private void addNameMatchersToList(final List<RecordMatcher<V>> recordMatchers, final RecordInterpreter<V> recordInterpreter) {
        if ((nameExpression != null) && !nameExpression.isEmpty()) {
            if (DataRoutingUtil.isRegex(nameExpression)) {
                recordMatchers.add(new SchemaNameRegexMatcher<>(recordInterpreter, nameExpression));
            } else {
                recordMatchers.add(new SchemaNameEqualityMatcher<>(recordInterpreter, nameExpression));
            }
        }
    }

    private void addEqualityMatcherToList(final List<RecordMatcher<V>> recordMatchers, final RecordInterpreter<V> recordInterpreter) {
        if (!equalityExpression.isEmpty()) {
            for (final Entry<String, Object> equalityChecks : equalityExpression.entrySet()) {
                recordMatchers.add(new SingleEqualityMatcher<V>(recordInterpreter, equalityChecks.getKey(), equalityChecks.getValue()));
            }

        }
    }

    private void addRegexMatcherToList(final List<RecordMatcher<V>> recordMatchers, final RecordInterpreter<V> recordInterpreter) {
        if (!regexExpressions.isEmpty()) {
            recordMatchers.add(new RegexMatcher<>(recordInterpreter, regexExpressions));
        }
    }

    private void addSchemaMatcherToList(final List<RecordMatcher<V>> recordMatchers, final RecordInterpreter<V> recordInterpreter) {
        if ((schemaExpression != null) && !schemaExpression.isEmpty()) {
            if (DataRoutingUtil.isRegex(schemaExpression)) {
                recordMatchers.add(new SchemaRegexMatcher<>(recordInterpreter, schemaExpression));
            } else {
                recordMatchers.add(new SchemaEqualityMatcher<>(recordInterpreter, schemaExpression));
            }
        }
    }

    private void addRegexPattern(final String attributeName, final String regex) {
        Set<Pattern> patterns = regexExpressions.get(attributeName);

        if (patterns == null) {
            patterns = new HashSet<>();
            regexExpressions.put(attributeName, patterns);
        }

        patterns.add(Pattern.compile(regex));
    }

}
