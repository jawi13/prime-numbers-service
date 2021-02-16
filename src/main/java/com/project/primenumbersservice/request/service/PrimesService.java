package com.project.primenumbersservice.request.service;

import com.project.primenumbersservice.cache.Cache;
import com.project.primenumbersservice.helper.PrimesCalculatorHelper;
import com.project.primenumbersservice.response.Primes;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PrimesService {

    private final Primes primes;
    private final Cache<Integer, List<Integer>> cache;
    private final PrimesCalculatorHelper primesCalculatorHelper;

    public PrimesService(Primes primes, Cache<Integer, List<Integer>> cache, PrimesCalculatorHelper primesCalculatorHelper) {
        this.primes = primes;
        this.cache = cache;
        this.primesCalculatorHelper = primesCalculatorHelper;
    }

    public Primes assignPrimesValues(int n, boolean isAlternateAlgo) {
        primes.initial = n;
        primes.primes = getPrimeNumbersListValue(n, isAlternateAlgo);
        return primes;
    }

    public List<Integer> getPrimeNumbersListValue(int n, boolean isAlternateAlgo) {
        Set<Integer> cachedKeys = cache.getCleanedCacheMap().keySet();
        Integer maxCachedKey = cachedKeys.isEmpty() ? null : Collections.max(cachedKeys);
        List<Integer> maxCachedValue = cachedKeys.isEmpty() ? null : cache.get(maxCachedKey).get();
        if (maxCachedKey == null || maxCachedKey == n) {
            return isAlternateAlgo ?
                    cache.get(n).orElseGet(() -> primesCalculatorHelper.getWithAlternateAlgo(3, n, false)) :
                    cache.get(n).orElseGet(() -> primesCalculatorHelper.getWithDefaultAlgo(3, n, false));
        }
        List<Integer> primeNumbersList = new LinkedList<>();
        if (n < maxCachedKey) {
            for (int prime, index = 0; index < maxCachedValue.size() && maxCachedValue.get(index) <= n; index++) {
                prime = maxCachedValue.get(index);
                primeNumbersList.add(prime);
            }
        } else {
            primeNumbersList = maxCachedValue;
            boolean isMaxCachedKeyEven = maxCachedKey % 2 == 0;
            primeNumbersList.addAll(isAlternateAlgo ?
                    primesCalculatorHelper.getWithAlternateAlgo(isMaxCachedKeyEven ? maxCachedKey + 1 : maxCachedKey + 2, n, true) :
                    primesCalculatorHelper.getWithDefaultAlgo(isMaxCachedKeyEven ? maxCachedKey + 1 : maxCachedKey + 2, n, true));
        }
        return primeNumbersList;
    }

    public void cacheResultIfNotInCache(int n, List<Integer> value) {
        if (!cache.containsKey(n)) cache.put(n, value);
    }
}