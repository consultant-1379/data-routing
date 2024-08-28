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

import java.util.Map;

import org.apache.avro.generic.GenericRecord;

import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;

/**
 * This class is used to interpret GenericRecords in order to be able to apply filters to them as well as using their contents for partitioning.
 *
 */
public class AvroRecordInterpreter implements RecordInterpreter<GenericRecord> {

    @Override
    public String getInterpretableDataFormat() {
        return "avro";
    }

    @Override
    public Map<String, Object> getMvelDataAccessor(final GenericRecord record) {
        return new GenericRecordMvelWrapper(record);
    }

    @Override
    public Object getAttributeValueFromRecord(final GenericRecord record, final String attribute) {
        final Object data = record.get(attribute);
        return data != null ? data.toString() : null;
    }

    @Override
    public String getSchemaName(final GenericRecord record) {
        return record.getSchema().getFullName();
    }

    @Override
    public String getName(final GenericRecord record) {
        return record.getSchema().getName();
    }
}
