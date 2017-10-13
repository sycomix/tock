/*
 * Copyright (C) 2017 VSCT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.vsct.tock.shared.cache

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import fr.vsct.tock.shared.error
import fr.vsct.tock.shared.injector
import fr.vsct.tock.shared.longProperty
import fr.vsct.tock.shared.provide
import mu.KotlinLogging
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

private val inMemoryCache: Cache<Any, Any> =
        CacheBuilder
                .newBuilder()
                .maximumSize(longProperty("tock_cache_in_memory_maximum_size", 10000))
                .expireAfterAccess(longProperty("tock_cache_in_memory_expiration_in_ms", 1000 * 60 * 60L), TimeUnit.MILLISECONDS)
                .build()

private val NOT_PRESENT = Any()

private val cache: TockCache get() = injector.provide()

private fun <T> Any?.replaceNotPresent(): T? {
    @Suppress("UNCHECKED_CAST")
    return if (this == NOT_PRESENT) {
        null
    } else {
        this as T
    }
}

private fun inMemoryKey(id: String, type: String): Any = id to type

/**
 * Returns the value for specified id and type.
 * If no value exists, [valueProvider] provides the value to cache.
 * If [valueProvider] throws exception or returns null, no value is cached and null is returned.
 */
fun <T : Any> getOrCache(id: String, type: String, valueProvider: () -> T?): T? {
    return inMemoryCache.get(inMemoryKey(id, type)) {
        cache.get(id, type)
                ?:
                try {
                    valueProvider.invoke()?.apply {
                        putInCache(id, type, this)
                    }
                } catch (e: Exception) {
                    logger.error(e)
                    null
                }
                ?: NOT_PRESENT
    }.replaceNotPresent()
}

/**
 * Returns the value for specified id and type.
 * If no value exists, null is returned.
 */
fun <T : Any> getFromCache(id: String, type: String): T? {
    return try {
        inMemoryCache.get(inMemoryKey(id, type)) {
            cache.get(id, type) ?: NOT_PRESENT
        }.replaceNotPresent()
    } catch (e: Exception) {
        logger.error(e)
        null
    }
}

/**
 * Adds in cache the specified value.
 */
fun <T : Any> putInCache(id: String, type: String, value: T) {
    try {
        inMemoryCache.put(inMemoryKey(id, type), value)
        cache.put(id, type, value)
    } catch (e: Exception) {
        logger.error(e)
    }
}

/**
 * Remove the value for specified id and type from cache.
 */
fun removeFromCache(id: String, type: String) {
    inMemoryCache.invalidate(inMemoryKey(id, type))
    cache.remove(id, type)
}



