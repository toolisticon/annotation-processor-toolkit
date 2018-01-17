package io.toolisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import io.toolisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericType;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Unit test for {@link GenericTypeMatcher}.
 */
@RunWith(Parameterized.class)
public class GenericTypeMatcherTest extends AbstractAnnotationProcessorUnitTest {

    public GenericTypeMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "getStringRepresentationOfPassedCharacteristic : create String representation correctl",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {

                                                        TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                        GenericType genericTypeToConvert = typeUtils.doGenerics().createGenericType(Map.class,
                                                                typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                                        typeUtils.doGenerics().createGenericType(StringBuilder.class)
                                                                ),
                                                                typeUtils.doGenerics().createGenericType(
                                                                        Comparator.class,
                                                                        typeUtils.doGenerics().createWildcardWithSuperBound(
                                                                                typeUtils.doGenerics().createGenericType(
                                                                                        List.class,
                                                                                        typeUtils.doGenerics().createPureWildcard()
                                                                                )
                                                                        )

                                                                )
                                                        );


                                                        MatcherAssert.assertThat(Matchers.GENERIC_TYPE_MATCHER.getMatcher().getStringRepresentationOfPassedCharacteristic(genericTypeToConvert), org.hamcrest.Matchers.is("java.util.Map<? extends java.lang.StringBuilder, java.util.Comparator<? super java.util.List<?>>>"));


                                                    }

                                                }
                                        )
                                        .build()


                        },

                        {
                                "checkForMatchingCharacteristic : Should be able to compare generic type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(Filters.NAME_FILTER).filterByOneOf("testGenericsOnParameter").getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              GenericType genericType = typeUtils.doGenerics().createGenericType(Map.class,
                                                                      typeUtils.doGenerics().createGenericType(String.class),
                                                                      typeUtils.doGenerics().createGenericType(
                                                                              Comparator.class,
                                                                              typeUtils.doGenerics().createGenericType(Long.class)
                                                                      )
                                                              );

                                                              MatcherAssert.assertThat("Should compare successful", Matchers.GENERIC_TYPE_MATCHER.getMatcher().checkForMatchingCharacteristic(method.getParameters().get(0), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "checkForMatchingCharacteristic : Should not be able to compare generic type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(Filters.NAME_FILTER).filterByOneOf("testGenericsOnParameter").getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              GenericType genericType = typeUtils.doGenerics().createGenericType(Map.class,
                                                                      typeUtils.doGenerics().createGenericType(String.class),
                                                                      typeUtils.doGenerics().createGenericType(
                                                                              Comparator.class,
                                                                              typeUtils.doGenerics().createGenericType(Double.class)
                                                                      )

                                                              );

                                                              MatcherAssert.assertThat("Should not compare successful", !Matchers.GENERIC_TYPE_MATCHER.getMatcher().checkForMatchingCharacteristic(method.getParameters().get(0), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "checkForMatchingCharacteristic : Should be able to compare generic type with wildcards",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();


                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(Filters.NAME_FILTER).filterByOneOf("testGenericsOnParameter").getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              //  Map<? extends StringBuilder, Comparator<? super List<?>>>

                                                              GenericType genericType = typeUtils.doGenerics().createGenericType(Map.class,
                                                                      typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                                              typeUtils.doGenerics().createGenericType(StringBuilder.class)
                                                                      ),
                                                                      typeUtils.doGenerics().createGenericType(
                                                                              Comparator.class,
                                                                              typeUtils.doGenerics().createWildcardWithSuperBound(
                                                                                      typeUtils.doGenerics().createGenericType(
                                                                                              List.class,
                                                                                              typeUtils.doGenerics().createPureWildcard()
                                                                                      )
                                                                              )

                                                                      )
                                                              );

                                                              MatcherAssert.assertThat("Should compare successful", Matchers.GENERIC_TYPE_MATCHER.getMatcher().checkForMatchingCharacteristic(method.getParameters().get(1), genericType));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "checkForMatchingCharacteristic : Should not be able to compare generic type with wildcards",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(Filters.NAME_FILTER).filterByOneOf("testGenericsOnParameter").getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              //  Map<? extends StringBuilder, Comparator<? super List<?>>>


                                                              GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                                      Map.class,
                                                                      typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                                              typeUtils.doGenerics().createGenericType(StringBuilder.class)
                                                                      ),
                                                                      typeUtils.doGenerics().createGenericType(
                                                                              Comparator.class,
                                                                              typeUtils.doGenerics().createWildcardWithSuperBound(
                                                                                      typeUtils.doGenerics().createGenericType(
                                                                                              List.class,
                                                                                              typeUtils.doGenerics().createWildcardWithExtendsBound(typeUtils.doGenerics().createGenericType(String.class))
                                                                                      )
                                                                              )

                                                                      )
                                                              );

                                                              MatcherAssert.assertThat("Should not compare successful", !Matchers.GENERIC_TYPE_MATCHER.getMatcher().checkForMatchingCharacteristic(method.getParameters().get(1), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },


                }

        );


    }

    @Test
    public void test() {
        super.test();
    }

}
