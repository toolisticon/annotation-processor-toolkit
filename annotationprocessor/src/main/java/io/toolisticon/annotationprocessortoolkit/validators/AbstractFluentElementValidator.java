package io.toolisticon.annotationprocessortoolkit.validators;


import io.toolisticon.annotationprocessortoolkit.tools.characteristicsvalidator.Validators;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * This abstract base class is used to implement validation logic that on {@link Element} level.
 * For example : Modifiers
 *
 * @param <T>
 * @param <E>
 */
public abstract class AbstractFluentElementValidator<T extends AbstractFluentElementValidator, E extends Element> extends AbstractFluentValidator<T, E> {


    public AbstractFluentElementValidator(E element) {
        super(element);
    }

    public AbstractFluentElementValidator(AbstractFluentElementValidator previousFluentExecutableElementValidator, boolean currentValidationResult) {

        super(previousFluentExecutableElementValidator, currentValidationResult);

    }


    /**
     * Checks if element under validation has all of the passed modifiers.
     *
     * @param modifiers the modifiers to check for
     * @return an immutable FluentExecutableElementValidator instance
     */
    public T hasModifiers(Modifier... modifiers) {
        boolean nextResult = getValidationResult();

        if (modifiers != null) {
            if (!Validators.MODIFIER_VALIDATOR.getValidator().hasAllOf(getElement(), modifiers)) {
                getMessagerUtils().printMessage(
                        getElement(), getMessageLevel(),
                        getCustomOrDefaultMessage("Element must have the following modifiers ${0}", getModifierString(modifiers))
                );
                nextResult = isErrorLevel() ? false : nextResult;
            }
        }

        return createNextFluentValidator(nextResult);
    }

    /**
     * Checks if element under validation has none of the passed modifiers.
     *
     * @param modifiers the modifiers to check for
     * @return an immutable FluentExecutableElementValidator instance
     */
    public T hasNotModifiers(Modifier... modifiers) {
        boolean nextResult = getValidationResult();

        if (modifiers != null) {
            if (!Validators.MODIFIER_VALIDATOR.getValidator().hasNoneOf(getElement(), modifiers)) {
                getMessagerUtils().printMessage(
                        getElement(),
                        getMessageLevel(),
                        getCustomOrDefaultMessage("Element must have the following modifiers ${0}", getModifierString(modifiers))
                );
                nextResult = isErrorLevel() ? false : nextResult;
            }
        }

        return createNextFluentValidator(nextResult);
    }

    /**
     * Private helper function to create modifier output string for message.
     *
     * @param modifiers the modifier array to be used for string representation
     * @return the string representation of the passed modifier array
     */
    private String getModifierString(Modifier[] modifiers) {

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        if (modifiers != null) {
            boolean first = true;
            for (Modifier modifier : modifiers) {

                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }

                sb.append(modifier.name());
            }
        }
        sb.append("]");
        return sb.toString();
    }


}
