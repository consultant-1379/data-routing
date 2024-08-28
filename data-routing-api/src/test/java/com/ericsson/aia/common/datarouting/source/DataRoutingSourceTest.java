package com.ericsson.aia.common.datarouting.source;
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Properties;

import org.apache.avro.generic.GenericRecord;
import org.junit.Test;

import com.ericsson.aia.common.datarouting.api.StreamingSource;
import com.ericsson.aia.common.datarouting.api.builder.DataRouterBuilder;
import com.ericsson.aia.common.datarouting.api.builder.InputDataRouterBuilder;
import com.ericsson.aia.common.datarouting.source.mocks.SourceStub;

/**
 * Tests the basic functionality for building an input.
 *
 */

public class DataRoutingSourceTest {

    @Test
    public void shouldCreateSubscriber() {
        final InputDataRouterBuilder<String, GenericRecord> inputDataRouterBuilder = DataRouterBuilder.inputRouter();

        final Properties kafkaProperties = getKafkaProperties();

        inputDataRouterBuilder.addSource("kafka://topic1?format=avro").setProperties(kafkaProperties);
        inputDataRouterBuilder.addSource("kafka://topic2?format=avro").setProperties(kafkaProperties);

        final StreamingSource<String, GenericRecord> sink = inputDataRouterBuilder.build();

        sink.start();
        assertThat(SourceStub.getRunning(), is(2));

        final Collection<GenericRecord> records = sink.collectStream();
        final GenericRecord[] recordsArray = records.toArray(new GenericRecord[records.size()]);

        assertThat(records.size(), is(2));
        assertThat(recordsArray[0].get("att_3"), is((Object) 0));
        assertThat(recordsArray[1].get("att_3"), is((Object) 1));

        sink.close();
        assertThat(SourceStub.getRunning(), is(0));
    }

    private Properties getKafkaProperties() {
        final Properties properties = new Properties();
        properties.put("bootstrap.servers", "${kafkaBrokers}");
        properties.put("group.id", "COMPLEX_TEST_ALL_NUMBERS");
        properties.put("auto.offset.reset", "earliest");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("auto.create.topics.enable", "true");
        properties.put("input.thread.pool.size", "1");

        return properties;

    }

}
