package de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matcher;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;

/**
 * Utility class for accessing validators.
 */
public class Validator<T> {

    public final static Validator<Modifier> MODIFIER_VALIDATOR = new Validator<Modifier>(new GenericElementCharacteristicValidator<Modifier>(Matcher.MODIFIER_MATCHER));
    public final static Validator<String> NAME_VALIDATOR = new Validator<String>(new GenericElementCharacteristicValidator<String>(Matcher.NAME_MATCHER));
    public final static Validator<String> REGEX_NAME_VALIDATOR = new Validator<String>(new GenericElementCharacteristicValidator<String>(Matcher.REGEX_NAME_MATCHER));
    public final static Validator<Class<? extends Annotation>> ANNOTATION_VALIDATOR = new Validator<Class<? extends Annotation>>(new GenericElementCharacteristicValidator<Class<? extends Annotation>>(Matcher.ANNOTATION_MATCHER));
    public final static Validator<ElementKind> ELEMENT_KIND_VALIDATOR = new Validator<ElementKind>(new GenericElementCharacteristicValidator<ElementKind>(Matcher.ELEMENT_KIND_MATCHER));


    private final GenericElementCharacteristicValidator<T> validator;


    /**
     * Hidden constructor.
     */
    private Validator(GenericElementCharacteristicValidator<T> validator) {
        this.validator = validator;
    }


    public GenericElementCharacteristicValidator<T> getValidator() {
        return validator;
    }

    /**
     * Convenience method for getting and using a Modifier matching validator.
     *
     * @return the validator instance
     */
    public static InclusiveElementValidator<Modifier> getModifierValidator() {
        return MODIFIER_VALIDATOR.getValidator();
    }

    /**
     * Convenience method for getting and using a name matching validator.
     *
     * @return the validator instance
     */
    public static ExclusiveElementValidator<String> getNameValidator() {
        return NAME_VALIDATOR.getValidator();
    }

    /**
     * Convenience method for getting and using a name matching validator by regular expressions.
     *
     * @return the validator instance
     */
    public static InclusiveElementValidator<String> getRegexNameValidator() {
        return REGEX_NAME_VALIDATOR.getValidator();
    }

    /**
     * Convenience method for getting and using an annotation matching validator.
     *
     * @return the validator instance
     */
    public static InclusiveElementValidator<Class<? extends Annotation>> getAnnotationValidator() {
        return ANNOTATION_VALIDATOR.getValidator();
    }

    /**
     * Convenience method for getting and using a ElementKind matching validator.
     *
     * @return the validator instance
     */
    public static ExclusiveElementValidator<ElementKind> getElementKindValidator() {
        return ELEMENT_KIND_VALIDATOR.getValidator();
    }

}