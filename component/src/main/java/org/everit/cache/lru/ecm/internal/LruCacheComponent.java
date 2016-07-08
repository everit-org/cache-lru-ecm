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
package org.everit.cache.lru.ecm.internal;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.everit.cache.CacheConstants;
import org.everit.cache.lru.ecm.LruCacheConstants;
import org.everit.osgi.ecm.annotation.Activate;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Deactivate;
import org.everit.osgi.ecm.annotation.ManualService;
import org.everit.osgi.ecm.annotation.ManualServices;
import org.everit.osgi.ecm.annotation.attribute.LongAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.component.ComponentContext;
import org.everit.osgi.ecm.extender.ExtendComponent;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

/**
 * ECM component for {@link LruCacheConstants#OBJECT_CLASS_CONCURRENT_LINKED_HASH_MAP} based on
 * {@link ConcurrentLinkedHashMap}.
 *
 * @param <K>
 *          the type of keys maintained by this map
 * @param <V>
 *          the type of mapped values
 */
@ExtendComponent
@Component(componentId = LruCacheConstants.SERVICE_FACTORY_PID,
    configurationPolicy = ConfigurationPolicy.FACTORY, label = "Everit LRU Cache",
    description = "LRU in-memory cache component based on a high performance"
        + " ConcurrentLinkedHashMap.")
@StringAttributes({
    @StringAttribute(attributeId = Constants.SERVICE_DESCRIPTION,
        defaultValue = LruCacheConstants.DEFAULT_SERVICE_DESCRIPTION,
        priority = LruCacheComponent.P01_SERVICE_DESCRIPTION,
        label = "Service Description",
        description = "The description of this component configuration. It is used to easily "
            + "identify the service registered by this component.") })
@ManualServices(@ManualService({ Map.class, ConcurrentMap.class, ConcurrentLinkedHashMap.class }))
public class LruCacheComponent<K, V> {

  public static final int P01_SERVICE_DESCRIPTION = 1;

  public static final int P02_CAPACITY = 2;

  private long capacity;

  private ServiceRegistration<?> serviceRegistration;

  /**
   * Component activator method.
   */
  @Activate
  public void activate(final ComponentContext<LruCacheComponent<K, V>> componentContext) {
    Dictionary<String, Object> serviceProperties =
        new Hashtable<String, Object>(componentContext.getProperties());
    ConcurrentMap<K, V> cache = new ConcurrentLinkedHashMap.Builder<K, V>()
        .maximumWeightedCapacity(capacity)
        .build();

    serviceRegistration = componentContext.registerService(
        new String[] {
            LruCacheConstants.OBJECT_CLASS_CONCURRENT_LINKED_HASH_MAP.getName(),
            LruCacheConstants.OBJECT_CLASS_CONCURRENT_MAP.getName(),
            CacheConstants.OBJECT_CLASS_MAP.getName() },
        cache, serviceProperties);
  }

  /**
   * Component deactivate method.
   */
  @Deactivate
  public void deactivate() {
    if (serviceRegistration != null) {
      serviceRegistration.unregister();
    }
  }

  @LongAttribute(attributeId = LruCacheConstants.PROP_CAPACITY,
      defaultValue = LruCacheConstants.DEFAULT_CAPACITY, priority = P02_CAPACITY,
      label = "Capacity", description = "The maximum number of elements stored in the cache.")
  public void setCapacity(final long capacity) {
    this.capacity = capacity;
  }

}
