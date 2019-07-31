package de.eismaenners.passwordcompliancecheck.core;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class PasswordCheck {

    List<PasswordRule> rules = new LinkedList<>();

    public static PasswordCheck create() {
        return new PasswordCheck();
    }

    public PasswordCheck with(PasswordRule... rules) {
        this.rules.addAll(Arrays.asList(rules));
        return this;
    }

    public Result complies(String abc) {
        return rules.isEmpty()
            ? Result.complies()
            : rules.size() > 1
            ? allOf(rules).check(abc)
            : rules.get(0).check(abc);
    }

        public static StandardRuleWithCustomizableMessage minimumLength(int length) {
        return new RegexPasswordRule(".{" + length + ",}", "Your password must contain at least " + length + " characters.");
    }

    public static StandardRuleWithCustomizableMessage containsLowerCase() {
        return new RegexPasswordRule(".*[a-z]+.*", "Your password must contain a lowercase letter (a-z).");
    }

    public static StandardRuleWithCustomizableMessage containsUpperCase() {
        return new RegexPasswordRule(".*[A-Z]+.*", "Your password must contain a uppercase letter (A-Z).");
    }

    public static StandardRuleWithCustomizableMessage containsNumbers() {
        return new RegexPasswordRule(".*[0-9]+.*", "Your password must contain a number (0-9).");
    }

    public static StandardRuleWithCustomizableMessage containsSpecialChars(String chars) {
        return new RegexPasswordRule(".*[" + Pattern.quote(chars) + "].*", "Your password must contain at least one special character of \"" + chars + "\"");
    }

    public static StandardRuleWithCustomizableMessage nOf(int numberOfRequiredRules, PasswordRule... rules) {
        return new NumberOfGivenRulesMatch(numberOfRequiredRules, rules);
    }

    public static StandardRuleWithCustomizableMessage nOf(int size, List<PasswordRule> rules) {
        return new NumberOfGivenRulesMatch(size, rules);
    }

    public static StandardRuleWithCustomizableMessage allOf(PasswordRule... rules) {
        return nOf(rules.length, rules);
    }

    public static StandardRuleWithCustomizableMessage allOf(List<PasswordRule> rules) {
        return nOf(rules.size(), rules);
    }

}
