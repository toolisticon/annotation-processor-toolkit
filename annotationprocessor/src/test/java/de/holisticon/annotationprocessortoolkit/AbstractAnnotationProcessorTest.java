package de.holisticon.annotationprocessortoolkit;

import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class AbstractAnnotationProcessorTest extends AbstractAnnotationProcessorTestBaseClass {

    public AbstractAnnotationProcessorTest(String message, AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {
        super(message, testcase, compilationShouldSucceed);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{

                        {
                                "TypeUtils : Get TypeElement for class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeElement typeElement = getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);

                                        MatcherAssert.assertThat(typeElement, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeElement.getSimpleName().toString(), Matchers.is(AbstractTestAnnotationProcessorClass.class.getSimpleName()));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : Get TypeMirror for class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().getTypeMirrorForClass(AbstractTestAnnotationProcessorClass.class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DECLARED));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : test isAssignableToType",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().isAssignableToType(element, Object.class));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().isAssignableToType(element, InputStream.class));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : test isAssignableToTypeElement",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().isAssignableToTypeElement(element, getTypeUtils().getTypeElementForClass(Object.class)));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().isAssignableToTypeElement(element, getTypeUtils().getTypeElementForClass(InputStream.class)));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : test isAssignableToTypeMirror",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().isAssignableToTypeMirror(element, getTypeUtils().getTypeMirrorForClass(Object.class)));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().isAssignableToTypeMirror(element, getTypeUtils().getTypeMirrorForClass(InputStream.class)));

                                    }
                                },
                                true

                        },
                        {
                                "TypeUtils : test check for void type ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().isVoidType(ElementUtils.getElementUtils().castMethod(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "synchronizedMethod").get(0)).getReturnType()), Matchers.is(true));
                                        MatcherAssert.assertThat(getTypeUtils().isVoidType(element.asType()), Matchers.is(false));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : get encapsulated javax.lang.model.util.Types instance",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().getTypes(), Matchers.notNullValue());

                                    }
                                },
                                true


                        },
                        {
                                "ElementUtils : isAnnotatedWith test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Should have detected that element is annotated with TestAnnotation annotation", ElementUtils.getElementUtils().isAnnotatedWith(element, TestAnnotation.class));
                                        MatcherAssert.assertThat("Should have detected that element is not annotated with Override annotation", !ElementUtils.getElementUtils().isAnnotatedWith(element, Override.class));

                                    }
                                },
                                true


                        },
                        {
                                "ElementUtils : getEnclosedElementsByName test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // find field
                                        List<? extends Element> result = ElementUtils.getElementUtils().getEnclosedElementsByName(element, "privateField");
                                        MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                        MatcherAssert.assertThat(result.get(0).getKind(), Matchers.is(ElementKind.FIELD));
                                        MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("privateField"));


                                        // shouldn't find nonexisting
                                        result = ElementUtils.getElementUtils().getEnclosedElementsByName(element, "XXXXXXXX");
                                        MatcherAssert.assertThat(result, Matchers.<Element>empty());

                                    }
                                },
                                true


                        },
                        {
                                "ElementUtils : getEnclosedElementsByNameRegex test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> results = ElementUtils.getElementUtils().getEnclosedElementsByNameRegex(element, ".*ublic.*");
                                        MatcherAssert.assertThat(results, Matchers.hasSize(4));
                                        for (Element result : results) {
                                            MatcherAssert.assertThat(result.getKind(), Matchers.is(ElementKind.FIELD));
                                            MatcherAssert.assertThat(result.getSimpleName().toString(), Matchers.startsWith("public"));
                                        }

                                        // shouldn't find nonexisting
                                        results = ElementUtils.getElementUtils().getEnclosedElementsByNameRegex(element, "XXXXXXXX");
                                        MatcherAssert.assertThat(results, Matchers.<Element>empty());

                                    }
                                },
                                true


                        },
                        {
                                "ElementUtils : hasModifiers test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Class should have public modifier", ElementUtils.getElementUtils().hasModifiers(element, Modifier.PUBLIC));
                                        MatcherAssert.assertThat("Class should not have abstract modifier", !ElementUtils.getElementUtils().hasModifiers(element, Modifier.PUBLIC, Modifier.ABSTRACT));

                                    }
                                },
                                true


                        },
                        {
                                "ElementUtils : hasPublicModifier test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Class should not be detected to have public modifier", ElementUtils.getElementUtils().hasPublicModifier(element));

                                    }
                                },
                                true


                        },
                        {
                                "FluentElementFilter : Do filterings",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements()).filterByKinds(ElementKind.FIELD).getResult();
                                        MatcherAssert.assertThat(result, Matchers.hasSize(7));


                                        result = createFluentElementFilter(element.getEnclosedElements()).filterByKinds(ElementKind.FIELD).filterByModifiers(Modifier.PUBLIC, Modifier.STATIC).getResult();
                                        MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                        MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));


                                    }
                                },
                                true


                        },
                        {
                                "FluentExecutableElementValidator: validate void return type method",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // check null value
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "synchronizedMethod")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasVoidReturnType().getValidationResult(), Matchers.is(true));

                                        // check non null value
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasNonVoidReturnType().getValidationResult(), Matchers.is(false));


                                        // check specific return type
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasReturnType(String.class).getValidationResult(), Matchers.is(false));


                                        getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);

                                    }
                                },
                                false


                        },
                        {
                                "FluentExecutableElementValidator: validate non void return type method",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // check null value
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasVoidReturnType().getValidationResult(), Matchers.is(false));

                                        // check non null value
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasNonVoidReturnType().getValidationResult(), Matchers.is(true));

                                        // check specific return type
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasReturnType(String.class).getValidationResult(), Matchers.is(true));

                                        // check for assignable supertype of return type
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasReturnType(Object.class).getValidationResult(), Matchers.is(true));

                                        // check specific return type
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasReturnType(Boolean.class).getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "FluentExecutableElementValidator: validate if ExecutableElement is method",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        // check for method
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().getValidationResult(), Matchers.is(true));


                                        elements = ElementUtils.getElementUtils().getEnclosedElementsOfKind(element, ElementKind.CONSTRUCTOR);
                                        MatcherAssert.assertThat("precondition : must have found unique static init block", elements.size() == 2);
                                        ExecutableElement constructorElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        // check for method
                                        MatcherAssert.assertThat(getFluentMethodValidator(constructorElement).isMethod().getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "FluentExecutableElementValidator: has name",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        // check for method
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasName("methodWithReturnTypeAndParameters").getValidationResult(), Matchers.is(true));


                                    }
                                },
                                true


                        },
                        {
                                "FluentExecutableElementValidator: has parameters",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        // check for existence of parameters
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters().getValidationResult(), Matchers.is(true));

                                        // check for existence of parameters
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters(Boolean.class, String.class).getValidationResult(), Matchers.is(true));

                                        // check non matching parameter length
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters(Boolean.class).getValidationResult(), Matchers.is(false));

                                        // check non matching parameter types
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters(String.class, Boolean.class).getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "FluentExecutableElementValidator: has / hasn't modifier",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "synchronizedMethod")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        // check for existence of parameters
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasModifiers(Modifier.SYNCHRONIZED, Modifier.PUBLIC).hasNotModifiers(Modifier.PROTECTED, Modifier.FINAL).getValidationResult(), Matchers.is(true));

                                        // check for nonexistence
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasModifiers(Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasModifiers(Modifier.SYNCHRONIZED, Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasNotModifiers(Modifier.PROTECTED, Modifier.SYNCHRONIZED).getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "FluentModifierElementValidator: has / hasn't modifier",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "synchronizedMethod")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        // check for existence of parameters
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasModifiers(Modifier.SYNCHRONIZED, Modifier.PUBLIC).hasNotModifiers(Modifier.PROTECTED, Modifier.FINAL).getValidationResult(), Matchers.is(true));

                                        // check for nonexistence
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasModifiers(Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasModifiers(Modifier.SYNCHRONIZED, Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasNotModifiers(Modifier.PROTECTED, Modifier.SYNCHRONIZED).getValidationResult(), Matchers.is(false));

                                        // Do the same on TypeElement
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasModifiers(Modifier.PUBLIC).hasNotModifiers(Modifier.FINAL, Modifier.ABSTRACT).getValidationResult(), Matchers.is(true));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasModifiers(Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasNotModifiers(Modifier.ABSTRACT, Modifier.PUBLIC).getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "FluentTypeElementValidator: has / hasn't modifier",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        MatcherAssert.assertThat(getFluentTypeValidator(element).hasModifiers(Modifier.PUBLIC).hasNotModifiers(Modifier.FINAL, Modifier.ABSTRACT).getValidationResult(), Matchers.is(true));
                                        MatcherAssert.assertThat(getFluentTypeValidator(element).hasModifiers(Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentTypeValidator(element).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                        MatcherAssert.assertThat(getFluentTypeValidator(element).hasModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentTypeValidator(element).hasNotModifiers(Modifier.ABSTRACT, Modifier.PUBLIC).getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "FluentTypeElementValidator : check if type is assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getFluentTypeValidator(element).isAssignableTo(Object.class).getValidationResult(), Matchers.is(true));
                                        MatcherAssert.assertThat(getFluentTypeValidator(element).isAssignableTo(String.class).getValidationResult(), Matchers.is(false));

                                    }
                                },
                                false


                        },
                        {
                                "FluentTypeElementValidator : check for noarg constructor",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getFluentTypeValidator(element).hasNoArgConstructor().getValidationResult(), Matchers.is(true));

                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "EmbeddedClass")).filterByKinds(ElementKind.CLASS).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        TypeElement _2ndTestElement = ElementUtils.getElementUtils().castToTypeElement(elements.get(0));

                                        MatcherAssert.assertThat(getFluentTypeValidator(_2ndTestElement).hasNoArgConstructor().getValidationResult(), Matchers.is(true));

                                        elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "EmbeddedClassWithNoNoargConstructor")).filterByKinds(ElementKind.CLASS).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        TypeElement _3rdTestElement = ElementUtils.getElementUtils().castToTypeElement(elements.get(0));

                                        MatcherAssert.assertThat(getFluentTypeValidator(_3rdTestElement).hasNoArgConstructor().getValidationResult(), Matchers.is(false));


                                    }
                                },
                                true


                        },
                        {
                                "",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);


                                    }
                                },
                                true


                        },
                        {
                                "",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);

                                    }
                                },
                                true


                        },
                        {
                                "",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeElement typeElement = getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);


                                    }
                                },
                                true


                        }

                }

        );


    }


}
