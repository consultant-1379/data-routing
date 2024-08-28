package com.ericsson.aia.common.datarouting.sink;
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

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.junit.Before;
import org.junit.Test;

import com.ericsson.aia.common.datarouting.api.DataSink;
import com.ericsson.aia.common.datarouting.api.builder.DataRouterBuilder;
import com.ericsson.aia.common.datarouting.api.builder.OutputDataRouterBuilder;
import com.ericsson.aia.common.datarouting.sink.mocks.SinkStub;

/**
 * Tests the basic functionality for building an output.
 *
 */
public class DataRoutingSinkBuilderTest {
    private static final String USER_SCHEMA = "{" + "\"type\":\"record\"," + "\"name\":\"myrecord\"," + "\"fields\":["
            + "  { \"name\":\"att_1\", \"type\":\"string\" }," + "  { \"name\":\"att_2\", \"type\":\"string\" },"
            + "  { \"name\":\"att_3\", \"type\":\"int\" }" + "]}";

    private Schema schema;

    private GenericRecord testRecord1, testRecord2, testRecord3, testRecord4;

    @Before
    public void setup() {
        final Schema.Parser parser = new Schema.Parser();
        schema = parser.parse(USER_SCHEMA);
        testRecord1 = getTestRecord("Hello", "world", "1");
        testRecord2 = getTestRecord("bye", "world", "3");
        testRecord3 = getTestRecord("hola", "world", "1");
        testRecord4 = getTestRecord("adios", "world", "4");
        SinkStub.reset();
    }

    @Test
    public void shouldCreateSinkWithFunctionFilteringAndPartitioning() {

        final OutputDataRouterBuilder<GenericRecord> outputDataRouterBuilder = DataRouterBuilder.outputRouter();

        outputDataRouterBuilder.addSink("kafka://topic1?format=avro").setProperties(getKafkaProperties()).partitionStrategy().applyRoundRobin()
                .filter().acceptRecord().function("att_1 == 'adios' && ((Integer)att_3) + 1 == 5");

        outputDataRouterBuilder.addSink("kafka://topic2?format=avro").setProperties(getKafkaProperties()).partitionStrategy().applyRoundRobin()
                .filter().acceptRecord().regex("att_3", "4").regex("att_1", "bye").acceptRecord()
                .function("att_1 == 'adios' && (Integer)att_3 + 1 == 5");

        outputDataRouterBuilder.addSink("kafka://topic3?format=avro").setProperties(getKafkaProperties()).partitionStrategy()
                .applyFunction("att_1.hashCode() + att_3").filter().acceptRecord().schema("my.*").regex("att_2", "world").regex("att_3", "1")
                .acceptRecord().regex("att_1", ".ello|.ola");

        final DataSink<GenericRecord> sink = outputDataRouterBuilder.build();

        sink.sendMessage(asList(testRecord1, testRecord2, testRecord3, testRecord4));
        sink.close();
        assertThat(SinkStub.getRecordsSentToTopic("topic1"), contains(testRecord4));
        assertThat(SinkStub.getRecordsSentToTopic("topic2"), contains(testRecord4));
        assertThat(SinkStub.getRecordsSentToTopic("topic3"), contains(testRecord1, testRecord3));
    }

    @Test
    public void shouldCreateSinkWithRegexFilteringAndPartitioning() {

        final OutputDataRouterBuilder<GenericRecord> outputDataRouterBuilder = DataRouterBuilder.outputRouter();

        outputDataRouterBuilder.addSink("kafka://topic1?format=avro").setProperties(getKafkaProperties()).partitionStrategy().applyRoundRobin()
                .filter().acceptRecord().regex("att_3", "\\d").regex("att_1", ".*bye");

        outputDataRouterBuilder.addSink("kafka://topic2?format=avro").setProperties(getKafkaProperties()).partitionStrategy().applyRoundRobin()
                .filter().acceptRecord().regex("att_3", "3").regex("att_1", "bye");

        outputDataRouterBuilder.addSink("kafka://topic3?format=avro").setProperties(getKafkaProperties());

        final DataSink<GenericRecord> sink = outputDataRouterBuilder.build();
        sink.sendMessage(asList(testRecord1, testRecord2, testRecord3, testRecord4));
        sink.close();
        assertThat(SinkStub.getRecordsSentToTopic("topic1"), contains(testRecord2));
        assertThat(SinkStub.getRecordsSentToTopic("topic2"), contains(testRecord2));
        assertThat(SinkStub.getRecordsSentToTopic("topic3"), contains(testRecord1, testRecord2, testRecord3, testRecord4));
    }

