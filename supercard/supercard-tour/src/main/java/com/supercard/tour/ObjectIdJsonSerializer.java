package com.supercard.tour;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdJsonSerializer extends JsonSerializer<ObjectId> {
    @Override
    public void serialize(ObjectId objectId, JsonGenerator generator, SerializerProvider s) throws IOException {
        if(objectId == null) {
            generator.writeNull();
        } else {
            generator.writeString(objectId.toString());
        }
    }
}