package be.kdg.int5.chatbot.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockManager {
    private final ConcurrentHashMap<String, Lock> lockRegistry = new ConcurrentHashMap<>();

    public Lock getLock(String key) {
        return lockRegistry.computeIfAbsent(key, k -> new ReentrantLock());
    }
}
