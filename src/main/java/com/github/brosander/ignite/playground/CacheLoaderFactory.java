package com.github.brosander.ignite.playground;

import java.io.Serializable;
import javax.cache.configuration.Factory;
import javax.cache.integration.CacheLoader;

public class CacheLoaderFactory implements Factory<CacheLoader<String, String>>, Serializable {
    @Override
    public CacheLoader<String, String> create() {
        return new ExpensiveValueLoader();
    }
}
