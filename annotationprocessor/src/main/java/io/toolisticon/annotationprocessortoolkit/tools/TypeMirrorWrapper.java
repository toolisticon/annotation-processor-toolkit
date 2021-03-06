package io.toolisticon.annotationprocessortoolkit.tools;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This is a convenience wrapper for TypeMirrors.
 * This is extremely useful for template based code generation and doing programatical checks.
 */
public class TypeMirrorWrapper {

    private final TypeMirror typeMirror;

    /**
     * Constructor.
     *
     * @param typeMirror the TypeMirror instance to wrap - must not be null
     * @throws IllegalArgumentException if passed typeMirror is null.
     */
    TypeMirrorWrapper(TypeMirror typeMirror) {
        if (typeMirror == null) {
            throw new IllegalArgumentException("Passed typeMirror must not be null");
        }
        this.typeMirror = typeMirror;
    }

    /**
     * Checks if wrapped TypeMirror is a primitive type.
     *
     * @return true if wrapped TypeMirror is primitive, otherwise false
     */
    public boolean isPrimitive() {
        return isPrimitive(typeMirror);
    }

    /**
     * Checks if wrapped TypeMirror is a primitive type.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if wrapped TypeMirror is primitive, otherwise false
     */
    public static boolean isPrimitive(TypeMirror typeMirror) {
        return TypeUtils.CheckTypeKind.isPrimitive(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a PrimitiveType
     *
     * @return the wrapped TypeMirror cast to a PrimitiveType, or null if TypeMirror does not represent a primitive type.
     */
    public PrimitiveType getPrimitiveType() {
        return getPrimitiveType(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a PrimitiveType
     *
     * @param typeMirror the TypeMirror to check
     * @return the wrapped TypeMirror cast to a PrimitiveType, or null if TypeMirror does not represent a primitive type.
     */
    public static PrimitiveType getPrimitiveType(TypeMirror typeMirror) {
        return isPrimitive(typeMirror) ? (PrimitiveType) typeMirror : null;
    }

    /**
     * Checks if wrapped TypeMirror is an array.
     *
     * @return true if wrapped TypeMirror is an array, otherwise false
     */
    public boolean isArray() {
        return isArray(typeMirror);
    }

    /**
     * Checks if wrapped TypeMirror is an array.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if wrapped TypeMirror is an array, otherwise false
     */
    public static boolean isArray(TypeMirror typeMirror) {
        return TypeUtils.CheckTypeKind.isArray(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a ArrayType
     *
     * @return the wrapped TypeMirror cast to a ArrayType, or null if ArrayType does not represent an array type.
     */
    public ArrayType getArrayType() {
        return getArrayType(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a ArrayType
     *
     * @param typeMirror the TypeMirror to check
     * @return the wrapped TypeMirror cast to an ArrayType, or null if TypeMirror does not represent an array type.
     */
    public static ArrayType getArrayType(TypeMirror typeMirror) {
        return isArray(typeMirror) ? (ArrayType) typeMirror : null;
    }

    /**
     * Checks if wrapped TypeMirror is a DeclaredType.
     *
     * @return true if wrapped TypeMirror is a DeclaredType, otherwise false
     */
    public boolean isDeclared() {
        return isDeclared(typeMirror);
    }

    /**
     * Checks if wrapped TypeMirror is a DeclaredType.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if wrapped TypeMirror is a DeclaredType, otherwise false
     */
    public static boolean isDeclared(TypeMirror typeMirror) {
        return TypeUtils.CheckTypeKind.isDeclared(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a DeclaredType
     *
     * @return the wrapped TypeMirror cast to a DeclaredType, or null if TypeMirror does not represent a declared type.
     */
    public DeclaredType getDeclaredType() {
        return getDeclaredType(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a DeclaredType
     *
     * @param typeMirror the TypeMirror to check
     * @return the wrapped TypeMirror cast to a DeclaredType, or null if TypeMirror does not represent a declared type.
     */
    public static DeclaredType getDeclaredType(TypeMirror typeMirror) {
        return isDeclared(typeMirror) ? (DeclaredType) typeMirror : null;
    }

    /**
     * Checks if wrapped TypeMirror is a Wildcard.
     *
     * @return true if wrapped TypeMirror is a Wildcard, otherwise false
     */
    public boolean isWildcardType() {
        return isWildcardType(typeMirror);
    }

    /**
     * Checks if wrapped TypeMirror is a DeclaredType.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if wrapped TypeMirror is a DeclaredType, otherwise false
     */
    public static boolean isWildcardType(TypeMirror typeMirror) {
        return TypeUtils.CheckTypeKind.isWildcard(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a DeclaredType
     *
     * @return the wrapped TypeMirror cast to a DeclaredType, or null if TypeMirror does not represent a declared type.
     */
    public WildcardType getWildcardType() {
        return getWildcardType(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a DeclaredType
     *
     * @param typeMirror the TypeMirror to check
     * @return the wrapped TypeMirror cast to a DeclaredType, or null if TypeMirror does not represent a declared type.
     */
    public static WildcardType getWildcardType(TypeMirror typeMirror) {
        return isWildcardType(typeMirror) ? (WildcardType) typeMirror : null;
    }

    /**
     * Gets the ComponentType of TypeMirror representing an array
     *
     * @return The component TypeMirror when passed typeMirror represents an array, otherwise null.
     */
    public TypeMirror getComponentType() {
        return getComponentType(typeMirror);
    }

    /**
     * Gets the ComponentType of TypeMirror representing an array
     *
     * @param typeMirror the TypeMirror to check
     * @return The component TypeMirror when passed typeMirror represents an array, otherwise null.
     */
    public static TypeMirror getComponentType(TypeMirror typeMirror) {
        return isArray(typeMirror) ? ((ArrayType) typeMirror).getComponentType() : null;
    }

    /**
     * Check if wrapped TypeMirror has TypeArguments
     *
     * @return true if wrapped TypeMirror is a DeclaredType and has type arguments, otherwise false.
     */
    public boolean hasTypeArguments() {
        return hasTypeArguments(typeMirror);
    }

    /**
     * Check if passed TypeMirror has TypeArguments
     *
     * @param typeMirror the TypeMirror to check
     * @return true if passed TypeMirror is a DeclaredType and has type arguments, otherwise false.
     */
    public static boolean hasTypeArguments(TypeMirror typeMirror) {
        return isDeclared(typeMirror) && getDeclaredType(typeMirror).getTypeArguments().size() > 0;
    }

    /**
     * Gets the package of the wrapped TypeMirror
     *
     * @return the qualified package name if wrapped TypeMirror is a DeclaredType or an array, otherwise null;
     */
    public String getPackage() {
        return getPackage(typeMirror);
    }

    /**
     * Gets the package of the passed TypeMirror
     *
     * @param typeMirror the TypeMirror to check
     * @return the qualified package name if passed TypeMirror is a DeclaredType or an array, otherwise null;
     */
    public static String getPackage(TypeMirror typeMirror) {

        if (isArray(typeMirror)) {
            return getPackage(new TypeMirrorWrapper(typeMirror).getComponentType());
        }

        if (isDeclared(typeMirror)) {
            DeclaredType declaredType = (DeclaredType) typeMirror;

            // get enclosing Package
            return ElementUtils.AccessEnclosingElements.<PackageElement>getFirstEnclosingElementOfKind(declaredType.asElement(), ElementKind.PACKAGE).getQualifiedName().toString();

        }

        // return null for primitive type mirrors and all other kinds
        return null;
    }

    /**
     * Gets the qualified name of the wrapped TypeMirror.
     *
     * @return the qualified name of the TypeMirror if wrapped TypeMirror is a DeclaredType or the qualified name of the component type if wrapped TypeMirror is an Array or the simple name if for primitive types, otherwise null.
     */
    public String getQualifiedName() {
        return getQualifiedName(typeMirror);
    }

    /**
     * Gets the qualified name of the passed TypeMirror.
     *
     * @param typeMirror the TypeMirror to check
     * @return the qualified name of the TypeMirror if wrapped TypeMirror is a DeclaredType or the qualified name of the component type if wrapped TypeMirror is an Array or the simple name if for primitive types, otherwise null.
     */
    public static String getQualifiedName(TypeMirror typeMirror) {
        if (isDeclared(typeMirror)) {
            return ((TypeElement) (getDeclaredType(typeMirror).asElement())).getQualifiedName().toString();
        } else if (isArray(typeMirror)) {
            return getQualifiedName(getArrayType(typeMirror).getComponentType());
        } else if (isPrimitive(typeMirror)) {
            return typeMirror.toString();
        }

        return null;
    }

    /**
     * Gets the simple name of the wrapped TypeMirror.
     *
     * @return the simple name if passed TypeMirror is a DeclaredType or Primitive, the component types simple name for arrays, otherwise null.
     */
    public String getSimpleName() {
        return getSimpleName(typeMirror);
    }

    /**
     * Gets the simple name of the passed TypeMirror.
     *
     * @param typeMirror the TypeMirror to check
     * @return the simple name if passed TypeMirror is a DeclaredType or Primitive, the component types simple name for arrays, otherwise null.
     */
    public static String getSimpleName(TypeMirror typeMirror) {

        if (isDeclared(typeMirror)) {
            return ((TypeElement) (getDeclaredType(typeMirror).asElement())).getSimpleName().toString();
        } else if (isArray(typeMirror)) {
            return getSimpleName(getArrayType(typeMirror).getComponentType());
        } else if (isPrimitive(typeMirror)) {
            return typeMirror.toString();
        }

        return null;
    }

    public String getTypeDeclaration() {
        return getTypeDeclaration(typeMirror);
    }

    public static String getTypeDeclaration(TypeMirror typeMirror) {

        if (isPrimitive(typeMirror)) {
            return typeMirror.toString();
        } else if (isArray(typeMirror)) {
            return getTypeDeclaration(getComponentType(typeMirror)) + "[]";
        } else if (isDeclared(typeMirror)) {

            /*-
            String typeMirrorString = typeMirror.toString();
            String packageName = getPackage(typeMirror);

            // remove package
            return typeMirrorString.startsWith(packageName) ? typeMirrorString.substring(packageName.length() + 1) : typeMirrorString;
            */

            DeclaredType declaredType = getDeclaredType(typeMirror);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getSimpleName(typeMirror));

            if (hasTypeArguments(typeMirror)) {

                stringBuilder.append("<");

                boolean first = true;
                for (TypeMirror typeArgument : declaredType.getTypeArguments()) {

                    if (first) {
                        first = false;
                    } else {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(getTypeDeclaration(typeArgument));

                }

                stringBuilder.append(">");

            }

            return stringBuilder.toString();
        } else if (isWildcardType(typeMirror)) {
            WildcardType wildcardType = getWildcardType(typeMirror);
            if (wildcardType.getSuperBound() != null) {
                return "? super " + getTypeDeclaration(wildcardType.getSuperBound());
            } else if (wildcardType.getExtendsBound() != null) {
                return "? extends " + getTypeDeclaration(wildcardType.getExtendsBound());
            } else {
                return "?";
            }
        }

        return "";
    }

    public String getTypeInitializationWithDiamondOperator() {
        return getTypeInitializationWithDiamondOperator(typeMirror);
    }

    public static String getTypeInitializationWithDiamondOperator(TypeMirror typeMirror) {
        return getSimpleName(typeMirror) + (hasTypeArguments(typeMirror) ? "<>" : "");
    }

    public Set<String> getImports() {
        return getImports(typeMirror);
    }

    public static Set<String> getImports(TypeMirror typeMirror) {

        // null value
        if (typeMirror == null) {
            return Collections.EMPTY_SET;
        }

        // array : must check component type
        if (isArray(typeMirror)) {
            return getImports(((ArrayType) typeMirror).getComponentType());
        }

        // primitive : no need for imports
        if (isPrimitive(typeMirror)) {
            return Collections.EMPTY_SET;
        }

        // wildcard type argument#
        if (TypeUtils.CheckTypeKind.isOfTypeKind(typeMirror, TypeKind.WILDCARD)) {
            WildcardType wildcardType = (WildcardType) typeMirror;
            Set<String> result = new HashSet<>();

            result.addAll(getImports(wildcardType.getExtendsBound()));
            result.addAll(getImports(wildcardType.getSuperBound()));

            return result;
        }

        // declared type : must add imports for both type and type parameters
        if (TypeUtils.CheckTypeKind.isDeclared(typeMirror)) {
            Set<String> result = new HashSet<>();

            // get enclosing Package
            String packageName = getPackage(typeMirror);

            if (packageName != null && !"java.lang".equals(packageName)) {
                result.add(getQualifiedName(typeMirror));
            }

            if (hasTypeArguments(typeMirror)) {
                for (TypeMirror argumentsTypeMirror : getDeclaredType(typeMirror).getTypeArguments()) {
                    result.addAll(getImports(argumentsTypeMirror));
                }
            }

            return result;
        }

        return Collections.EMPTY_SET;
    }

    public TypeElement getTypeElement() {
        return getTypeElement(typeMirror);
    }

    public static TypeElement getTypeElement(TypeMirror typeMirror) {
        if (typeMirror != null && isDeclared(typeMirror)) {
            return (TypeElement) getDeclaredType(typeMirror).asElement();
        }
        return null;
    }

    public static TypeMirrorWrapper wrap(TypeMirror typeMirror) {
        return new TypeMirrorWrapper(typeMirror);
    }
}
