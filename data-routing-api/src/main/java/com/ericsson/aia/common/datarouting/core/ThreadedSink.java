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

package com.ericsson.aia.common.datarouting.core;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.aia.common.datarouting.api.DataSink;
import com.ericsson.aia.common.datarouting.core.sinks.Sink;

/**
 * This class is used to hold a collection of all the sinks that make up an output. Any action carried out on this sink will be performed on all sinks
 * that make up the output.
 *
 * @param <V>
 *            The type of the record.
 */
public class ThreadedSink<V> implements DataSink<V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadedSink.class);
    private final Sink<V> sink;
    private final SinkProxy<V> sinkProxy;
    private final ExecutorService executor;
    private final BlockingQueue<Collection<V>> blockingQueue = new ArrayBlockingQueue<>(1024);

    /**
     * Constructs an instance of a DataRouterSink which encloses multiple sinks
     *
     * @param sink
     *            The sink to wrap.
     */
    public ThreadedSink(final Sink<V> sink) {
        this.sink = sink;
        final BasicThreadFactory factory = new BasicThreadFactory.Builder().namingPattern("Data-Routing-sink-%d").daemon(false)
                .priority(Thread.NORM_PRIORITY).build();

        executor = Executors.newFixedThreadPool(sink.getThreads(), factory);
        sinkProxy = new SinkProxy<>(blockingQueue, sink);
        LOGGER.info("DataRouting:: created thread pool for sink:: {}, number of threads::{}", sink, sink.getThreads());
    }

    @Override
    public void close() {
        try {
            executor.shutdown();
            executor.awaitTermination(20, TimeUnit.SECONDS);
            sink.flush();
            sink.close();
        } catch (final InterruptedException e) {
            LOGGER.error("DataRouting:: error while closing sink:: ", e);
        }
    }

    @Override
    public void flush() {
        sink.flush();
    }

    @Override
    public boolean isConnected() {
        return sink.isConnected();
    }

    @Override
    public void sendMessage(final Collection<V> records) {
        try {
            blockingQueue.put(records);
            executor.submit(sinkProxy);
        } catch (final InterruptedException e) {
            LOGGER.error("DataRouting:: error while sending message ", e);
        }
    }

}
