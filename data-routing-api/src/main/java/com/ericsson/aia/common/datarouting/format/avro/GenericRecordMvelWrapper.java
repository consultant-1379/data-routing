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

package com.ericsson.aia.common.datarouting.format.avro;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.avro.generic.GenericRecord;

/**
 *
 * This class is used as a wrapper for AVRO GenericRecords so it can be used to resolve variables.
 *
 */
public class GenericRecordMvelWrapper implements Map<String, Object> {

    private final GenericRecord record;

    /**
     *
     * @param record
     *            The record to be wrapped.
     */
    public GenericRecordMvelWrapper(final GenericRecord record) {
        this.record = record;
    }

    @Override
    public int size() {
        return record.getSchema().getFields().size();
    }

    @Override
    public boolean isEmpty() {
        return record.getSchema().getFields().isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        final boolean con = record.getSchema().getField((String) key) != null;
        return con;
    }

    @Override
    public boolean containsValue(final Object value) {
        return false;
    }

    @Override
    public Object get(final Object key) {
        final Object val = record.get((String) key);
        return val;
    }

    @Override
    public Object put(final String key, final Object value) {
        record.put(key, value);
        return null;
    }

    @Override
    public Object remove(final Object key) {
        return key;
    }

    @Override
    public void putAll(final Map<? extends String, ? extends Object> map) {
    }

    @Override
    public void clear() {
    }

    @Override
    public Set<String> keySet() {
        return Collections.<String>emptySet();
    }

    @Override
    public Collection<Object> values() {
        return Collections.<Object>emptySet();
    }

    @Override
    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return Collections.<java.util.Map.Entry<String, Object>>emptySet();
    }

}
