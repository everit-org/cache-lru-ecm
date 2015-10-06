# cache-lru
Simple LRU [cache-api][1] implementation based on a high performance ConcurrentLinkedHashMap.

## Component

The module contains an ECM component. The component can
be instantiated multiple times via Configuration Admin. The component
registers three OSGi services:
 - __java.util.Map__
 - __java.util.concurrent.ConcurrentMap__
 - __com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap__: There 
 are some useful public methods that can be accessed via this class.

## Configuration
It is possible to configure the capacity of the cache via the 
**LruCacheConstants.PROP_CAPACITY** ("capacity") property.

[![Analytics](https://ga-beacon.appspot.com/UA-15041869-4/everit-org/cache-lru)](https://github.com/igrigorik/ga-beacon)

[1]: https://github.com/everit-org/cache-api
