package org.sega.viewer.services.support;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sega.viewer.utils.Base64Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raysmond<i@raysmond.com>.
 */
public class EntityJsonService {
    private JSONObject entityJson;

    public EntityJsonService(String entityJson) {
        try {
            String json = Base64Util.decode(entityJson);
            this.entityJson = new JSONArray(json).getJSONObject(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<AttributeType> parseAttributes(JSONArray children){
        List<AttributeType> attributeTypes = new ArrayList<>();

        for (int i = 0; i < children.length(); i++) {
            JSONObject child = children.getJSONObject(i);

            switch (child.getString("type")){
                case "artifact":
                    break;
                case "key":
                    break;
                case "attribute":
                    AttributeType attributeType = new AttributeType(
                            child.getString("id"),
                            child.getString("text"),
                            child.getString("value_type"),
                            child.getBoolean("isMapped"),
                            child.getString("mapped_type")
                    );

                    attributeTypes.add(attributeType);
                    break;
                case "group":
                    break;
                case "artifact_n":
                    break;
                default:
                    System.out.println("Unrecognized entityJson child: " + child.toString());
            }
        }

        return attributeTypes;
    }

    public String getEntityId() {
        return entityJson.getString("id");
    }

    public String getEntityName() {
        return entityJson.getString("text");
    }

    public JSONObject getEntityJson() {
        return entityJson;
    }

    public void setEntityJson(String entityJson) {
        this.entityJson = new JSONObject(entityJson);
    }

}
