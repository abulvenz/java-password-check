package de.eismaenners.passwordcompliancecheck.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class Result {
    
    private final List<Result> children = new LinkedList<>();
    private final String message;
    private final String suffix;
    
    public static Result complies() {
        return new Result(null, null);
    }
    
    public static Result failed(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message must not be null.");
        }
        return new Result(message, null);
    }
    
    public static Result failed(String prefix, String suffix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Message must not be null.");
        }
        return new Result(prefix, suffix);
    }
    
    private Result(String message, String suffix) {
        this.message = message;
        this.suffix = suffix;
    }
    
    public boolean ok() {
        return message == null;
    }
    
    public void whenFailed(Consumer<String> withMessage) {
        message().ifPresent(withMessage);
    }
    
    public void ifOKOrElse(Runnable ifOK, Consumer<String> orElse) {
        if (ok()) {
            ifOK.run();
        } else {
            orElse.accept(message);
        }
    }
    
    public Optional<String> message() {
        return Optional.ofNullable(message);
    }
    
    public Result addChild(Result result) {
        children.add(result);
        return this;
    }
    
    public void print() {
        print("");
    }
    
    private void print(String indent) {
        message().ifPresentOrElse(s -> {
            System.out.println(indent + s);
            children().forEach(r -> r.print(indent + "  "));
            Optional.ofNullable(suffix).ifPresent(suffix -> System.out.println(indent + suffix));
        }, () -> {
          //  System.out.println("Rules matched");
        });
    }
    
    public ReadOnlyList<Result> children() {
        return new ReadOnlyList<Result>() {
            @Override
            public int size() {
                return children.size();
            }
            
            @Override
            public Stream<Result> stream() {
                return children.stream();
            }
            
            @Override
            public Result get(int index) {
                return children.get(index);
            }
            
            @Override
            public Iterator<Result> iterator() {
                return children.iterator();
            }
        };
    }
}
