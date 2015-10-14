package org.sega.viewer.services.support;

import org.json.JSONObject;
import org.sega.viewer.models.support.ValueType;

/**
 * @author Raysmond<i@raysmond.com>.
 */
public class AttributeType {
    private String id;
    private String text;
    private String type;
    private ValueType valueType;
    private boolean isMapped;
    private String mappedType;

    public AttributeType() {

    }

    public AttributeType(JSONObject attributeJson) {
        this.setId(attributeJson.getString("id"));
        this.setText(attributeJson.getString("text"));
        this.setType(attributeJson.getString("type"));

        JSONObject data = attributeJson.getJSONObject("data");
        if (data != null) {
            this.setStringValueType(data.optString("value_type"));
            this.setIsMapped(data.optBoolean("isMapped"));
            this.setMappedType(data.optString("mapped_type"));
        }
    }

    public AttributeType(String id, String text, String type, String valueType, boolean isMapped, String mappedType) {
        this.id = id;
        this.text = text;
        this.isMapped = isMapped;
        this.mappedType = mappedType;
        this.type = type;

        this.setStringValueType(valueType);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setStringValueType(String valueType) {
        if (valueType == null || valueType.isEmpty()) {
            this.valueType = ValueType.STRING;
            return;
        }
        switch (valueType) {
            case "auto":
                // 默认auto的类型为String
                this.valueType = ValueType.STRING;

                if (isMapped) {
                    if (mappedType.startsWith("int")) {
                        this.valueType = ValueType.LONG;
                    }

                    if (mappedType.startsWith("varchar")) {
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
