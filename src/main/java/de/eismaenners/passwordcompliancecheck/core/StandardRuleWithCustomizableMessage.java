package de.eismaenners.passwordcompliancecheck.core;

public abstract class StandardRuleWithCustomizableMessage implements PasswordRule{

    protected String message;
    protected String suffixAfterSubrules;

    public StandardRuleWithCustomizableMessage withMessage(String message) {
        this.message = message;
        return this;
    }

    public StandardRuleWithCustomizableMessage withSuffixAfterSubrules(String suffixAfterSubrules) {
        this.suffixAfterSubrules = suffixAfterSubrules;
        return this;
    }        
    
    String message() {
        return message;
    }
}
