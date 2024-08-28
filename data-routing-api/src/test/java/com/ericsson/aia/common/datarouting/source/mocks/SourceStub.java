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
package com.ericsson.aia.common.datarouting.source.mocks;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

import com.ericsson.aia.common.datarouting.api.EventListener;
import com.ericsson.aia.common.datarouting.api.StreamingSource;

/**
 * Stub for mediation source.
 *
 */
public class SourceStub implements StreamingSource<String, GenericRecord> {
    private static final AtomicInteger recordNumber = new AtomicInteger(0);
    private static final AtomicInteger running = new AtomicInteger(0);

    public static int getRunning() {
        return running.intValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.aia.common.datarouting.api.Source#close()
     */
    @Override
    public void close() {
        running.decrementAndGet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.aia.common.datarouting.api.Source#collectStream()
     */
    @Override
    public Collection<GenericRecord> collectStream() {
        final String USER_SCHEMA = "{" + "\"type\":\"record\"," + "\"name\":\"myrecord\"," + "\"fields\":["
                + "  { \"name\":\"att_1\", \"type\":\"string\" }," + "  { \"name\":\"att_2\", \"type\":\"string\" },"
                + "  { \"name\":\"att_3\", \"type\":\"int\" }" + "]}";

        final Schema schema;

        final Schema.Parser parser = new Schema.Parser();
        schema = parser.parse(USER_SCHEMA);

        final GenericRecord record = new GenericData.Record(schema);
        record.put("att_1", "Hello");
        record.put("att_2", "BYE");
        record.put("att_3", recordNumber.getAndIncrement());

        return Collections.singleton(record);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.aia.common.datarouting.api.Source#registerEventListener(com.ericsson.aia.common.datarouting.api.EventListener)
     */
    @Override
    public void registerEventListener(final EventListener<GenericRecord> listener) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.aia.common.datarouting.api.Source#removeEventListener(com.ericsson.aia.common.datarouting.api.EventListener)
     */
    @Override
    public void removeEventListener(final EventListener<GenericRecord> listener) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.aia.common.datarouting.api.Source#start()
     */
    @Override
    public void start() {
        running.incrementAndGet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ericsson.aia.common.datarouting.api.Source#subscribe(java.util.List)
     */
    @Override
    public void subscribe(final List<String> topics) {
    }

}
