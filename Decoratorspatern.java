interface TextProcessor {
    String process(String text);
}

class SimpleTextProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text;
    }
}

class UpperCaseDecorator implements TextProcessor {
    private TextProcessor textProcessor;

    public UpperCaseDecorator(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public String process(String text) {
        return textProcessor.process(text).toUpperCase();
    }
}

class TrimDecorator implements TextProcessor {
    private TextProcessor textProcessor;

    public TrimDecorator(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public String process(String text) {
        return textProcessor.process(text).trim();
    }
}

class ReplaceDecorator implements TextProcessor {
    private TextProcessor textProcessor;

    public ReplaceDecorator(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public String process(String text) {
        return textProcessor.process(text).replace(" ", "_");
    }
}
public class Decoratorspatern {
        public static void main(String[] args) {
    String text = "  Это тестовая строка.  ";

    TextProcessor processor = new ReplaceDecorator(new UpperCaseDecorator(new TrimDecorator(new SimpleTextProcessor())));
    String processedText = processor.process(text);
    System.out.println("Обработанный текст: " + processedText);

    TextProcessor processor2 = new TrimDecorator(new UpperCaseDecorator(new SimpleTextProcessor()));
    String processedText2 = processor2.process(text);
    System.out.println("Обработанный текст 2: " + processedText2);
    }
}

