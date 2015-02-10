/**
 * This file is part of Everit - Cache LRU.
 *
 * Everit - Cache LRU is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Everit - Cache LRU is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Everit - Cache LRU.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.everit.osgi.cache.lru;

public final class LruCacheConstants {

    public static final String SERVICE_FACTORY_PID = "org.everit.osgi.cache.lru.LruCache";

    public static final String DEFAULT_SERVICE_DESCRIPTION = "Default LRU cache";

    public static final String PROP_CAPACITY = "capacity";

    public static final long DEFAULT_CAPACITY = 1000;

    public static final String CACHE_DRIVER_NAME =
            "com.googlecode.concurrentlinkedhashmap:concurrentlinkedhashmap-lru";

    public static final String OBJECT_CLASS_NAME_CONCURRENT_LINKED_HASH_MAP =
            "com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap";

    private LruCacheConstants() {
    }

}
