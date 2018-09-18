package org.cuber.cache;

import com.github.benmanes.caffeine.cache.LoadingCache;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.cuber.stub.basic.CacheDef;
import org.cuber.stub.vo.StubConfVO;
import org.cuber.zk.XClient;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CommonCache<T extends StubConfVO> implements NodeCacheListener {

    protected LoadingCache<String, T> cache;


    protected CacheDef<T> def;

    protected ITopCache<T> tiTopCache;

    public CommonCache(ITopCache<T> tiTopCache) {
        this.tiTopCache = tiTopCache;
    }

    protected synchronized CacheDef<T> loadCacheDef() {
        if (Objects.isNull(def)) {
            def = tiTopCache.loadDef();
            /**
             * 第一次拿的时候， 注册变更事件
             */
            XClient.addChangeListener(def.getCacheZkPath(), this);
        }
        return def;
    }

    @Override
    public void nodeChanged() throws Exception {
        cache.invalidateAll();
    }

    protected String findFirstPopKey(T searchIns) {
        CacheDef<T> def = loadCacheDef();
        Set<List<String>> fields = def.getFieldKeys();
        String key = null;
        if (CollectionUtils.isNotEmpty(fields) && Objects.nonNull(searchIns)) {
            Iterator<List<String>> iterator = fields.iterator();
            while (iterator.hasNext()) {
                List<String> fieldNames = iterator.next();
                if (CacheDefUtils.isKey(searchIns, fieldNames)) {
                    key = CacheDefUtils.key(searchIns, fieldNames);
                    break;
                }
            }
        }
        return key;
    }

    public T loadByKey(T searchIns) {
        if (Objects.nonNull(searchIns)) {
            return cache.get(findFirstPopKey(searchIns));
        }
        return null;
    }
}
