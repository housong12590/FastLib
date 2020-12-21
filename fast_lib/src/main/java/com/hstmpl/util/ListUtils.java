package com.hstmpl.util;

import androidx.arch.core.util.Function;
import androidx.core.util.Consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ListUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> List<T> partition(List<T> list, int page, int pageSize) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        int fromIndex = (page < 1 ? 1 : page - 1) * pageSize;
        int toIndex = fromIndex + pageSize;
        if (fromIndex >= list.size()) {
            return new ArrayList<>();
        }
        if (toIndex > list.size()) {
            toIndex = list.size();
        }
        return list.subList(fromIndex, toIndex);
    }

    public static <T> void partition(List<T> list, int size, Consumer<List<T>> consumer) {
        if (isEmpty(list)) {
            return;
        }
        if (list.size() <= size) {
            consumer.accept(list);
            return;
        }
        for (List<T> ts : partition(list, size)) {
            consumer.accept(ts);
        }
    }

    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        int count = list.size() / size + ((list.size() % size) == 0 ? 0 : 1);
        List<List<T>> _result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int start = i * size;
            int end = Math.min(start + size, list.size());
            _result.add(list.subList(start, end));
        }
        return _result;
    }

    public static <T, R> List<R> map(Collection<T> list, Function<T, R> function) {
        List<R> result = new ArrayList<>();
        if (isEmpty(list)) {
            return result;
        }
        for (T t : list) {
            result.add(function.apply(t));
        }
        return result;
    }

    public static <K, V> Map<K, V> map2(Collection<V> list, Function<V, K> mapping) {
        Map<K, V> _map = new HashMap<>(list.size());
        if (isEmpty(list)) {
            return _map;
        }
        for (V value : list) {
            K key = mapping.apply(value);
            _map.put(key, value);
        }
        return _map;
    }


    public static <T, K> Map<K, List<T>> groupingBy(Collection<T> list, Function<T, K> mapping) {
        Map<K, List<T>> resultMap = new HashMap<>();
        if (isEmpty(list)) {
            return resultMap;
        }
        for (T it : list) {
            K k = mapping.apply(it);
            List<T> ts = resultMap.get(k);
            if (ts == null) {
                ts = new ArrayList<>();
                resultMap.put(k, ts);
            }
            ts.add(it);
        }
        return resultMap;
    }


    public static <T> List<T> limit(Collection<T> list, int limit) {
        List<T> _list = new ArrayList<>(limit);
        if (isEmpty(list)) {
            return _list;
        }
        if (limit <= 0) return _list;
        int index = 0;
        for (T t : list) {
            if (limit <= index) break;
            _list.add(t);
            index++;
        }
        return _list;
    }

    public static <T> T last(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public static <T> T first(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public static <T> T next(List<T> list, T obj) {
        if (isEmpty(list)) {
            return null;
        }
        int index = list.indexOf(obj);
        if (index == -1) {
            return null;
        }
        index++;
        if (index >= list.size()) {
            return null;
        }
        return list.get(index);
    }

    public static <T> T prev(List<T> list, T obj) {
        if (isEmpty(list)) {
            return null;
        }
        int index = list.indexOf(obj);
        if (index == -1) {
            return null;
        }
        index--;
        if (index < 0) {
            return null;
        }
        return list.get(index);
    }

    public static <T> T random(List<T> list) {
        Random random = new Random();
        int index = random.nextInt(list.size());
        return list.get(index);
    }

    @SafeVarargs
    public static <T> List<T> asList(T... a) {
        List<T> list = new ArrayList<>(a.length);
        list.addAll(Arrays.asList(a));
        return list;
    }
}
