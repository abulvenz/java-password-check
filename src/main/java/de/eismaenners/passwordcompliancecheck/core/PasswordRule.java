package de.eismaenners.passwordcompliancecheck.core;

@FunctionalInterface
public interface PasswordRule {
    public Result check(String password);
}
