package de.ralleytn.fmcs.editor.parser;

import java.util.ArrayList;
import java.util.List;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.folding.Fold;
import org.fife.ui.rsyntaxtextarea.folding.FoldParser;
import org.fife.ui.rsyntaxtextarea.folding.FoldType;

public class FoldParserLua implements FoldParser {

	private boolean foldComments;
	
	public void setFoldComments(boolean foldComments) {
		
		this.foldComments = foldComments;
	}
	
	@Override
	public List<Fold> getFolds(RSyntaxTextArea textArea) {

		List<Fold> folds = new ArrayList<>();
		
		try {
			
			char[] tokens = textArea.getText().toCharArray();
			int length = tokens.length;
			
			Fold currentCommentFold = null;
			boolean inBlockComment = false;
			boolean inLineComment = false;
			
			for(int index = 0; index < length; index++) {
				
				int charactersLeft = length - index;
				
				if(charactersLeft > 3) {
					
					if(!inLineComment &&
					   tokens[index] == '-' &&
					   tokens[index + 1] == '-' &&
					   tokens[index + 2] == '[' &&
					   tokens[index + 3] == '[') {

						if(this.foldComments) {
							
							currentCommentFold = new Fold(FoldType.COMMENT, textArea, index);
						}
						
						inBlockComment = true;
					}

					if(!inLineComment &&
					   tokens[index] == ']' &&
					   tokens[index + 1] == ']' &&
					   tokens[index + 2] == '-' &&
					   tokens[index + 3] == '-') {

						if(currentCommentFold != null) {
							
							currentCommentFold.setEndOffset(index);
							folds.add(currentCommentFold);
							currentCommentFold = null;
						}
						
						inBlockComment = false;
					}
				}
				
				if(charactersLeft >= 2) {
					
					if(!inBlockComment &&
					   tokens[index] == '-' &&
					   tokens[index + 1] == '-') {
						
						inLineComment = true;
					}
				}
				
				if(inLineComment && tokens[index] == '\n') {
					
					inLineComment = false;
				}
			}
			
		} catch(Exception exception) {
			
			exception.printStackTrace();
		}

		return folds;
	}
	
	public boolean isFoldingComments() {
		
		return this.foldComments;
	}
}