    @Test
    public void shouldCreateSinkWithSchemaFilteringAndPartitioning() {
        final OutputDataRouterBuilder<GenericRecord> outputDataRouterBuilder = DataRouterBuilder.outputRouter();

        outputDataRouterBuilder.addSink("kafka://topic1?format=avro").setProperties(getKafkaProperties()).partitionStrategy()
                .applyFunction("att_1.hashCode() + att_3").filter().acceptRecord().schema("my.*");

        outputDataRouterBuilder.addSink("kafka://topic2?format=avro").setProperties(getKafkaProperties()).filter().acceptRecord()
                .schema("NotMySchema");

        outputDataRouterBuilder.addSink("kafka://topic3?format=avro").setProperties(getKafkaProperties());

        outputDataRouterBuilder.addSink("kafka://topic4?format=avro").partitionStrategy().applyRoundRobin(1).setProperties(getKafkaProperties());

        final DataSink<GenericRecord> sink = outputDataRouterBuilder.build();
        sink.sendMessage(asList(testRecord1, testRecord2, testRecord3, testRecord4));
        sink.close();
        assertThat(SinkStub.getRecordsSentToTopic("topic1"), contains(testRecord1, testRecord2, testRecord3, testRecord4));
        assertThat(SinkStub.getRecordsSentToTopic("topic2"), is(nullValue()));
        assertThat(SinkStub.getRecordsSentToTopic("topic3"), contains(testRecord1, testRecord2, testRecord3, testRecord4));
        assertThat(SinkStub.getRecordsSentToTopic("topic4"), contains(testRecord1, testRecord2, testRecord3, testRecord4));
    }

    @Test
    public void shouldCreateSinkWithMixedFilteringAndPartitioning() {
        final OutputDataRouterBuilder<GenericRecord> outputDataRouterBuilder = DataRouterBuilder.outputRouter();

        outputDataRouterBuilder.addSink("kafka://topic1?format=avro").setProperties(getKafkaProperties()).partitionStrategy()
                .applyFunction("att_1.hashCode() + att_3").filter().acceptRecord().schema("my.*").function("att_1 + ' ' +att_2 == 'Hello world'")
                .regex("att_1", "H.*").acceptRecord().function("(Integer)att_3 - 1 == 0").regex("att_1", "Hola");

        outputDataRouterBuilder.addSink("kafka://topic2?format=avro").setProperties(getKafkaProperties()).filter().acceptRecord()
                .schema("NotMySchema").regex("att_3", "\\d").acceptRecord().schema("myrecord").regex("att_3", "\\d");

        outputDataRouterBuilder.addSink("kafka://topic3?format=avro").setProperties(getKafkaProperties()).filter().acceptRecord()
                .function("att_3 > 20 ").regex("att_3", "\\d\\d").acceptRecord().schema("myrecord").regex("att_3", "\\d\\d").regex("att_1", "Hello");

        final DataSink<GenericRecord> sink = outputDataRouterBuilder.build();
        sink.sendMessage(asList(testRecord1, testRecord2, testRecord3, testRecord4));
        sink.close();
        assertThat(SinkStub.getRecordsSentToTopic("topic1"), contains(testRecord1));
        assertThat(SinkStub.getRecordsSentToTopic("topic2"), contains(testRecord1, testRecord2, testRecord3, testRecord4));
        assertThat(SinkStub.getRecordsSentToTopic("topic3"), is(nullValue()));
    }

    private Properties getKafkaProperties() {
        final Properties properties = new Properties();
        properties.put("bootstrap.servers", "${kafkaBrokers}");
        properties.put("output.thread.pool.size", "2");
        properties.put("acks", "all");
        properties.put("retries", "0");
        properties.put("batch.size", "16384");
        properties.put("linger.ms", "1");
        properties.put("buffer.memory", "33554432");
        return properties;
    }

    private GenericRecord getTestRecord(final String att1, final String att2, final String att3) {
        final GenericRecord record = new GenericData.Record(schema);
        record.put("att_1", att1);
        record.put("att_2", att2);
        record.put("att_3", att3);
        return record;
    }

}
