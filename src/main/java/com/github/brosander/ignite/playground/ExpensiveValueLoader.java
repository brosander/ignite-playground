package com.github.brosander.ignite.playground;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpensiveValueLoader implements CacheLoader<String, String>, Serializable {
    private static final Logger log = LoggerFactory.getLogger(ExpensiveValueLoader.class);

    @Override
    public String load(String key) throws CacheLoaderException {
        log.info("Doing expensive load for key: {}", key);
        int sleeps = 5;
        for (int i = sleeps; i > 0; i--) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new CacheLoaderException("Interrupted while loading key " + key);
            }
            log.info("Still working on it... hold on...");
        }
        return "Value for " + key;
    }

    @Override
    public Map<String, String> loadAll(Iterable<? extends String> keys) throws CacheLoaderException {
        Map<String, String> result = new HashMap<>();
        for (String key : keys) {
            result.put(key, load(key));
        }
        return result;
    }
}
