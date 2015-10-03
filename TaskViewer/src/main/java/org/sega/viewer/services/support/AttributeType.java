package org.sega.viewer.services.support;

import org.sega.viewer.models.support.ValueType;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
public class AttributeType {
    private String id;
    private String text;
    private ValueType valueType;
    private boolean isMapped;
    private String mappedType;

    public AttributeType(String id, String text, String valueType, boolean isMapped, String mappedType) {
        this.id = id;
        this.text = text;
        this.isMapped = isMapped;
        this.mappedType = mappedType;

        this.setStringValueType(valueType);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public void setStringValueType(String valueType){
        switch (valueType){
            case "auto":
                // 默认auto的类型为String
                this.valueType = ValueType.STRING;

                if (isMapped){
                    if (mappedType.startsWith("int")){
                        this.valueType = ValueType.LONG;
                    }

                    if (mappedType.startsWith("varchar")){
                        this.valueType = ValueType.STRING;
                    }
                }

                break;
            case "number":
                this.valueType = ValueType.LONG;
                break;
            case "string-line":
                this.valueType = ValueType.STRING;
                break;
            case "string-textarea":
                this.valueType = ValueType.TEXT;
                break;
            case "file":
                this.valueType = ValueType.FILE;
                break;
            default:
        }
    }

    public boolean isMapped() {
        return isMapped;
    }

    public void setIsMapped(boolean isMapped) {
        this.isMapped = isMapped;
    }

    public String getMappedType() {
        return mappedType;
    }

    public void setMappedType(String mappedType) {
        this.mappedType = mappedType;
    }
}
