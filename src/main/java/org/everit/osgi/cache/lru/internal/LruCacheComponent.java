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
package org.everit.osgi.cache.lru.internal;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.everit.osgi.cache.lru.LruCacheConstants;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

@Component(name = LruCacheConstants.SERVICE_FACTORY_PID, metatype = true, configurationFactory = true,
        policy = ConfigurationPolicy.REQUIRE)
@Properties({
        @Property(name = Constants.SERVICE_DESCRIPTION, propertyPrivate = false,
                value = LruCacheConstants.DEFAULT_SERVICE_DESCRIPTION),
        @Property(name = LruCacheConstants.PROP_CAPACITY, longValue = LruCacheConstants.DEFAULT_CAPACITY),
        @Property(name = LruCacheConstants.PROP_CACHE_NAME, value = LruCacheConstants.DEFAULT_CACHE_NAME)
})
public class LruCacheComponent<K, V> {

    private ServiceRegistration<?> cacheSR;

    @Activate
    public void activate(final BundleContext bundleContext, final Map<String, Object> componentPorperties) {
        Hashtable<String, Object> properties = new Hashtable<String, Object>();
        properties.putAll(componentPorperties);

        long capacity = (Long) componentPorperties.get(LruCacheConstants.PROP_CAPACITY);
        ConcurrentMap<K, V> cache = new ConcurrentLinkedHashMap.Builder<K, V>()
                .maximumWeightedCapacity(capacity)
                .build();

        cacheSR = bundleContext.registerService(
                new String[] { ConcurrentMap.class.getName(), Map.class.getName() },
                cache, properties);
    }

    @Deactivate
    public void deactivate() {
        cacheSR.unregister();
    }

}
