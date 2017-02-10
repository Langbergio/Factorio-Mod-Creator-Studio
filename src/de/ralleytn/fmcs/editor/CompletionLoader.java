package de.ralleytn.fmcs.editor;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.fife.ui.autocomplete.Completion;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import de.ralleytn.fmcs.Utils;

public final class CompletionLoader {

	public static final List<Completion> load(Reader reader) {
		
		JSONArray array = null;
		List<Completion> completions = new ArrayList<>();
		
		try {
			
			array = (JSONArray)new JSONParser().parse(reader);
			
		} catch(Exception exception) {
			
			Utils.handleException(exception);
			return completions;
		}
		
		for(Object element : array) {
			
			JSONObject object = (JSONObject)element;
		}
		
		return completions;
	}
}
