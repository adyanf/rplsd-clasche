package com.rplsd.clasche.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class ClascheDriver {
    public void start(String in) {
        CharStream charStream = new ANTLRInputStream(in);
        ClascheLexer lexer = new ClascheLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        ClascheParser parser = new ClascheParser(commonTokenStream);
        parser.addErrorListener(ParserErrorListener.INSTANCE);
        try {
            ParseTree tree = parser.eval(); // parse the content and get the tree
            ClascheWalker listener = new ClascheWalker();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(listener, tree);
        } catch (ParseCancellationException e) {
            //do nothing
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
