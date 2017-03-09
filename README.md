# json-flattener

A Java tool to flatten nested JSON documents.

## Usage

Here is an examples to give you an idea how to use the tool.

Assume you want to flatten the JSON object:

```json
{
  "name": "my colors",
  "colors": [
    {"name": "red", "val": {"R": "255","G": "0","B": "0"}}, 
    {"name": "blue", "val": {"R": "0","G": "0","B": "255"}}
  ]
}
```

You can write a simple class to flatten the JSON document:

```java
package pl.azurro.json.flattener.example;

import java.util.Map;
import java.util.Map.Entry;

import pl.azurro.json.flattener.JsonFlattener;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonFlattenerExample {

    public static void main(String[] args) {
        String json = "{\"name\": \"my colors\",\"colors\": ["
                + "{\"name\": \"red\", \"val\": {\"R\": \"255\",\"G\": \"0\",\"B\": \"0\"}}, "
                + "{\"name\": \"blue\", \"val\": {\"R\": \"0\",\"G\": \"0\",\"B\": \"255\"}}]}";
        JsonObject doc = new JsonParser().parse(json).getAsJsonObject();

        Map<String, String> flattened = JsonFlattener.flatten(doc);

        for (Entry<String, String> e : flattened.entrySet()) {
            System.out.println(e.getKey() + "=" + e.getValue());
        }
    }

}
```

The above example prints the result to standard output:

```
name=my colors
colors[0].name=red
colors[0].val.R=255
colors[0].val.G=0
colors[0].val.B=0
colors[1].name=blue
colors[1].val.R=0
colors[1].val.G=0
colors[1].val.B=255
```
