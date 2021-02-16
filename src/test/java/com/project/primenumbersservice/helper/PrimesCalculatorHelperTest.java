package com.project.primenumbersservice.helper;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

public class PrimesCalculatorHelperTest {

    PrimesCalculatorHelper primesCalculatorHelper = new PrimesCalculatorHelper();

    @Test
    public void shouldIterateToInitialNumberAndReturnPrimesWhenIntegerIsPassedWithDefaultAlgo() {
        List<Integer> expectedResult = new LinkedList<>();
        expectedResult.add(2);
        expectedResult.add(3);
        expectedResult.add(5);
        expectedResult.add(7);
        List<Integer> result = primesCalculatorHelper.getWithDefaultAlgo(3, 10, false);
        Assert.isTrue(expectedResult.equals(result), "Prime numbers list value must match expected result");
    }

    @Test
    public void shouldIterateToInitialNumberAndReturnPrimesWhenContinuedFromCachedValueWithDefaultAlgo() {
        List<Integer> expectedResult = new LinkedList<>();
        expectedResult.add(5);
        expectedResult.add(7);
        List<Integer> result = primesCalculatorHelper.getWithDefaultAlgo(5, 10, true);
        Assert.isTrue(expectedResult.equals(result), "Prime numbers list value must match expected result");
    }

    @Test
    public void shouldReturnTrueIfNumberIsPrime() {
        Assert.isTrue(primesCalculatorHelper.isPrime(7), "Prime number must return true");
    }

    @Test
    public void shouldReturnFalseIfNumberIsNotPrime() {
        Assert.isTrue(!primesCalculatorHelper.isPrime(9), "Non-prime number must return false");
    }

    @Test
    public void shouldIterateToInitialNumberAndReturnPrimesWhenIntegerIsPassedWithAlternateAlgo() {
        List<Integer> expectedResult = new LinkedList<>();
        expectedResult.add(2);
        expectedResult.add(3);
        expectedResult.add(5);
        expectedResult.add(7);
        List<Integer> result = primesCalculatorHelper.getWithAlternateAlgo(3, 10, false);
        Assert.isTrue(expectedResult.equals(result), "Prime numbers list value must match expected result");
    }

    @Test
    public void shouldIterateToInitialNumberAndReturnPrimesWhenContinuedFromCachedValueWithAlternateAlgo() {
        List<Integer> expectedResult = new LinkedList<>();
        expectedResult.add(5);
        expectedResult.add(7);
        List<Integer> result = primesCalculatorHelper.getWithAlternateAlgo(5, 10, true);
        Assert.isTrue(expectedResult.equals(result), "Prime numbers list value must match expected result");
    }
}
