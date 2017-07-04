package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.tools.generics.GenericType;
import de.holisticon.annotationprocessortoolkit.tools.generics.GenericTypeType;
import de.holisticon.annotationprocessortoolkit.tools.generics.GenericTypeWildcard;

import javax.lang.model.element.Element;

/**
 * A matcher for generic types.
 */
public class GenericTypeMatcher extends GenericMatcherWithToolsSupport<GenericType> {

    public GenericTypeMatcher(FrameworkToolWrapper tools) {
        super(tools);
    }


    @Override
    public boolean checkForMatchingCharacteristic(Element element, GenericType toCheckFor) {

        return (element != null && toCheckFor != null) && typeUtils.GENERICS.genericTypeEquals(element.asType(), toCheckFor);

    }

    @Override
    public String getStringRepresentationOfPassedCharacteristic(GenericType toGetStringRepresentationFor) {

        if (toGetStringRepresentationFor == null) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();

        createStringRepresentationRecursively(stringBuilder, toGetStringRepresentationFor);

        return stringBuilder.toString();

    }

    private void createStringRepresentationRecursively(StringBuilder stringBuilder, GenericType toGetStringRepresentationFor) {


        stringBuilder.append(typeUtils.getTypes().erasure(toGetStringRepresentationFor.getRawType()).toString());


        // Check type parameters
        if (toGetStringRepresentationFor.typeParameters.length > 0) {

            stringBuilder.append("<");


            boolean first = true;
            // Now check type parameters recursively
            for (int i = 0; i < toGetStringRepresentationFor.getTypeParameters().length; i++) {

                if (first) {
                    first = false;
                } else {
                    stringBuilder.append(", ");
                }

                GenericTypeType currentGenericTypeType = toGetStringRepresentationFor.getTypeParameters()[i];

                switch (currentGenericTypeType.getType()) {

                    case WILDCARD:

                        GenericTypeWildcard wildcard = (GenericTypeWildcard) currentGenericTypeType;

                        if (wildcard.isPureWildcard()) {

                            stringBuilder.append("?");

                        } else if (wildcard.hasExtendsBound()) {

                            stringBuilder.append("? extends ");

                            createStringRepresentationRecursively(stringBuilder, wildcard.getExtendsBound());


                        } else if (wildcard.hasSuperBound()) {

                            stringBuilder.append("? super ");

                            createStringRepresentationRecursively(stringBuilder, wildcard.getSuperBound());

                        }

                        break;


                    case DECLARED_TYPE:
                        createStringRepresentationRecursively(stringBuilder, (GenericType) currentGenericTypeType);
                        break;

                }


            }
            stringBuilder.append(">");
        }


    }


}