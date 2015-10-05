/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.cache.lru.ecm;

import java.util.concurrent.ConcurrentMap;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

/**
 * Constants of the Lru Cache component.
 */
public final class LruCacheConstants {

  public static final String CACHE_DRIVER_NAME =
      "com.googlecode.concurrentlinkedhashmap:concurrentlinkedhashmap-lru";

  public static final long DEFAULT_CAPACITY = 1000;

  public static final String DEFAULT_SERVICE_DESCRIPTION = "Default LRU cache";

  public static final Class<ConcurrentLinkedHashMap> OBJECT_CLASS_NAME_CONCURRENT_LINKED_HASH_MAP =
      ConcurrentLinkedHashMap.class;

  public static final Class<ConcurrentMap> OBJECT_CLASS_NAME_CONCURRENT_MAP = ConcurrentMap.class;

  public static final String PROP_CAPACITY = "capacity";

  public static final String SERVICE_FACTORY_PID = "org.everit.cache.lru.ecm.LruCache";

  private LruCacheConstants() {
  }

}
