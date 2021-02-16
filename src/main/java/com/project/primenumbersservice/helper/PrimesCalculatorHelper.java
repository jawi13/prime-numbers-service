package com.project.primenumbersservice.helper;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class PrimesCalculatorHelper {

    public List<Integer> getWithDefaultAlgo(int start, int end, boolean isContinuedFromCachedValue) {
        System.out.println("getWithDefaultAlgo beginning...");
        List<Integer> primeNumbers = new LinkedList<>();
        if (!isContinuedFromCachedValue && end > 1) {
            primeNumbers.add(2);
        }
        for (int i = start; i <= end; i += 2) {
            if (isPrime(i)) {
                primeNumbers.add(i);
            }
        }
        System.out.println("getWithDefaultAlgo finished.");
        return primeNumbers;
    }

    public boolean isPrime(int n) {
        for (int i = 3; i < n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> getWithAlternateAlgo(int start, int end, boolean isContinuedFromCachedValue) {
        System.out.println("getWithAlternateAlgo beginning...");
        boolean[] prime = new boolean[end + 1];
        Arrays.fill(prime, true);
        for (int p = 2; p * p <= end; p++) {
            if (prime[p]) {
                for (int i = p * 2; i <= end; i += p) {
                    prime[i] = false;
                }
            }
        }
        List<Integer> primeNumbers = new LinkedList<>();
        if (!isContinuedFromCachedValue && end > 1) {
            primeNumbers.add(2);
        }
        for (int i = start; i <= end; i++) {
            if (prime[i]) {
                primeNumbers.add(i);
            }
        }
        System.out.println("getWithAlternateAlgo finished.");
        return primeNumbers;
    }
}
