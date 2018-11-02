package io.toolisticon.annotationprocessortoolkit.tools.command.impl;


import io.toolisticon.annotationprocessortoolkit.testhelper.compiletest.CompileTestBuilder;
import io.toolisticon.annotationprocessortoolkit.testhelper.compiletest.JavaFileObjectUtils;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.BeanUtils;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.TypeElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit Test for {@link GetAttributesCommandWithInheritance}.
 */
public class GetAttributesCommandWithInheritanceTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .createCompileTestBuilder()
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/testcases.commands/GetAttributesCommandWithInheritanceTestClass.java"));

    @Test
    public void shouldExecuteSuccessfullyDataAnnotatedClass() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestDataAnnotatedClass")
                                .getResult().get(0);
                        BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                        MatcherAssert.assertThat(attributeResult.length, Matchers.is(1));
                        MatcherAssert.assertThat(attributeResult[0].getFieldName(), Matchers.is("field1"));

                    }
                })

                .compilationShouldSucceed()
                .testCompilation();

    }

    @Test
    public void shouldExecuteSuccessfullyInheritedDataAnnotatedClass() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestInheritedDataAnnotatedClass")
                                .getResult().get(0);
                        BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                        MatcherAssert.assertThat(attributeResult.length, Matchers.is(2));

                        Set<String> fields = new HashSet<String>();
                        for (BeanUtils.AttributeResult item : attributeResult) {
                            fields.add(item.getFieldName());
                        }

                        MatcherAssert.assertThat(fields, Matchers.containsInAnyOrder("field1", "field3"));

                    }
                })

                .compilationShouldSucceed()
                .testCompilation();

    }

    @Test
    public void shouldExecuteSuccessfullyGetterAnnotatedClass() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {


                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestJustGetterAnnotatedClass")
                                .getResult().get(0);

                        BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                        MatcherAssert.assertThat(attributeResult.length, Matchers.is(0));

                    }
                })

                .compilationShouldSucceed()
                .testCompilation();

    }

    @Test
    public void shouldExecuteSuccessfullySetterAnnotatedClass() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestJustSetterAnnotatedClass")
                                .getResult().get(0);


                        BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                        MatcherAssert.assertThat(attributeResult.length, Matchers.is(0));

                    }
                })

                .compilationShouldSucceed()
                .testCompilation();

    }


    @Test
    public void shouldExecuteSuccessfullyGetterAndSetterAnnotatedClass() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {


                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestGetterAndSetterAnnotatedClass")
                                .getResult().get(0);


                        BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                        MatcherAssert.assertThat(attributeResult.length, Matchers.is(1));
                        MatcherAssert.assertThat(attributeResult[0].getFieldName(), Matchers.is("field1"));

                    }
                })

                .compilationShouldSucceed()
                .testCompilation();

    }


    @Test
    public void shouldExecuteSuccessfullyMixedGetterAndSetterAnnotatedClassAndField2() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestMixedGetterAndSetterAnnotatedClassAndField1")
                                .getResult().get(0);

                        BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                        MatcherAssert.assertThat(attributeResult.length, Matchers.is(1));
                        MatcherAssert.assertThat(attributeResult[0].getFieldName(), Matchers.is("field1"));

                    }
                })

                .compilationShouldSucceed()
                .testCompilation();

    }


    @Test
    public void shouldExecuteSuccessfullyMixedGetterAndSetterAnnotatedClassAndField2_() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestMixedGetterAndSetterAnnotatedClassAndField2")
                                .getResult().get(0);

                        BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                        MatcherAssert.assertThat(attributeResult.length, Matchers.is(1));
                        MatcherAssert.assertThat(attributeResult[0].getFieldName(), Matchers.is("field1"));

                    }
                })

                .compilationShouldSucceed()
                .testCompilation();

    }


    @Test
    public void shouldExecuteSuccessfullyGetterAnnotatedField() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestJustGetterAnnotatedField")
                                .getResult().get(0);

                        BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                        MatcherAssert.assertThat(attributeResult.length, Matchers.is(0));

                    }
                })

                .compilationShouldSucceed()
                .testCompilation();

    }

    @Test
    public void shouldExecuteSuccessfullySetterAnnotatedField() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {


                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestJustSetterAnnotatedField")
                                .getResult().get(0);

                        BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                        MatcherAssert.assertThat(attributeResult.length, Matchers.is(0));

                    }
                })

                .compilationShouldSucceed()
                .testCompilation();

    }

    @Test
    public void shouldExecuteSuccessfullyGetterAndSetterAnnotatedField() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestGetterAndSetterAnnotatedField")
                                .getResult().get(0);

                        BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                        MatcherAssert.assertThat(attributeResult.length, Matchers.is(1));
                        MatcherAssert.assertThat(attributeResult[0].getFieldName(), Matchers.is("field1"));

                    }
                })

                .compilationShouldSucceed()
                .testCompilation();

    }

    @Test
    public void shouldExecuteSuccessfullyGetterAndSetterAnnotatedMethod() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestFieldGetterAndSetterMethods")
                                .getResult().get(0);

                        BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                        MatcherAssert.assertThat(attributeResult.length, Matchers.is(1));
                        MatcherAssert.assertThat(attributeResult[0].getFieldName(), Matchers.is("field1"));

                    }
                })

                .compilationShouldSucceed()
                .testCompilation();

    }

    @Test
    public void shouldExecuteSuccessfullyGetterAndSetterAnnotatedMethodWithInvalidParameterType() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestFieldGetterAndSetterMethodsWithInvalidSetterParameterType")
                                .getResult().get(0);

                        BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                        MatcherAssert.assertThat(attributeResult.length, Matchers.is(0));

                    }
                })

                .compilationShouldSucceed()
                .testCompilation();

    }


}
