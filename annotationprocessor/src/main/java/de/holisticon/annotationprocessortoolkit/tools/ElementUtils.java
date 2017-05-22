package de.holisticon.annotationprocessortoolkit.tools;

import de.holisticon.annotationprocessortoolkit.internal.Utilities;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.Validator;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Utility class which helps to handle different {@link Element} related tasks.
 */
public final class ElementUtils {

    /**
     * Hidden constructor that prevents instantiation.
     */
    private ElementUtils() {

    }

    public static class CheckKindOfElement {

        /**
         * Checks if passed Element instance is of kind enum.
         *
         * @param e the element to check
         * @return true if passed element is of kind enum, otherwise false
         */
        public static boolean isEnum(Element e) {
            return isOfKind(e, ElementKind.ENUM);
        }

        /**
         * Checks if passed Element instance is of kind class.
         *
         * @param e the element to check
         * @return true if passed element is of kind class, otherwise false
         */
        public static boolean isClass(Element e) {
            return isOfKind(e, ElementKind.CLASS);
        }

        /**
         * Checks if passed Element instance is of kind interface.
         *
         * @param e the element to check
         * @return true if passed element is of kind enum, otherwise false
         */
        public static boolean isInterface(Element e) {
            return isOfKind(e, ElementKind.INTERFACE);
        }

        /**
         * Checks if passed Element instance is of kind method.
         *
         * @param e the element to check
         * @return true if passed element is of kind method, otherwise false
         */
        public static boolean isMethod(Element e) {
            return isOfKind(e, ElementKind.METHOD);
        }


        /**
         * Checks if passed Element instance is of kind parameter.
         *
         * @param e the element to check
         * @return true if passed element is of kind parameter, otherwise false
         */
        public static boolean isParameter(Element e) {
            return isOfKind(e, ElementKind.PARAMETER);
        }

        /**
         * Checks if passed Element instance is of kind constructor.
         *
         * @param e the element to check
         * @return true if passed element is of kind constructor, otherwise false
         */
        public static boolean isConstructor(Element e) {
            return isOfKind(e, ElementKind.CONSTRUCTOR);
        }

        /**
         * Checks if passed Element instance is of kind field.
         *
         * @param e the element to check
         * @return true if passed element is of kind field, otherwise false
         */
        public static boolean isField(Element e) {
            return isOfKind(e, ElementKind.FIELD);
        }

        /**
         * Checks if passed Element instance is of a specific kind.
         *
         * @param e    the element to check
         * @param kind the kind to check for
         * @return true if passed element is of the passed kind, otherwise false
         */
        public static boolean isOfKind(Element e, ElementKind kind) {
            return kind != null && e != null ? kind.equals(e.getKind()) : false;
        }

    }

    public static class CastElement {

        private final static Set<ElementKind> typeElementKindLUT = Utilities.convertVarargsToSet(ElementKind.CLASS, ElementKind.INTERFACE, ElementKind.ENUM);
        private final static Set<ElementKind> variableElementKindLUT = Utilities.convertVarargsToSet(ElementKind.PARAMETER, ElementKind.FIELD);
        private final static Set<ElementKind> executableElementKindLUT = Utilities.convertVarargsToSet(ElementKind.CONSTRUCTOR, ElementKind.METHOD);

        /**
         * Checks if passed element can be casted to TypeElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to TypeElement, otherwise false
         */
        public static boolean isTypeElement(Element e) {
            return e != null && typeElementKindLUT.contains(e.getKind());
        }

        /**
         * Checks if passed element can be casted to VariableElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to VariableElement, otherwise false
         */
        public static boolean isVariableElement(Element e) {
            return e != null && variableElementKindLUT.contains(e.getKind());
        }

