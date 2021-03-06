/*
 * /*
 *     This file is part of ImageJ FX.
 *
 *     ImageJ FX is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     ImageJ FX is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with ImageJ FX.  If not, see <http://www.gnu.org/licenses/>. 
 *
 * 	Copyright 2015,2016 Cyril MONGIS, Michael Knop
 *
 */
package ijfx.ui.module.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.scijava.module.ModuleItem;

/**
 *
 * @author Cyril MONGIS, 2015
 */
public class ModuleItemSerializer extends JsonSerializer<ModuleItem> {

    @Override
    public void serialize(ModuleItem t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {

        jg.writeStartObject();
        jg.writeStringField("name", t.getName());
        jg.writeStringField("label", t.getLabel());

        try {
            jg.writeObjectField("minValue", t.getMinimumValue());
            jg.writeObjectField("maxValue", t.getMaximumValue());
            jg.writeObjectField("classType", t.getType());

            jg.writeObjectField("description", t.getDescription());
            jg.writeObjectField("ioType", t.getIOType());
            jg.writeObjectField("isInput", t.isInput());
            jg.writeObjectField("isOutput", t.isOutput());
            jg.writeObjectField("isAutoFill", t.isAutoFill());
            jg.writeObjectField("isRequired", t.isRequired());
            jg.writeObjectField("visibility", t.getVisibility());
            jg.writeObjectField("columnCount", t.getColumnCount());
            jg.writeObjectField("choices", t.getChoices());
        } catch (Exception e) {

        }
        jg.writeEndObject();

    }

}
