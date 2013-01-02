package org.eel.kitchen.uritemplate.expression.variable;

import org.eel.kitchen.uritemplate.expression.ExpressionBuilder;
import org.eel.kitchen.uritemplate.expression.Operator;
import org.parboiled.Action;
import org.parboiled.BaseParser;
import org.parboiled.Context;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.matchers.AnyOfMatcher;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.support.Characters;
import org.parboiled.support.ParsingResult;
import org.parboiled.support.Var;

import java.util.Scanner;

class URITemplateParser
    extends BaseParser<Variable>
{
    static final Rule OPCHAR
        = new AnyOfMatcher(Characters.of(Operator.validChars()));

    private final ExpressionBuilder builder;

    URITemplateParser(final ExpressionBuilder builder)
    {
        this.builder = builder;
    }

    /*
     * Section 2.3: variables
     */

    /*
     * Basic rules
     */
    Rule ASCIILetter()
    {
        return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'));
    }

    Rule Digit()
    {
        return CharRange('0', '9');
    }

    Rule NotPercentEscape()
    {
        return FirstOf(ASCIILetter(), Digit(), '_');
    }

    Rule HexDigit()
    {
        return FirstOf(CharRange('a', 'f'), CharRange('A', 'F'), Digit());
    }

    Rule PercentEscape()
    {
        return Sequence('%', HexDigit(), HexDigit());
    }

    Rule VarChar()
    {
        return OneOrMore(FirstOf(NotPercentEscape(), PercentEscape()));
    }

    Rule VarName()
    {
        return Sequence(
            VarChar(),
            ZeroOrMore(Sequence('.', VarChar()))
        );
    }

    /*
     * Variable with no modifiers
     */
    Rule SimpleVarName()
    {
        return Sequence(VarName(), push(new SimpleVariable(match())));
    }

    /*
     * Variable with "explode" modifier
     */
    Rule VarNameExploded()
    {
        final Var<String> name = new Var<String>();
        return Sequence(
            VarName(),
            name.set(match()),
            '*',
            push(new ExplodedVariable(name.get()))
        );
    }

    /*
     * Variable with "substring" modifier
     */
    Rule VarNameSubstring()
    {
        final Var<String> name = new Var<String>();
        final Var<Integer> subLength = new Var<Integer>();
        return Sequence(
            // Variable name
            VarName(), name.set(match()),
            // ':'
            ':',
            // Integer, less than or equal to 10000
            OneOrMore(Digit()),
            subLength.set(Integer.parseInt(match())),
            ACTION(subLength.get() <= 10000),
            push(new SubLengthVariable(name.get(), subLength.get()))
        );
    }

    /*
     * Any variable
     */
    Rule VarSpec()
    {
        return  FirstOf(VarNameSubstring(), VarNameExploded(), SimpleVarName());
    }

    Rule Expression()
    {
        return Sequence(
            // Operator
            Optional(Operator()),
            // Variable list
            VarSpec(), ZeroOrMore(',', VarSpec()),
            // End of input
            EOI
        );
    }

    Rule Operator()
    {
        final Var<Character> opchar = new Var<Character>();
        return Sequence(
            OPCHAR,
            opchar.set(matchedChar()),
            new Action<Object>()
            {
                @Override
                public boolean run(Context<Object> context)
                {
                    final Operator operator = Operator.fromChar(opchar.get());
                    builder.setOperator(operator);
                    return true;
                }
            }
        );
    }

    public static void main(final String... args)
    {
        final Scanner scanner = new Scanner(System.in);

        ExpressionBuilder builder;
        URITemplateParser parser;

        String input;
        ParsingResult<Variable> result;
        ParseRunner<Variable> runner;

        try {
            while(true) {
                builder = new ExpressionBuilder();
                parser = Parboiled.createParser(URITemplateParser.class,
                    builder);
                System.out.print("Enter expression: ");
                input = scanner.nextLine();
                runner = new BasicParseRunner<Variable>(parser.Expression());
                result = runner.run(input);
                if (!result.matched)
                    break;
                builder.setVariables(result.valueStack);
                System.out.println(builder);
            }
        } finally {
            scanner.close();
        }

        System.exit(0);
    }
}
