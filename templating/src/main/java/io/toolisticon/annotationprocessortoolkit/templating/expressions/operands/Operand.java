package io.toolisticon.annotationprocessortoolkit.templating.expressions.operands;


public abstract class Operand<T> {



    public Operand() {


    }



    public abstract OperandType getOperandType();


    /**
     * Can be used to get the java type of the operand.
     *
     * @return the java type of the operand
     */
    public abstract Class<? extends T> getOperandsJavaType();


    /**
     * Gets the value of the operand
     *
     * @return
     */
    public abstract T value();


    /**
     * Gets a String representation of all operand types of the passed Operands array.
     *
     * @param operandsArray the operands array to build the string representation for
     * @return a string representation of the passed operand arrays java types
     */
    public static String getStringRepresentationOfOperandsJavaTypes(Operand[] operandsArray) {
        StringBuilder stringBuilder = new StringBuilder("[");

        boolean first = true;
        if (operandsArray != null) {

            for (Operand operand : operandsArray) {
                if (first) {
                    first = false;
                } else {
                    stringBuilder.append(", ");
                }

                stringBuilder.append(operand.getOperandsJavaType().getSimpleName());

            }

        }

        stringBuilder.append("]");
        return stringBuilder.toString();

    }


}
