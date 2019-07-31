package de.eismaenners.passwordcompliancecheck.core;

public class ContainsSpecialChars extends StandardRuleWithCustomizableMessage {

    private final String chars;

    public ContainsSpecialChars(String chars) {
        this.chars = chars;
        this.message = "Your password must contain one special character of " + chars;
    }

    @Override
    public Result check(String password) {
        return password.chars()
            .mapToObj(i -> new byte[]{(byte) i})
            .anyMatch(c -> password.contains(new String(c)))
               ? Result.complies()
               : Result.failed(message);
    }

}
