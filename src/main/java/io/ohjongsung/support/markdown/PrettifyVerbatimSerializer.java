package io.ohjongsung.support.markdown;

import org.parboiled.common.StringUtils;
import org.pegdown.Printer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.VerbatimNode;

/**
 * Created by ohjongsung on 2017-05-07. JavaScript google-code-prettify library (코드 이쁘게
 * 꾸며주는) 라이브러리를 사용하기 위해서 code tag 에 prettyprint class attribute 를 추가하는 확장 클래스
 */
public class PrettifyVerbatimSerializer implements VerbatimSerializer {
    public static final PrettifyVerbatimSerializer INSTANCE = new PrettifyVerbatimSerializer();

    @Override
    public void serialize(final VerbatimNode node, final Printer printer) {
        printer.println().print("<pre><code");
        String className = "prettyprint";
        if (!StringUtils.isEmpty(node.getType())) {
            className = className.concat(" " + node.getType());
        }
        printAttribute(printer, "class", className);
        printer.print(">");
        String text = node.getText();
        // print HTML breaks for all initial newlines
        while (text.charAt(0) == '\n') {
            printer.print("<br/>");
            text = text.substring(1);
        }
        printer.printEncoded(text);
        printer.print("</code></pre>");

    }

    private void printAttribute(final Printer printer, final String name, final String value) {
        printer.print(' ').print(name).print('=').print('"').print(value).print('"');
    }
}
