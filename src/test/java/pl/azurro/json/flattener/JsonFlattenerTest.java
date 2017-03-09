package pl.azurro.json.flattener;

import java.util.HashMap;
import java.util.Map;

import org.fest.assertions.Assertions;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonFlattenerTest {

    @Test
    public void shouldFlattenSimpleNestedJson() {
        String json = "{\"weight\": {\"value\": 50.54, \"unit\": \"kg\"}, \"id\":12}";
        JsonObject doc = new JsonParser().parse(json).getAsJsonObject();

        Map<String, String> flattened = JsonFlattener.flatten(doc);

        Map<String, String> expected = new HashMap<String, String>();
        expected.put("id", "12");
        expected.put("weight.value", "50.54");
        expected.put("weight.unit", "kg");
        Assertions.assertThat(flattened).hasSize(3).isEqualTo(expected);
    }

    @Test
    public void shouldFlattenJsonArray() {
        JsonObject doc = new JsonParser().parse("{\"array\":[1, 2, 3]}").getAsJsonObject();

        Map<String, String> flattened = JsonFlattener.flatten(doc);

        Map<String, String> expected = new HashMap<String, String>();
        expected.put("array[0]", "1");
        expected.put("array[1]", "2");
        expected.put("array[2]", "3");
        Assertions.assertThat(flattened).hasSize(3).isEqualTo(expected);
    }

    @Test
    public void shouldFlattenNestedJsonWithArray() {
        String json = "{\"colors\": [{\"name\": \"red\", \"val\": {\"R\": \"255\",\"G\": \"0\",\"B\": \"0\"}}, "
                + "{\"name\": \"blue\", \"val\": {\"R\": \"0\",\"G\": \"0\",\"B\": \"255\"}}]}";
        JsonObject doc = new JsonParser().parse(json).getAsJsonObject();

        Map<String, String> flattened = JsonFlattener.flatten(doc);

        Map<String, String> expected = new HashMap<String, String>();
        expected.put("colors[0].name", "red");
        expected.put("colors[0].val.R", "255");
        expected.put("colors[0].val.G", "0");
        expected.put("colors[0].val.B", "0");
        expected.put("colors[1].name", "blue");
        expected.put("colors[1].val.R", "0");
        expected.put("colors[1].val.G", "0");
        expected.put("colors[1].val.B", "255");
        Assertions.assertThat(flattened).hasSize(8).isEqualTo(expected);
    }

    @Test
    public void shouldReturnEmptyMapForEmptyArray() {
        JsonObject doc = new JsonParser().parse("{\"emptyArray\":{}}").getAsJsonObject();

        Map<String, String> flattened = JsonFlattener.flatten(doc);

        Assertions.assertThat(flattened).isEmpty();
    }

    @Test
    public void shouldReturnEmptyMapForEmptyRootDocument() {
        JsonObject doc = new JsonParser().parse("{}").getAsJsonObject();

        Map<String, String> flattened = JsonFlattener.flatten(doc);

        Assertions.assertThat(flattened).isEmpty();
    }

    @Test
    public void shouldReturnEmptyMapForEmptyDocument() {
        JsonObject doc = new JsonParser().parse("{\"emptyDoc\":{}}").getAsJsonObject();

        Map<String, String> flattened = JsonFlattener.flatten(doc);

        Assertions.assertThat(flattened).isEmpty();
    }

}
