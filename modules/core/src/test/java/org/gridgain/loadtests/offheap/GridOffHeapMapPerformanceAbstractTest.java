/* @java.file.header */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package org.gridgain.loadtests.offheap;

import org.gridgain.grid.util.typedef.*;
import org.gridgain.grid.util.offheap.*;
import org.gridgain.testframework.junits.common.*;

import java.util.*;

/**
 * Tests off-heap map.
 */
public abstract class GridOffHeapMapPerformanceAbstractTest extends GridCommonAbstractTest {
    /** Random. */
    private static final Random RAND = new Random();

    /** */
    protected static final int LOAD_CNT = 1024 * 1024;

    /** Sample map. */
    private static Map<String, T3<String, byte[], byte[]>> kvMap =
        new HashMap<>(LOAD_CNT);

    /** Unsafe map. */
    private GridOffHeapMap<String> map;

    /** */
    protected float load = 0.75f;

    /** */
    protected int initCap = 1024 * 1024 * 1024;

    /** */
    protected int concurrency = 16;

    /** */
    protected short lruStripes = 16;

    /** */
    protected GridOffHeapEvictListener evictClo;

    /** */
    protected long mem = 12L * 1024L * 1024L * 1024L;

    /** */
    protected long dur = 60 * 1000;//2 * 60 * 60 * 1000;

    /**
     *
     */
    protected GridOffHeapMapPerformanceAbstractTest() {
        super(false);
    }

    /** {@inheritDoc} */
    @Override protected void beforeTest() throws Exception {
        map = newMap();

        if (kvMap.isEmpty())
            for (int i = 0; i < LOAD_CNT; i++) {
                String k = string();
                String v = string();

                kvMap.put(k,  new T3<>(v, k.getBytes(), v.getBytes()));
            }
    }

    /** {@inheritDoc} */
    @Override protected void afterTest() throws Exception {
        if (map != null)
            map.destruct();
    }

    /**
     * @return New map.
     */
    protected abstract <K> GridOffHeapMap<K> newMap();

    /**
     * @param key Key.
     * @return Hash.
     */
    private int hash(Object key) {
        return hash(key.hashCode());
    }

    /**
     * @param h Hashcode.
     * @return Hash.
     */
    private int hash(int h) {
        // Apply base step of MurmurHash; see http://code.google.com/p/smhasher/
        // Despite two multiplies, this is often faster than others
        // with comparable bit-spread properties.
        h ^= h >>> 16;
        h *= 0x85ebca6b;
        h ^= h >>> 13;
        h *= 0xc2b2ae35;

        return (h >>> 16) ^ h;
    }

    /**
     *
     * @return New Object.
     */
    private String string() {
        String key = "";

        for (int i = 0; i < 3; i++)
            key += RAND.nextLong();

        return key;
    }

    /**
     * Test plain hash map.
     */
    public void testHashMapPutRemove() {
        Map<String, String> map = new HashMap<>(LOAD_CNT);

        info("Starting standard HashMap performance test...");

        long cnt = 0;

        long start = System.currentTimeMillis();

        boolean rmv = false;

        boolean done = false;

        while (!done) {
            for (Map.Entry<String, T3<String, byte[], byte[]>> e : kvMap.entrySet()) {
                String key = e.getKey();
                T3<String, byte[], byte[]> t = e.getValue();

                try {
                    if (rmv)
                        map.remove(key);
                    else
                        map.put(key, t.get1());
                }
                catch (GridOffHeapOutOfMemoryException ex) {
                    error("Map put failed for count: " + cnt, ex);

                    throw ex;
                }

                if (cnt > 0 && cnt % 10000000 == 0) {
                    long cur = System.currentTimeMillis();

                    long throughput = cnt * 1000 / (cur - start);

                    X.println("Insert [cnt=" + cnt + ", ops/sec=" + throughput + ']');

                    if ((cur - start) > dur) {
                        done = true;

                        break;
                    }
                }

                cnt++;
            }

            rmv = !rmv;
        }
    }

    /**
     *
     */
    public void testInsertRemoveLoad() {
        info("Starting insert performance test...");

        long cnt = 0;

        long start = System.currentTimeMillis();

        boolean rmv = false;

        boolean done = false;

        while (!done) {
            for (Map.Entry<String, T3<String, byte[], byte[]>> e : kvMap.entrySet()) {
                String key = e.getKey();
                T3<String, byte[], byte[]> t = e.getValue();

                try {
                    if (rmv)
                        map.remove(hash(key), t.get2());
                    else
                        map.insert(hash(key), t.get2(), t.get3());
                }
                catch (GridOffHeapOutOfMemoryException ex) {
                    error("Map put failed for count: " + cnt, ex);

                    throw ex;
                }

                if (cnt > 0 && cnt % 10000000 == 0) {
                    long cur = System.currentTimeMillis();

                    long throughput = cnt * 1000 / (cur - start);

                    X.println("Insert [cnt=" + cnt + ", ops/sec=" + throughput + ']');

                    if ((cur - start) > dur) {
                        done = true;

                        break;
                    }
                }

                cnt++;
            }

            rmv = !rmv;
        }
    }


    /**
     *
     */
    public void testPutRemoveLoad() {
        info("Starting put performance test...");

        long cnt = 0;

        long start = System.currentTimeMillis();

        boolean rmv = false;

        boolean done = false;

        while (!done) {
            for (Map.Entry<String, T3<String, byte[], byte[]>> e : kvMap.entrySet()) {
                String key = e.getKey();
                T3<String, byte[], byte[]> t = e.getValue();

                try {
                    if (rmv)
                        map.remove(hash(key), t.get2());
                    else
                        map.put(hash(key), t.get2(), t.get3());
                }
                catch (GridOffHeapOutOfMemoryException ex) {
                    error("Map put failed for count: " + cnt, ex);

                    throw ex;
                }

                if (cnt > 0 && cnt % 10000000 == 0) {
                    long cur = System.currentTimeMillis();

                    long throughput = cnt * 1000 / (cur - start);

                    X.println("Put [cnt=" + cnt + ", ops/sec=" + throughput + ']');

                    if ((cur - start) > dur) {
                        done = true;

                        break;
                    }
                }

                cnt++;
            }

            rmv = cnt % 3 == 0;
        }
    }
}
