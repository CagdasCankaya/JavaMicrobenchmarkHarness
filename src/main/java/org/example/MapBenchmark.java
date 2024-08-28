package org.example;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

@State(Scope.Thread)
public class MapBenchmark {

    private Map<Integer, String> map;

    @Setup(Level.Trial)
    public void setUp() {
        map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, "Value" + i);
        }
    }

    @Benchmark
    public void testUsingForEachAndJava8() {
        map.forEach((key, value) -> {
            String result = value.toUpperCase();
        });
    }
    @Benchmark
    public void testUsingForAndIterator() {
        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            String result = entry.getValue().toUpperCase();
        }
    }
    @Benchmark
    public void testUsingJava8StreamAPI() {
        map.values().stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MapBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
