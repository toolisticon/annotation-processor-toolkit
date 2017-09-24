package de.holisticon.annotationprocessortoolkit.templating;

import de.holisticon.annotationprocessortoolkit.templating.exceptions.InvalidPathException;
import de.holisticon.annotationprocessortoolkit.templating.exceptions.MissingClosingTagException;
import de.holisticon.annotationprocessortoolkit.templating.templateblocks.ForTemplateBlock;
import de.holisticon.annotationprocessortoolkit.templating.templateblocks.IfTemplateBlock;
import de.holisticon.annotationprocessortoolkit.templating.templateblocks.PlainTextTemplateBlock;
import de.holisticon.annotationprocessortoolkit.templating.templateblocks.StaticTemplateBlock;
import de.holisticon.annotationprocessortoolkit.templating.templateblocks.TemplateBlockBinder;
import de.holisticon.annotationprocessortoolkit.templating.templateblocks.TemplateBlockType;
import de.holisticon.annotationprocessortoolkit.templating.templateblocks.VariableTextTemplateBlock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParseUtilities {

    public static class ParserResult {

        private final int beginIndex;
        private final int endIndex;
        private final String content;

        public ParserResult(int beginIndex, int endIndex, String content) {
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
            this.content = content;
        }


        public int getBeginIndex() {
            return beginIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public String getContent() {
            return content;
        }
    }



    private enum NextBlockType {
        NONE,
        FOR,
        IF,
        STATIC,
        DYNAMIC_TEXT
    }


    public static final Pattern DYNAMIC_TEXT_BLOCK_REGEX = Pattern.compile("[$]<\\s*((?:\\w|.)*?)\\s*>");


    public static TemplateBlockBinder parseString(String templateString) {

        String tmpTemplateString = templateString;

        TemplateBlockBinder binder = new TemplateBlockBinder(tmpTemplateString);

        NextDetectedBlockResult nextBlock = TemplateBlockType.getNextBlock(templateString);

        while (nextBlock != null) {

            // add plain text block upfront
            if (nextBlock.getBeginIndex() != 0) {
                binder.addTemplateBlock(new PlainTextTemplateBlock(tmpTemplateString.substring(0, nextBlock.getBeginIndex())));
            }




            switch (nextBlock.getTemplateBlockType()) {

                case DYNAMIC_TEXT: {
                    ParserResult nextDynamicText = getNextDynamicText(tmpTemplateString);
                    binder.addTemplateBlock(new VariableTextTemplateBlock(nextDynamicText.getContent()));
                    tmpTemplateString = tmpTemplateString.substring(nextDynamicText.getEndIndex());
                    break;
                }
                case FOR: {


                    ForTemplateBlock forTemplateBlock = new ForTemplateBlock(nextBlock.getAttributes(), nextBlock.getContent());
                    binder.addTemplateBlock(forTemplateBlock);

                    forTemplateBlock.setBinder(parseString(forTemplateBlock.getTemplateString()));
                    tmpTemplateString = nextBlock.getRemainingStringToBeProcessed();

                    break;
                }
                case IF: {

                    IfTemplateBlock ifTemplateBlock = new IfTemplateBlock(nextBlock.getAttributes(), nextBlock.getContent());
                    binder.addTemplateBlock(ifTemplateBlock);

                    ifTemplateBlock.setBinder(parseString(ifTemplateBlock.getTemplateString()));
                    tmpTemplateString = nextBlock.getRemainingStringToBeProcessed();

                    break;
                }
                case STATIC: {


                    StaticTemplateBlock staticTemplateBlock = new StaticTemplateBlock(nextBlock.getContent());
                    binder.addTemplateBlock(staticTemplateBlock);

                    tmpTemplateString = nextBlock.getRemainingStringToBeProcessed();


                    break;
                }


            }

            // get next block type
            nextBlock = TemplateBlockType.getNextBlock(tmpTemplateString);
        }

        // add last plain text block
        binder.addTemplateBlock(new PlainTextTemplateBlock(tmpTemplateString));

        return binder;
    }





    protected static ParserResult getNextDynamicText(String templateString) {

        if (templateString == null) {
            return null;
        }

        // first check for control block sequence
        Matcher matcher = DYNAMIC_TEXT_BLOCK_REGEX.matcher(templateString);
        if (matcher.find()) {

            return new ParserResult(matcher.start(), matcher.end(), matcher.group(1));

        }

        return null;
    }

    protected Integer getNextControlBlock(String templateString) {
        return null;
    }

    public static String readResourceToString(String resourcefileName) throws Exception {

        InputStream inputStream = ParseUtilities.class.getResourceAsStream(resourcefileName);
        if (inputStream != null) {
            return readFromInputStream(inputStream);
        } else {
            throw new IllegalArgumentException("Can't open resource file '" + resourcefileName + "'");
        }


    }

    public static String readFromInputStream(InputStream stream) throws IOException {

        byte[] buffer = new byte[10000];
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        int line = 0;
        // read bytes from stream, and store them in buffer
        while ((line = stream.read(buffer)) != -1) {
            // Writes bytes from byte array (buffer) into output stream.
            os.write(buffer, 0, line);
        }
        stream.close();
        os.flush();
        os.close();

        return new String(os.toByteArray());
    }


    public static Object resolvePath(Map<String, Object> model, String path) {

        if (model == null || path == null) {
            return null;
        }

        // break path strings to path tokens
        String[] pathTokens = path.split("[.]");

        Object currentNode = model;

        for (String currentPathToken : pathTokens) {

            if (currentNode == null) {
                throw new InvalidPathException("Path cannot be resolved. Encountered null value in path so token " + currentPathToken + " cannot be applied.");
            } else if (currentNode.getClass().isArray()) {
                throw new InvalidPathException("Path cannot be resolved. Encountered array in path so token \" + currentPathToken + \" cannot be applied.");
            } else if (currentNode instanceof Map) {

                currentNode = ((Map) currentNode).get(currentPathToken);

            } else {

                // POJOS

                // get getter
                String getterName = getGetter(currentNode, currentPathToken);

                if (getterName == null) {
                    throw new InvalidPathException("Path cannot be resolved. Path token " + currentPathToken + " not resolvable");
                }

                // now call method via reflection
                try {
                    Method getterMethodToCall = currentNode.getClass().getMethod(getterName);

                    currentNode = getterMethodToCall.invoke(currentNode);

                } catch (NoSuchMethodException e) {
                    // can be ignored - because it's guaranteed that the method exists
                } catch (Exception e) {
                    throw new InvalidPathException("Path cannot be resolved. Cannot invoke getter method", e);
                }


            }


        }

        return currentNode;

    }

    /**
     * Gets the name of the getter.
     * Getter method must take no parameters and must be public
     *
     * @param instance          the instance to search the getter in
     * @param fieldNameOrGetter the name of the field or method to get the getter for
     * @return the name of the method or getter method or null if no callable getter can be found
     */
    protected static String getGetter(Object instance, String fieldNameOrGetter) {

        final String[] GETTER_PREFIXES = {"get", "is", "has"};

        final String trimmedFieldNameOrGetter = fieldNameOrGetter != null ? fieldNameOrGetter.trim() : null;

        if (instance == null || trimmedFieldNameOrGetter == null || trimmedFieldNameOrGetter.length() == 0) {
            return null;
        }

        try {

            // check if passed fieldNameOrGetter is already method without parameter
            Method method = instance.getClass().getMethod(trimmedFieldNameOrGetter.trim());

            if (java.lang.reflect.Modifier.isPublic(method.getModifiers())) {
                return trimmedFieldNameOrGetter;
            }


        } catch (NoSuchMethodException e) {
            // ignore this
        }

        // now check existence of method with getter prefixes - return first match
        for (String getterPrefix : GETTER_PREFIXES) {

            // construct getter name
            String getterName = getterPrefix + trimmedFieldNameOrGetter.substring(0, 1).toUpperCase() + trimmedFieldNameOrGetter.substring(1);


            try {

                // check if passed fieldNameOrGetter is already method without parameter
                Method method = instance.getClass().getMethod(getterName);
                if (java.lang.reflect.Modifier.isPublic(method.getModifiers())) {
                    return getterName;
                }

            } catch (NoSuchMethodException e) {
                // ignore this
            }


        }

        return null;

    }

}
