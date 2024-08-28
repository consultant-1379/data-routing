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
package com.ericsson.aia.common.datarouting.sink.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.avro.generic.GenericRecord;

import com.ericsson.aia.common.datarouting.api.StreamingSink;

/**
 * Stub for mediation publisher.
 *
 */
public class SinkStub implements StreamingSink<String, GenericRecord> {

    public static Map<String, List<GenericRecord>> sentRecords = new ConcurrentHashMap<>();

    public static List<GenericRecord> getRecordsSentToTopic(final String topicName) {
        return sentRecords.get(topicName);
    }

    public static void reset() {
        sentRecords.clear();
    }

    @Override
    public void close() {

    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub

    }

    private void cacheSentMessage(final String paramString, final GenericRecord paramV) {
        List<GenericRecord> recordsSentToTopic = sentRecords.get(paramString);

        if (recordsSentToTopic == null) {
            recordsSentToTopic = new ArrayList<>();
            sentRecords.put(paramString, recordsSentToTopic);
        }

        recordsSentToTopic.add(paramV);
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void sendMessage(final String topic, final GenericRecord value) {
        cacheSentMessage(topic, value);
    }

    @Override
    public void sendMessage(final String topic, final String key, final GenericRecord value) {
        cacheSentMessage(topic, value);
    }

    @Override
    public void sendMessage(final String topic, final Integer pKey, final String key, final GenericRecord value) {
        cacheSentMessage(topic, value);
    }

    @Override
    public int getNoPartitions(final String topicName) {
        return 100;
    }

}
