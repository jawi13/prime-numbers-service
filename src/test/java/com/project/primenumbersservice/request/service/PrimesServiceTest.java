package com.project.primenumbersservice.request.service;

import com.project.primenumbersservice.cache.Cache;
import com.project.primenumbersservice.helper.PrimesCalculatorHelper;
import com.project.primenumbersservice.response.Primes;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class PrimesServiceTest {

    Primes primes = new Primes();
    Cache<Integer, List<Integer>> cache = new Cache<>();
    PrimesCalculatorHelper primesCalculatorHelper = new PrimesCalculatorHelper();
    PrimesService primesService = new PrimesService(primes, cache, primesCalculatorHelper);

    @Test
    public void shouldAssignPrimeValuesWhenIntegerPassed() {
        Integer initial = 10;
        List<Integer> primesValues = new LinkedList<>();
        primesValues.add(2);
        primesValues.add(3);
        primesValues.add(5);
        primesValues.add(7);
        primesService.assignPrimesValues(initial, false);
        Assert.isTrue((primes.initial == initial), "Initial property must match passed Integer");
        Assert.isTrue((primes.primes.equals(primesValues)), "Primes property must match assigned values");
    }

    @Test
    public void shouldGetPrimeNumbersListValueWhenIntegerPassedAndCacheIsEmpty() {
        List<Integer> expectedResult = new LinkedList<>();
        expectedResult.add(2);
        expectedResult.add(3);
        expectedResult.add(5);
        expectedResult.add(7);
        List<Integer> result = primesService.getPrimeNumbersListValue(10, false);
        Assert.isTrue(expectedResult.equals(result), "Prime numbers list value must match expected result");
    }

    @Test
    public void shouldGetPrimeNumbersListValueWhenIntegerPassedEqualsMaxCachedKey() {
        List<Integer> cachedValue = new LinkedList<>();
        cachedValue.add(2);
        cachedValue.add(3);
        cachedValue.add(5);
        cachedValue.add(7);
        cache.put(10, cachedValue);
        Assert.isTrue(cache.getCleanedCacheMap().containsKey(10), "Cache must contain added key");
        List<Integer> result = primesService.getPrimeNumbersListValue(10, false);
        Assert.isTrue(cachedValue.equals(result), "Prime numbers list value must match cached value");
        cache.clear();
    }

    @Test
    public void shouldGetPrimeNumbersListValueWhenIntegerPassedIsLessThanMaxCachedKey() {
        List<Integer> cachedValue = new LinkedList<>();
        cachedValue.add(2);
        cachedValue.add(3);
        cachedValue.add(5);
        cachedValue.add(7);
        List<Integer> expectedResult = new LinkedList<>();
        expectedResult.add(2);
        expectedResult.add(3);
        expectedResult.add(5);
        cache.put(10, cachedValue);
        Assert.isTrue(cache.getCleanedCacheMap().containsKey(10), "Cache must contain added key");
        List<Integer> result = primesService.getPrimeNumbersListValue(5, false);
        Assert.isTrue(expectedResult.equals(result), "Prime numbers list value must match expected result");
        cache.clear();
    }

    @Test
    public void shouldGetPrimeNumbersListValueWhenIntegerPassedIsGreaterThanMaxCachedKey() {
        List<Integer> cachedValue = new LinkedList<>();
        cachedValue.add(2);
        cachedValue.add(3);
        cachedValue.add(5);
        cachedValue.add(7);
        List<Integer> expectedResult = cachedValue;
        expectedResult.add(11);
        expectedResult.add(13);
        cache.put(10, cachedValue);
        Assert.isTrue(cache.getCleanedCacheMap().containsKey(10), "Cache must contain added key");
        List<Integer> result = primesService.getPrimeNumbersListValue(15, false);
        Assert.isTrue(expectedResult.equals(result), "Prime numbers list value must match expected result");
        cache.clear();
    }

    @Test
    public void shouldCacheResultIfNotInCache() {
        List<Integer> cacheValue = new LinkedList<>();
        cacheValue.add(2);
        cacheValue.add(3);
        cacheValue.add(5);
        cacheValue.add(7);
        Assert.isTrue(cache.getCleanedCacheMap().isEmpty(), "Cache must be empty");
        cache.put(10, cacheValue);
        Assert.isTrue(cache.getCleanedCacheMap().containsKey(10), "Cache must contain added key");
        Assert.isTrue(cache.get(10).get().equals(cacheValue), "Cached value must match added value");
        cache.clear();
    }
}
