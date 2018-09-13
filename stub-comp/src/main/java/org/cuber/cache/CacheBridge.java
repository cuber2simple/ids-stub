package org.cuber.cache;

public interface CacheBridge<T> {

    T loadByKey(String key);

    String refreshTopic();
}
