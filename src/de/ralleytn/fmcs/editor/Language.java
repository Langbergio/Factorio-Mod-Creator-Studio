package de.ralleytn.fmcs.editor;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;

public class Language {

	private final String name;
	private final String identifier;
	
	public Language(String name, String identifier, Class<?> tokenMaker) {
		
		this.name = name;
		this.identifier = identifier;
		
		AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory)TokenMakerFactory.getDefaultInstance();
		atmf.putMapping(identifier, tokenMaker.getName());
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public String getIdentifier() {
		
		return this.identifier;
	}
}
