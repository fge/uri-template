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

    public Rule SimpleVarName()
    {
        return Sequence(VarName(), push(new SimpleVariable(match())));
    }

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

    public Rule VarSpec()
    {
        return Sequence(
            FirstOf(
                VarNameSubstring(),
                VarNameExploded(),
                SimpleVarName()
            ),
            EOI);
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
                runner = new BasicParseRunner<Object>(parser.VarSpec());
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

    abstract static class Variable
    {
        protected final String name;

        protected Variable(final String name)
        {
            this.name = name;
        }

        @Override
        public abstract String toString();
    }

    static class SimpleVariable
        extends Variable
    {
        SimpleVariable(final String name)
        {
            super(name);
        }

        @Override
        public String toString()
        {
            return name;
        }
    }

    static class SubLengthVariable
        extends Variable
    {
        private final int subLength;

        SubLengthVariable(final String name, final int subLength)
        {
            super(name);
            this.subLength = subLength;
        }

        @Override
        public String toString()
        {
            return name + " (up to " + subLength + " chars)";
        }
    }

    static class ExplodedVariable
        extends Variable
    {
        protected ExplodedVariable(final String name)
        {
            super(name);
        }

        @Override
        public String toString()
        {
            return name + " (exploded)";
        }
    }
}
