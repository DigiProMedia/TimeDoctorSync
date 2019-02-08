package com.digipro.timedoctor.rest.ro.td;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class LongTypeAdapter extends TypeAdapter<Number> {

	@Override
	public void write(JsonWriter out, Number value) throws IOException {
		out.value(value);
	}

	@Override
	public Number read(JsonReader in) throws IOException {

		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		try {
			String result = in.nextString();
			if (result != null)
				result = result.trim();
			if ("".equals(result)) {
				return null;
			}
			return Integer.parseInt(result);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
