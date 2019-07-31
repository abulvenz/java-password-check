package de.eismaenners.passwordcompliancecheck.core;

import java.util.Arrays;
import java.util.List;

public class NumberOfGivenRulesMatch extends StandardRuleWithCustomizableMessage {

    private final int numberOfRequiredRules;
    private final List<PasswordRule> rules;

    NumberOfGivenRulesMatch(int numberOfRequiredRules, PasswordRule[] rules) {
        this.numberOfRequiredRules = numberOfRequiredRules;
        this.rules = Arrays.asList(rules);
        message = buildMessage();
    }

    NumberOfGivenRulesMatch(int size, List<PasswordRule> rules) {
        this.numberOfRequiredRules = size;
        this.rules = rules;
        message = buildMessage();
    }

    @Override
    public Result check(String password) {
        final long numberOfPassedRules = this.rules.stream()
            .map(rule -> rule.check(password))
            .filter(result_ -> result_.ok())
            .count();

        Result result = numberOfPassedRules >= numberOfRequiredRules
                        ? Result.complies()
                        : Result.failed(message.replaceAll("\\{\\{numberOfMissingRules\\}\\}", "" + (numberOfRequiredRules - numberOfPassedRules)),suffixAfterSubrules);

        this.rules.stream()
            .map(rule -> rule.check(password))
          //  .filter(result_ -> !result_.ok())
            .forEach(result::addChild);

        return result;

    }

    private String buildMessage() {
        return rules.size() == numberOfRequiredRules
               ? "All of the following subrules must match."
               : "At least {{numberOfMissingRules}} more of the following subrules must match.";
    }
}
