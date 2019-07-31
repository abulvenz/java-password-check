package de.eismaenners.passwordcompliancecheck.core;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class RegexPasswordRule extends StandardRuleWithCustomizableMessage{

    private final Predicate<String> predicate;

    public RegexPasswordRule(Pattern pattern, String message) {
        this.predicate = pattern.asPredicate();
        this.message = message;
    }

    public RegexPasswordRule(String pattern, String message) {
        this.predicate = Pattern.compile(pattern).asPredicate();
        this.message = message;
    }

    @Override
    public Result check(String password) {
        return predicate.test(password)
            ? Result.complies()
            : Result.failed(message);
    }
}
