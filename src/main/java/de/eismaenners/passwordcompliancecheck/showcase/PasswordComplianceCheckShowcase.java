package de.eismaenners.passwordcompliancecheck.showcase;

import de.eismaenners.passwordcompliancecheck.core.PasswordCheck;
import static de.eismaenners.passwordcompliancecheck.core.PasswordCheck.*;
import static de.eismaenners.passwordcompliancecheck.core.Result.*;
import de.eismaenners.passwordcompliancecheck.core.Result;
import de.eismaenners.passwordcompliancecheck.core.StandardRuleWithCustomizableMessage;

public class PasswordComplianceCheckShowcase {

    public PasswordComplianceCheckShowcase() {
    }

    public static void main(String[] args) {
        {
            PasswordCheck passwordCheck = create()
                .with(
                    nOf(3,
                        containsLowerCase().withMessage("Hey, please some lowercase chars"),
                        containsUpperCase(),
                        containsNumbers(),
                        containsSpecialChars("!\"§$%&/()")
                    ),
                    minimumLength(8),
                    password -> password.startsWith("abc")
                                ? complies()
                                : failed("Your password does not start with 'abc'")
                );

            testPasswordAndPrintResult(passwordCheck, "abc");
            testPasswordAndPrintResult(passwordCheck, "abc!");
            testPasswordAndPrintResult(passwordCheck, "abcA!");
            testPasswordAndPrintResult(passwordCheck, "abcdefgh!A");
            testPasswordAndPrintResult(passwordCheck, "abc0$");
            testPasswordAndPrintResult(passwordCheck, "A");
        }
        System.out.println("##############################################################");
        {
            PasswordCheck passwordCheck = create()
                .with(
                    nOf(1,
                        allOf(
                            containsLowerCase().withMessage("Hey, please some lowercase chars"),
                            containsUpperCase()
                        ),
                        allOf(
                            containsNumbers(),
                            containsSpecialChars("!\"§$%&/()")
                        )
                    ),
                    minimumLength(4)
                );

            testPasswordAndPrintResult(passwordCheck, "abc");
            testPasswordAndPrintResult(passwordCheck, "abc!");
            testPasswordAndPrintResult(passwordCheck, "abcA!");
            testPasswordAndPrintResult(passwordCheck, "abcdefgh!A");
            testPasswordAndPrintResult(passwordCheck, "abc0$");
            testPasswordAndPrintResult(passwordCheck, "A");
        }
        System.out.println("##############################################################");

        {            
            PasswordCheck.create()
                .with(
                    allOf(nOf(
                            3,
                            new StandardRuleWithCustomizableMessage() {
                            @Override
                            public Result check(String password) {
                                return password.contains("HelloWorld")
                                       ? complies()
                                       : failed("<li>Das Passwort muss 'HelloWorld' enthalten</li>");
                            }
                        },
                            password -> complies(),
                            password -> complies()
                        ).withMessage("<li><h1>Drei dieser Regeln müssen erfüllt sein</h1><ul>").withSuffixAfterSubrules("</ul></li>")
                    ).withMessage("<h1>Alle diese Regeln müssen erfüllt sein</h1><ul>").withSuffixAfterSubrules("</ul>")
                )
                .complies("abc")
                .print();
        }
    }

    private static void testPasswordAndPrintResult(PasswordCheck passwordCheck, final String password) {
        Result result
            = passwordCheck.complies(password);

        System.out.println(password);
        result.print();
    }
}
