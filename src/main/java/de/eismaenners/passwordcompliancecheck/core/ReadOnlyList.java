package de.eismaenners.passwordcompliancecheck.core;

import java.util.stream.Stream;

public interface ReadOnlyList<T> extends Iterable<T>{
    int size();
    Stream<T> stream();
    T get(int index);        
}
