package com.nasa.nasaapod.controller;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cache")
public class CacheStatsController {

    @Autowired
    private CacheManager cacheManager;

    @GetMapping("/stats")
    public ResponseEntity<?> getCacheStats() {

        Cache cache = cacheManager.getCache("apodCache");

        Object nativeCache = (cache != null) ? cache.getNativeCache() : null;

        if (nativeCache instanceof com.github.benmanes.caffeine.cache.Cache<?, ?> caffeineNativeCache) {
            CacheStats stats = caffeineNativeCache.stats();

            return ResponseEntity.ok(
                    new CacheStatsResponse(
                            stats.hitCount(),
                            stats.missCount(),
                            stats.hitRate(),
                            stats.missRate(),
                            stats.loadSuccessCount(),
                            stats.loadFailureCount(),
                            stats.totalLoadTime(),
                            stats.evictionCount()
                    )
            );
        }

        return ResponseEntity.badRequest().body("Cache is not a Caffeine cache!");
    }

    // DTO for JSON response
    record CacheStatsResponse(
            long hitCount,
            long missCount,
            double hitRate,
            double missRate,
            long loadSuccess,
            long loadFailure,
            long totalLoadTime,
            long evictionCount
    ) {}
}