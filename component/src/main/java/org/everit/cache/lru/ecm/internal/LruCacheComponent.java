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
import java.util.concurrent.ConcurrentMap;

import org.everit.cache.CacheConstants;
import org.everit.cache.lru.ecm.LruCacheConstants;
import org.everit.osgi.ecm.annotation.Activate;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Deactivate;
import org.everit.osgi.ecm.annotation.attribute.LongAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.component.ComponentContext;
import org.everit.osgi.ecm.extender.ECMExtenderConstants;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

import aQute.bnd.annotation.headers.ProvideCapability;

/**
 * ECM component for {@link CacheConstants#OBJECT_CLASS_NAME_MAP} based on
 * {@link ConcurrentLinkedHashMap}.
 *
 * @param <K>
 *          the type of keys maintained by this map
 * @param <V>
 *          the type of mapped values
 */
@Component(componentId = LruCacheConstants.SERVICE_FACTORY_PID,
    configurationPolicy = ConfigurationPolicy.FACTORY, label = "Everit LRU Cache",
    description = "LRU cache component based on a high performance ConcurrentLinkedHashMap.")
@ProvideCapability(ns = ECMExtenderConstants.CAPABILITY_NS_COMPONENT,
    value = ECMExtenderConstants.CAPABILITY_ATTR_CLASS + "=${@class}")
@StringAttributes({
    @StringAttribute(attributeId = Constants.SERVICE_DESCRIPTION,
        defaultValue = LruCacheConstants.DEFAULT_SERVICE_DESCRIPTION,
        priority = LruCacheComponent.PRIORITY_01_SERVICE_DESCRIPTION,
        label = "Service Description",
        description = "The description of this component configuration. It is used to easily "
            + "identify the service registered by this component."),
    @StringAttribute(attributeId = CacheConstants.ATTR_CACHE_DRIVER_NAME,
        defaultValue = LruCacheConstants.CACHE_DRIVER_NAME,
        priority = LruCacheComponent.PRIORITY_02_CACHE_DRIVER_NAME, label = "Cache driver name",
        description = "The name of the cache driver used in this implementation.") })
public class LruCacheComponent<K, V> {

  public static final int PRIORITY_01_SERVICE_DESCRIPTION = 1;

  public static final int PRIORITY_02_CACHE_DRIVER_NAME = 2;

  public static final int PRIORITY_03_CAPACITY = 3;

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
            LruCacheConstants.OBJECT_CLASS_NAME_CONCURRENT_LINKED_HASH_MAP,
            LruCacheConstants.OBJECT_CLASS_NAME_CONCURRENT_MAP,
            CacheConstants.OBJECT_CLASS_NAME_MAP },
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
      defaultValue = LruCacheConstants.DEFAULT_CAPACITY, priority = PRIORITY_03_CAPACITY,
      label = "Capacity", description = "The maximum number of elements stored in the cache.")
  public void setCapacity(final long capacity) {
    this.capacity = capacity;
  }

}
