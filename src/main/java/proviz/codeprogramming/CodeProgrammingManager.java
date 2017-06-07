package proviz.codeprogramming;

import proviz.codegeneration.CodeGeneration;
import proviz.codedistribution.ArduinoFlasher;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import proviz.codegeneration.CodeGenerationTemplate;

/**
 * Created by Burak on 10/24/16.
 */
public class CodeProgrammingManager {
    private CharStream charStream;
    private ProvizLexer provizLexer;
    private CommonTokenStream commonTokenStream;
    private ProvizParser provizParser;
    private ParseTree parseTree;
    private ParseTreeWalker parseTreeWalker;
    private CodeGenerationTemplate codeGenerationTemplate;
    private CodeGeneration codeGeneration;



    public CodeProgrammingManager(String sourceCode, CodeGenerationTemplate codeGenerationTemplate)
    {
        this.codeGenerationTemplate = codeGenerationTemplate;
        charStream = new ANTLRInputStream(sourceCode);
        provizLexer = new ProvizLexer(charStream);
        commonTokenStream = new CommonTokenStream(provizLexer);
        provizParser = new ProvizParser(commonTokenStream);
        parseTree = provizParser.entireCode();
         parseTreeWalker = new ParseTreeWalker();

    }

    public void compile()
    {
        parseTreeWalker.walk(new ProvizCompilerTreeTraverse(codeGenerationTemplate), parseTree);
    }

    private String generateProvizCode(CodeGenerationTemplate codeGenerationTemplate)
    {
        try {
            codeGeneration = new CodeGeneration(codeGenerationTemplate);
          return  codeGeneration.createCode();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }


}
