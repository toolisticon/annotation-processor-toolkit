package io.toolisticon.annotationprocessortoolkit.tools.command.impl;

import io.toolisticon.annotationprocessortoolkit.tools.BeanUtils;
import io.toolisticon.annotationprocessortoolkit.tools.BeanUtils.AttributeResult;
import io.toolisticon.annotationprocessortoolkit.tools.command.CommandWithReturnType;

import javax.lang.model.element.TypeElement;

/**
 * Get all attributes of  passed TypeElement.
 * attribute = field with adequate getter and setter method
 */
public class GetAttributesCommand implements CommandWithReturnType<TypeElement, AttributeResult[]> {

    public final static GetAttributesCommand INSTANCE = new GetAttributesCommand();

    @Override
    public AttributeResult[] execute(TypeElement element) {

        return BeanUtils.getAttributes(element);

    }


}
