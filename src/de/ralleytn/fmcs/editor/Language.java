package de.ralleytn.fmcs.editor;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.folding.FoldParser;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;
import org.fife.ui.rsyntaxtextarea.parser.Parser;

public class Language {

	private final String name;
	private final String identifier;
	private final Parser parser;
	private final FoldParser foldParser;
	private final String langName;
	
	public Language(String name, String langName, String identifier, Class<?> tokenMaker, Parser parser, FoldParser foldParser) {
		
		this.name = name;
		this.identifier = identifier;
		this.parser = parser;
		this.langName = langName;
		this.foldParser = foldParser;
		
		AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory)TokenMakerFactory.getDefaultInstance();
		atmf.putMapping(identifier, tokenMaker.getName());
		
		FoldParserManager.get().addFoldParserMapping(identifier, foldParser);
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public String getIdentifier() {
		
		return this.identifier;
	}
	
	public Parser getParser() {
		
		return this.parser;
	}
	
	public FoldParser getFoldParser() {
		
		return this.foldParser;
	}
	
	public String getLangName() {
		
		return this.langName;
	}
}