        /**
         * Checks if passed element can be casted to ExecutableElement.
         *
         * @param e the element to check
         * @return true if passed element can be cast to ExecutableElement, otherwise false
         */
        public static boolean isExecutableElement(Element e) {
            return e != null && executableElementKindLUT.contains(e.getKind());
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static TypeElement castClass(Element e) {
            return castToTypeElement(e);
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static TypeElement castInterface(Element e) {
            return castToTypeElement(e);
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static TypeElement castEnum(Element e) {
            return castToTypeElement(e);
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static VariableElement castParameter(Element e) {
            return castToVariableElement(e);
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static VariableElement castField(Element e) {
            return castToVariableElement(e);
        }

        /**
         * Casts an element to TypeElement.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast to TypeElement
         */
        public static TypeElement castToTypeElement(Element e) {
            return (TypeElement) e;
        }

        /**
         * Casts an element to VariableElement.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast to TypeElement
         */
        public static VariableElement castToVariableElement(Element e) {
            return (VariableElement) e;
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static ExecutableElement castConstructor(Element e) {
            return castToExecutableElement(e);
        }

        /**
         * Casts an element.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast
         */
        public static ExecutableElement castMethod(Element e) {
            return castToExecutableElement(e);
        }

        /**
         * Casts an element to ExecutableElement.
         * This is a convenient method. You don't have to think about the matching element types for a specific ElementKind.
         *
         * @param e the element to cast
         * @return the casted element
         * @throws ClassCastException if passed Element can't be cast to ExecutableElement
         */
        public static ExecutableElement castToExecutableElement(Element e) {
            return (ExecutableElement) e;
        }

        /**
         * Casts a list of elements to a list of elements.
         *
         * @param elementList  the list to be processed
         * @param typeToCastTo the Element sub class to cast to
         * @param <T>
         * @return a new list containing all elements of passed elementList
         */
        public static <T extends Element> List<T> castElementList(List<? extends Element> elementList, Class<T> typeToCastTo) {
            List<T> result = new ArrayList<T>(elementList.size());
            for (Element enclosedElement : elementList) {
                result.add((T) enclosedElement);
            }
            return result;
        }
    }

    public static class CheckModifierOfElement {
        /**
         * Check if passed Element has public Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the public modifier, otherwise false
         */
        public static boolean hasPublicModifier(Element e) {
            return Validator.MODIFIER_VALIDATOR.getValidator().hasAllOf(e, Modifier.PUBLIC);
        }

        /**
         * Check if passed Element has protected Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the protected modifier, otherwise false
         */
        public static boolean hasProtectedModifier(Element e) {
            return Validator.MODIFIER_VALIDATOR.getValidator().hasAllOf(e, Modifier.PROTECTED);
        }

        /**
         * Check if passed Element has private Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the private modifier, otherwise false
         */
        public static boolean hasPrivateModifier(Element e) {
            return Validator.MODIFIER_VALIDATOR.getValidator().hasAllOf(e, Modifier.PRIVATE);
        }

        /**
         * Check if passed Element has abstract Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the abstract modifier, otherwise false
         */
        public static boolean hasAbstractModifier(Element e) {
            return Validator.MODIFIER_VALIDATOR.getValidator().hasAllOf(e, Modifier.ABSTRACT);
        }

    /*
     * Check if passed Element has default Modifier.
     *
     * @param e the element to check
     * @return true if the passed element has the default modifier, otherwise false
     */
        // Disabled until we move to Java 8
    /*
    public boolean hasDefaultModifier(Element e) {
        return hasModifiers(e, Modifier.DEFAULT);
    }
    */

        /**
         * Check if passed Element has static Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the static modifier, otherwise false
         */
        public static boolean hasStaticModifier(Element e) {
            return Validator.MODIFIER_VALIDATOR.getValidator().hasAllOf(e, Modifier.STATIC);
        }

        /**
         * Check if passed Element has final Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the final modifier, otherwise false
         */
        public static boolean hasFinalModifier(Element e) {
            return Validator.MODIFIER_VALIDATOR.getValidator().hasAllOf(e, Modifier.FINAL);
        }

        /**
         * Check if passed Element has transient Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the transient modifier, otherwise false
         */
        public static boolean hasTransientModifier(Element e) {
            return Validator.MODIFIER_VALIDATOR.getValidator().hasAllOf(e, Modifier.TRANSIENT);
        }

        /**
         * Check if passed Element has volatile Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the volatile modifier, otherwise false
         */
        public static boolean hasVolatileModifier(Element e) {
            return Validator.MODIFIER_VALIDATOR.getValidator().hasAllOf(e, Modifier.VOLATILE);
        }

        /**
         * Check if passed Element has synchronized Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the synchronized modifier, otherwise false
         */
        public static boolean hasSynchronizedModifier(Element e) {
            return Validator.MODIFIER_VALIDATOR.getValidator().hasAllOf(e, Modifier.SYNCHRONIZED);
        }

        /**
         * Check if passed Element has native Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the native modifier, otherwise false
         */
        public static boolean hasNativeModifier(Element e) {
            return Validator.MODIFIER_VALIDATOR.getValidator().hasAllOf(e, Modifier.NATIVE);
        }

        /**
         * Check if passed Element has strictfp Modifier.
         *
         * @param e the element to check
         * @return true if the passed element has the strictfp modifier, otherwise false
         */
        public static boolean hasStrictfpModifier(Element e) {
            return Validator.MODIFIER_VALIDATOR.getValidator().hasAllOf(e, Modifier.STRICTFP);
        }


    }

    public static class AccessEnclosedElements {
        public static List<VariableElement> getEnclosedFields(TypeElement e) {
            List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.FIELD);
            return CastElement.castElementList(enclosedElementsOfKind, VariableElement.class);
        }

        /**
         * Get all enclosed methods.
         *
         * @param e the element to search within
         * @return all methods of the passed element
         */
        public static List<ExecutableElement> getEnclosedMethods(TypeElement e) {
            List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.METHOD);
            return CastElement.castElementList(enclosedElementsOfKind, ExecutableElement.class);
        }


        /**
         * Get all enclosed constructors.
         *
         * @param e the element to search within
         * @return all methods of the passed element
         */
        public static List<ExecutableElement> getEnclosedConstructors(TypeElement e) {
            List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.CONSTRUCTOR);
            return CastElement.castElementList(enclosedElementsOfKind, ExecutableElement.class);
        }

        /**
         * Get all enclosed classes.
         *
         * @param e the element to search within
         * @return all methods of the passed element
         */
        public static List<TypeElement> getEnclosedTypes(TypeElement e) {
            List<? extends Element> enclosedElementsOfKind = getEnclosedElementsOfKind(e, ElementKind.CLASS);
            return CastElement.castElementList(enclosedElementsOfKind, TypeElement.class);
        }


        /**
         * Returns all enclosed elements with matching name for passed regular expression.
         *
         * @param element the element to search within
         * @param name    the name to search for
         * @return the elements with matching name
         */
        public static List<? extends Element> getEnclosedElementsByName(Element element, String... name) {
            if (element == null) {
                return new ArrayList<Element>();
            }

            return FilterElements.filterElementListByName(element.getEnclosedElements(), name);

        }

        /**
         * Returns all enclosed elements with matching name for passed regular expression.
         *
         * @param element   the element to search within
         * @param nameRegex the regular expression for name
         * @return the elements with matching name
         * @throws PatternSyntaxException if passed pattern is invalid
         */
        public static List<Element> getEnclosedElementsByNameRegex(Element element, String nameRegex) {
            List<Element> result = new ArrayList<Element>();

            if (element != null && nameRegex != null) {

                Pattern pattern = Pattern.compile(nameRegex);

                for (Element enclosedElement : element.getEnclosedElements()) {

                    if (pattern.matcher(enclosedElement.getSimpleName().toString()).matches()) {
                        result.add(enclosedElement);
                    }

                }

            }

            return result;
        }


        /**
         * Returns all enclosed elements with matching name for passed regular expression.
         *
         * @param element the element to search within
         * @param kind    the kinds to filter
         * @return all enclosed element that are matching one of the passed kinds
         */
        public static List<? extends Element> getEnclosedElementsOfKind(Element element, ElementKind... kind) {

            if (element == null) {
                return new ArrayList<Element>();
            }

            return FilterElements.filterElementListByKind(element.getEnclosedElements(), kind);

        }


        /**
         * Gets all enclosed elements that are annotated with ALL passed annotations
         *
         * @param element     the element to search within
         * @param annotations the annotations to filter by
         * @return the enclosed element that are annotated with ALL passed annotations.
         */
        public static List<? extends Element> getEnclosedElementsWithAnnotation(Element element, Class<? extends Annotation>... annotations) {

            if (element == null) {
                return new ArrayList<Element>();
            }

            return FilterElements.filterElementListByAnnotation(element.getEnclosedElements(), annotations);

        }
    }

    public static class FilterElements {

        /**
         * Filter passed list of elements by modifiers.
         * Returned list will contain all Elements with have ALL passed modifiers
         *
         * @param elementList the Element list to filter
         * @param modifiers   the Modifiers to filter by
         * @param <T>
         * @return the filtered list with elements that have all passed modifiers
         */
        public static <T extends Element> List<T> filterElementListByModifier(List<T> elementList, Modifier... modifiers) {
            List<T> result = new ArrayList<T>();

            if (elementList != null && modifiers != null) {

                outer:
                for (T element : elementList) {

                    for (Modifier modifier : modifiers) {
                        if (!element.getModifiers().contains(modifier)) {
                            continue outer;
                        }
                    }

                    result.add(element);
                }

            }

            return result;
        }


        /**
         * Filter passed list of elements by annotation.
         * Returned list will contain all Elements that are annotated with ALL of the passed annotations
         *
         * @param elementList the Element list to filter
         * @param annotations the annotations to filter by
         * @param <T>
         * @return the filtered list with elements that have matching names
         */
        public static <T extends Element> List<T> filterElementListByAnnotation(List<T> elementList, Class<? extends Annotation>... annotations) {
            List<T> result = new ArrayList<T>();

            if (elementList != null && annotations != null && annotations.length > 0) {

                outer:
                for (T element : elementList) {

                    for (Class<? extends Annotation> annotation : annotations) {
                        if (element.getAnnotation(annotation) == null) {
                            continue outer;
                        }
                    }
                    result.add(element);

                }

            }

            return result;
        }

        /**
         * Filter passed list of elements by annotation.
         * Returned list will contain Elements that are not annotated with ALL of the passed annotations.
         *
         * @param elementList the Element list to filter
         * @param annotations the annotations to filter by
         * @param <T>
         * @return the filtered list with elements that are annotated with none of the no matching annotations
         */
        public static <T extends Element> List<T> inverseFilterElementListByAnnotation(List<T> elementList, Class<? extends Annotation>... annotations) {
            List<T> result = new ArrayList<T>();

            if (elementList != null) {

                for (T element : elementList) {

                    boolean matchedAll = annotations != null && annotations.length > 0;

                    if (annotations != null) {
                        for (Class<? extends Annotation> annotation : annotations) {
                            if (annotation != null && element.getAnnotation(annotation) == null) {
                                matchedAll = false;
                                break;
                            }
                        }
                    }

                    if (!matchedAll) {
                        result.add(element);
                    }

                }

            }

            return result;
        }


        /**
         * Filter passed list of elements by names.
         * Returned list will contain all Elements with match ONE of the passed names
         *
         * @param elementList the Element list to filter
         * @param names       the names to filter by
         * @param <T>
         * @return the filtered list with elements that have matching names
         */
        public static <T extends Element> List<T> filterElementListByName(List<T> elementList, String... names) {
            List<T> result = new ArrayList<T>();

            if (elementList != null && names != null) {

                for (T element : elementList) {

                    for (String name : names) {
                        if (name != null && name.equals(element.getSimpleName().toString())) {
                            result.add(element);
                            break;
                        }
                    }

                }

            }

            return result;
        }

        /**
         * Filter passed list of elements by names.
         * Returned list will contain Elements that have no matching names.
         *
         * @param elementList the Element list to filter
         * @param names       the names to filter by
         * @param <T>
         * @return the filtered list with elements that have no matching names
         */
        public static <T extends Element> List<T> inverseFilterElementListByName(List<T> elementList, String... names) {
            List<T> result = new ArrayList<T>();

            if (elementList != null) {

                outer:
                for (T element : elementList) {

                    if (names != null) {
                        for (String name : names) {
                            if (name != null && name.equals(element.getSimpleName().toString())) {
                                // continue with next element
                                continue outer;
                            }
                        }
                    }
                    result.add(element);

                }

            }

            return result;
        }


        /**
         * Returns all enclosed elements with matching name for ONE of the passed regular expressions.
         *
         * @param elementList        the element to search within
         * @param regularExpressions the regular expressions for name
         * @return the elements with matching name
         * @throws PatternSyntaxException if passed pattern is invalid
         */
        public static <T extends Element> List<T> filterElementsByNameWithRegularExpression(List<T> elementList, String... regularExpressions) {
            List result = new ArrayList();

            if (elementList != null && regularExpressions != null) {

                for (Element enclosedElement : elementList) {

                    for (String regularExpression : regularExpressions) {

                        if (regularExpression != null) {

                            Pattern pattern = Pattern.compile(regularExpression);

                            if (pattern.matcher(enclosedElement.getSimpleName().toString()).matches()) {
                                result.add(enclosedElement);
                                break;
                            }

                        }

                    }
                }

            }

            return result;
        }

        /**
         * Filter passed list of elements by kind.
         * Returned list will contain all Elements that are matching ONE of the passed kinds.
         *
         * @param elementList the Element list to filter
         * @param kinds       the kinds to filter by
         * @param <T>
         * @return the filtered list with elements that have matching kinds
         */
        public static <T extends Element> List<T> filterElementListByKind(List<T> elementList, ElementKind... kinds) {
            List<T> result = new ArrayList<T>();

            if (elementList != null && kinds != null) {

                for (T element : elementList) {

                    for (ElementKind kind : kinds) {
                        if (kind != null && kind.equals(element.getKind())) {
                            result.add(element);
                            break;
                        }
                    }

                }

            }

            return result;
        }

        /**
         * Filter passed list of elements by kind.
         * Returned list will contain all Elements that are matching NONE of the passed kinds.
         *
         * @param elementList the Element list to filter
         * @param kinds       the kinds to filter by
         * @param <T>
         * @return the filtered list with elements that have no matching kind
         */
        public static <T extends Element> List<T> inverseFilterElementListByKind(List<T> elementList, ElementKind... kinds) {
            List<T> result = new ArrayList<T>();

            if (elementList != null) {

                outer:
                for (T element : elementList) {

                    if (kinds != null) {
                        for (ElementKind kind : kinds) {
                            if (kind != null && kind.equals(element.getKind())) {
                                // continue with next element
                                continue outer;
                            }
                        }
                    }
                    result.add(element);

                }

            }

            return result;
        }

    }

    public static boolean isAnnotatedWith(Element element, Class<? extends Annotation> annotationType) {
        return element == null || annotationType == null ? false : element.getAnnotation(annotationType) != null;
    }

    /**
     * Checks if passed element is annotated with all passed annotations.
     *
     * @param element         the element to check
     * @param annotationTypes the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public static boolean isAnnotatedWithAllOf(Element element, Class<? extends Annotation>... annotationTypes) {

        if (element == null || annotationTypes == null) {
            return false;
        }

        // convert array to set to filter doubled elements
        for (Class<? extends Annotation> annotationType : Utilities.convertArrayToSet(annotationTypes)) {

            if (!isAnnotatedWith(element, annotationType)) {
                return false;
            }

        }

        return true;
    }

    /**
     * Checks if passed element is annotated with at least one of the passed annotations.
     *
     * @param element         the element to check
     * @param annotationTypes the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public static boolean isAnnotatedWithAtLeastOneOf(Element element, Class<? extends Annotation>... annotationTypes) {
        if (element == null || annotationTypes == null) {
            return false;
        }

        // convert array to set to filter doubled elements
        for (Class<? extends Annotation> annotationType : Utilities.convertArrayToSet(annotationTypes)) {

            if (isAnnotatedWith(element, annotationType)) {
                return true;
            }

        }

        return false;
    }

    /**
     * Checks if passed element is annotated with EXACTLY one of the passed annotations.
     *
     * @param element         the element to check
     * @param annotationTypes the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public static boolean isAnnotatedWithOneOf(Element element, Class<? extends Annotation>... annotationTypes) {
        if (element == null || annotationTypes == null) {
            return false;
        }

        int count = 0;
        // convert array to set to filter doubled elements
        for (Class<? extends Annotation> annotationType : Utilities.convertArrayToSet(annotationTypes)) {

            if (isAnnotatedWith(element, annotationType)) {
                count++;
            }

        }

        return count == 1;
    }

    /**
     * Checks if passed element is annotated with NONE of the passed annotations.
     *
     * @param element         the element to check
     * @param annotationTypes the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public static boolean isAnnotatedWithNoneOf(Element element, Class<? extends Annotation>... annotationTypes) {
        if (element == null || annotationTypes == null) {
            return false;
        }

        // convert array to set to filter doubled elements
        for (Class<? extends Annotation> annotationType : Utilities.convertArrayToSet(annotationTypes)) {

            if (isAnnotatedWith(element, annotationType)) {
                return false;
            }

        }

        return true;
    }


}