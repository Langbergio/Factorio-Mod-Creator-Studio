package de.ralleytn.fmcs;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.folding.CurlyFoldParser;
import org.fife.ui.rsyntaxtextarea.folding.FoldParser;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;
import org.fife.ui.rsyntaxtextarea.parser.Parser;

import de.ralleytn.fmcs.editor.parser.FoldParserLua;
import de.ralleytn.fmcs.editor.tokenmaker.TokenMakerJavaScriptFMCS;
import de.ralleytn.fmcs.editor.tokenmaker.TokenMakerLua_5_2;

/**
 * 
 * @author Ralph Niemitz(RalleYTN)/ralph.niemitz@gmx.de
 * @version 0.1.0
 * @since 0.1.0
 */
public enum Language {

	/**
	 * 
	 * @since 0.1.0
	 */
	LUA("Lua 5.2", "Lua", "text/lua-5.2", TokenMakerLua_5_2.class, null, new FoldParserLua(), "lua"),
	
	/**
	 * 
	 * @since 0.1.0
	 */
	JAVASCRIPT("JavaScript(FMCS Edition)", "JavaScript", "text/js-fmcs", TokenMakerJavaScriptFMCS.class, null, new CurlyFoldParser(true, false), "js");
	
	private final String name;
	private final String identifier;
	private final Class<?> parser;
	private final FoldParser foldParser;
	private final String langName;
	private final String fileEnding;
	
	private Language(String name, String langName, String identifier, Class<?> tokenMaker, Class<?> parser, FoldParser foldParser, String fileEnding) {
		
		this.name = name;
		this.identifier = identifier;
		this.parser = parser;
		this.langName = langName;
		this.foldParser = foldParser;
		this.fileEnding = fileEnding;
		
		AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory)TokenMakerFactory.getDefaultInstance();
		atmf.putMapping(identifier, tokenMaker.getName());
		
		FoldParserManager.get().addFoldParserMapping(identifier, foldParser);
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public String getName() {
		
		return this.name;
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public String getIdentifier() {
		
		return this.identifier;
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public Parser getParser() {
		
		if(this.parser != null) {
			
			try {
				
				return (Parser)this.parser.newInstance();
				
			} catch (InstantiationException | IllegalAccessException exception) {
				
				Utils.handleException(exception);
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public FoldParser getFoldParser() {
		
		return this.foldParser;
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public String getLangName() {
		
		return this.langName;
	}
	
	/**
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public String getFileEnding() {
		
		return this.fileEnding;
	}
}
