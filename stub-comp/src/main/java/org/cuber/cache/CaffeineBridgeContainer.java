package org.cuber.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

public class CaffeineBridgeContainer<T> {

    private LoadingCache<String, T> cache;

    private CacheBridge<T> bridge;

    public CaffeineBridgeContainer(CacheBridge<T> bridge) {
        this.bridge = bridge;
        cache = Caffeine.newBuilder()
                .weakKeys()
                .weakValues()
                .build(key -> bridge.loadByKey(key));

    }


}
