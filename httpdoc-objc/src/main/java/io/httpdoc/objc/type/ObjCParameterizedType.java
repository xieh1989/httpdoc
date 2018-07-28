package io.httpdoc.objc.type;

import java.util.Set;
import java.util.TreeSet;

/**
 * ObjC 参数化类型
 *
 * @author 杨昌沛 646742615@qq.com
 * @date 2018-07-27 14:04
 **/
public class ObjCParameterizedType extends ObjCType {
    private ObjCClass rawType;
    private ObjCType[] actualTypeArguments;

    ObjCParameterizedType() {
    }

    public ObjCParameterizedType(ObjCClass rawType, ObjCType... actualTypeArguments) {
        this.rawType = rawType;
        this.actualTypeArguments = actualTypeArguments;
    }

    @Override
    public String getName() {
        StringBuilder builder = new StringBuilder();
        builder.append(rawType.getName());
        builder.append("<");
        for (int i = 0; i < actualTypeArguments.length; i++) {
            if (i > 0) builder.append(", ");
            ObjCType actualTypeArgument = actualTypeArguments[i];
            builder.append(actualTypeArgument.getName());
            if (!actualTypeArgument.isPrimitive() && !actualTypeArgument.isTypedef()) builder.append(" *");
        }
        builder.append(">");
        return builder.toString();
    }

    @Override
    public boolean isPrimitive() {
        return rawType.isPrimitive();
    }

    @Override
    public boolean isTypedef() {
        return rawType.isTypedef();
    }

    @Override
    public String getReferenceType() {
        return rawType.getReferenceType();
    }

    @Override
    public Set<String> imports() {
        Set<String> imports = new TreeSet<>();
        imports.addAll(rawType.imports());
        for (ObjCType type : actualTypeArguments) imports.addAll(type.imports());
        return imports;
    }

    public ObjCClass getRawType() {
        return rawType;
    }

    ObjCParameterizedType setRawType(ObjCClass rawType) {
        this.rawType = rawType;
        return this;
    }

    public ObjCType[] getActualTypeArguments() {
        return actualTypeArguments;
    }

    ObjCParameterizedType setActualTypeArguments(ObjCType[] actualTypeArguments) {
        this.actualTypeArguments = actualTypeArguments;
        return this;
    }

}