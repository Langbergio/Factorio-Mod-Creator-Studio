package de.ralleytn.fmcs.editor;

import java.awt.Color;
import java.io.StringReader;

import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;
import org.fife.ui.rsyntaxtextarea.parser.ParserNotice.Level;
import org.luaj.vm2.ast.Chunk;
import org.luaj.vm2.ast.Stat.FuncDef;
import org.luaj.vm2.ast.Visitor;
import org.luaj.vm2.parser.LuaParser;
import org.luaj.vm2.parser.ParseException;

import de.ralleytn.fmcs.Program;

public class LuaJ2RSyntaxTextArea extends AbstractParser {
	
	// FIXME
	// For some weird reason an error on the AWT-EventQueue-0 thread is produced when you type ONLY ONE
	// Lua keyword and then hover with the mouse over the red marked text. it's not really breaking anything.
	// It is just a bit annoying and should be removed in case that it could prevent features or bites us in
	// the ass at a later point in time.
	
	@Override
	public ParseResult parse(RSyntaxDocument document, String identifier) {
		
		if(identifier.equals(Program.getLanguageByName("Lua").getIdentifier())) {
			
			try {
				
				String text = document.getText(0, document.getLength());
				LuaParser parser = new LuaParser(new StringReader(text));
				Chunk chunk = parser.Chunk();
				chunk.accept(new Visitor() {
					
					@Override
					public void visit(FuncDef stat) {
						
						super.visit(stat);
						
						try {
							
							String functionName = text.substring((stat.name.beginLine * stat.name.beginColumn) - 1, stat.name.endLine * stat.name.endColumn);
							System.out.println(functionName);
							
						} catch(StringIndexOutOfBoundsException exception) {
							
							
						}
					}
				});
				
			} catch(ParseException exception) {
				
				if(exception.currentToken != null) {
					
					DefaultParserNotice notice = new DefaultParserNotice(this, exception.getMessage(), exception.currentToken.beginLine);
					notice.setLevel(Level.ERROR);
					notice.setColor(Color.RED);
					notice.setShowInEditor(true);
					notice.setToolTipText(exception.getMessage());
					
					DefaultParseResult result = new DefaultParseResult(this);
					result.addNotice(notice);
					result.setError(exception);
					
					return result;
					
				} else {
					
					DefaultParserNotice notice = new DefaultParserNotice(this, exception.getMessage(), 0);
					notice.setLevel(Level.ERROR);
					notice.setColor(Color.RED);
					notice.setShowInEditor(true);
					notice.setToolTipText(exception.getMessage());
					
					DefaultParseResult result = new DefaultParseResult(this);
					result.addNotice(notice);
					result.setError(exception);
					
					return result;
				}
				
			} catch(Exception exception) {
				
				
			}
		}
		
		return new DefaultParseResult(this);
	}
}
