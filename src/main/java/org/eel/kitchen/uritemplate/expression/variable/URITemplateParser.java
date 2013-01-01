package org.eel.kitchen.uritemplate.expression.variable;

import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.parserunners.BasicParseRunner;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.support.ParsingResult;
import org.parboiled.support.Var;

import java.util.Scanner;

public class URITemplateParser
    extends BaseParser<Object>
{
    /*
     * Section 2.3: variables
     */

    /*
     * Basic rules
     */
    public Rule ASCIILetter()
    {
        return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'));
    }

    public Rule Digit()
    {
        return CharRange('0', '9');
    }

    public Rule NotPercentEscape()
    {
        return FirstOf(ASCIILetter(), Digit(), '_');
    }

    public Rule HexDigit()
    {
        return FirstOf(CharRange('a', 'f'), CharRange('A', 'F'), Digit());
    }

    public Rule PercentEscape()
    {
        return Sequence('%', HexDigit(), HexDigit());
    }

    public Rule VarChar()
    {
        return OneOrMore(FirstOf(NotPercentEscape(), PercentEscape()));
    }

    public Rule VarName()
    {
        return Sequence(
            VarChar(),
            ZeroOrMore(Sequence('.', VarChar()))
        );
    }

    /*
     * Variable with no modifiers
     */
    public Rule SimpleVarName()
    {
        return Sequence(VarName(), push(new SimpleVariable(match())));
    }

    /*
     * Variable with "explode" modifier
     */
    public Rule VarNameExploded()
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
    public Rule VarNameSubstring()
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
    public Rule VarSpec()
    {
        return  FirstOf(
            VarNameSubstring(),
            VarNameExploded(),
            SimpleVarName()
        );
    }

    public Rule Expression()
    {
        return Sequence(
            VarSpec(), ZeroOrMore(',', VarSpec()),
            EOI
        );
    }

    public static void main(final String... args)
    {
        final Scanner scanner = new Scanner(System.in);
        final URITemplateParser parser
            = Parboiled.createParser(URITemplateParser.class);

        String input;
        ParsingResult<Object> result;
        ParseRunner<Object> runner;

        try {
            while(true) {
                System.out.print("Enter expression: ");
                input = scanner.nextLine();
                runner = new BasicParseRunner<Object>(parser.Expression());
                result = runner.run(input);
                if (!result.matched)
                    break;
                for (final Object o : result.valueStack)
                    System.out.println(o);
            }
        } finally {
            scanner.close();
        }

        System.exit(0);
    }
}
