package de.ralleytn.fmcs.editor.translator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Stack;

import de.ralleytn.fmcs.Utils;

public final class TranslatorJavaScript {

	public static final void translate(Reader reader, Writer writer) {
		
		try(BufferedReader bReader = new BufferedReader(reader); BufferedWriter bWriter = new BufferedWriter(writer)) {
			
			String line = null;
			boolean inBlockComment = false;
			boolean inLineComment = false;
			boolean inString = false;
			boolean searchForWhile = false;
			Stack<String> codeBlock = new Stack<>();
			char stringStarter = '"';
			
			while((line = bReader.readLine()) != null) {
				
				line = line + '\n';
				char[] tokens = line.toCharArray();
				int length = tokens.length;
				
				// direct char array edits to improve speed.
				
				for(int index = 0; index < length; index++) {
					
					int charactersLeft = length - index;
					
					if(charactersLeft > 1) {
						
						if(!inLineComment && !inBlockComment &&
						   tokens[index] == '/' &&
						   tokens[index + 1] == '*') {
							
							tokens[index] = '-';
							tokens[index + 1] = '-';
							tokens = _insert(tokens, '[', index + 2);
							tokens = _insert(tokens, '[', index + 3);
							inBlockComment = true;
							length = tokens.length;
							
						} else if(!inLineComment && inBlockComment &&
						   tokens[index] == '*' &&
						   tokens[index + 1] == '/') {

							tokens[index] = ']';
							tokens[index + 1] = ']';
							tokens = _insert(tokens, '-', index + 2);
							tokens = _insert(tokens, '-', index + 3);
							inBlockComment = false;
							length = tokens.length;
						}
						
						if(charactersLeft >= 2 && !inBlockComment &&
								   tokens[index] == '/' &&
								   tokens[index + 1] == '/') {

							tokens[index] = '-';
							tokens[index + 1] = '-';
							inLineComment = true;
						}
					}

					if(inLineComment && tokens[index] == '\n') {
						
						inLineComment = false;
					}
					
					if(charactersLeft > 1) {
						
						if(!inBlockComment && !inLineComment && !inString && (tokens[index] == '\'' || tokens[index] == '"')) {
							
							inString = true;
							stringStarter = tokens[index];
						}
						
						if(!inBlockComment && !inLineComment && inString && tokens[index] == stringStarter && (index - 1 > 0 && tokens[index - 1] != '\\')) {
							
							inString = false;
						}
					}
					
					if(!inBlockComment && !inLineComment && !inString) {
						
						if(charactersLeft >= 3) {
							
							if(tokens[index] == '=' && tokens[index + 1] == '=' && tokens[index + 2] == '=') {
								
								tokens[index + 2] = '\0';
								
							}
							
							if(tokens[index] == '!' && tokens[index + 1] == '=' && tokens[index + 2] == '=') {
								
								tokens[index + 2] = '\0';
								tokens[index] = '~';
							}
							
							if(tokens[index] == 'f' && tokens[index + 1] == 'o' && tokens[index + 2] == 'r') {
								
								codeBlock.push("for");
							}
							
							if(tokens[index] == 'v' && tokens[index + 1] == 'a' && tokens[index + 2] == 'r') {
										
								if(codeBlock.size() > 0) {
											
									tokens[index] = 'l';
									tokens[index + 1] = 'o';
									tokens[index + 2] = 'c';
									tokens = _insert(tokens, 'a', index + 3);
									tokens = _insert(tokens, 'l', index + 4);
									length = tokens.length;
													
								} else {
											
									tokens[index] = '\0';
									tokens[index + 1] = '\0';
									tokens[index + 2] = '\0';
								}
							}
						}
						
						if(charactersLeft >= 2) {
							
							if(tokens[index] == '&' && tokens[index + 1] == '&') {
								
								tokens[index] = 'a';
								tokens[index + 1] = 'n';
								tokens = _insert(tokens, 'd', index + 2);
								length = tokens.length;
							
							} else if(tokens[index] == '!' && tokens[index + 1] == '=') {
								
								tokens[index] = '~';
								
							} else if(tokens[index] == '|' && tokens[index + 1] == '|') {
								
								tokens[index] = 'o';
								tokens[index + 1] = 'r';
								
							} else if(tokens[index] == 'd' && tokens[index + 1] == 'o') {
								
								tokens[index] = 'r';
								tokens[index + 1] = 'e';
								tokens = _insert(tokens, 'p', index + 2);
								tokens = _insert(tokens, 'e', index + 3);
								tokens = _insert(tokens, 'a', index + 4);
								tokens = _insert(tokens, 't', index + 5);
								length = tokens.length;
								codeBlock.push("repeat");
							}
							
							if(tokens[index] == 'i' && tokens[index + 1] == 'f') {
								
								codeBlock.push("if");
							}
						}
						
						if(charactersLeft >= 4) {
							
							if(tokens[index] == 'n' &&
							   tokens[index + 1] == 'u' &&
							   tokens[index + 2] == 'l' &&
							   tokens[index + 3] == 'l') {
								
								tokens[index] = 'n';
								tokens[index + 1] = 'i';
								tokens[index + 2] = 'l';
								tokens[index + 3] = '\0';
							}
						}
						
						if(charactersLeft >= 5) {
							
							if(tokens[index] == 'w' && tokens[index + 1] == 'h' && tokens[index + 2] == 'i' && tokens[index + 3] == 'l' && tokens[index + 4] == 'e') {
								
								if(searchForWhile) {
									
									tokens[index] = 'u';
									tokens[index + 1] = 'n';
									tokens[index + 2] = 't';
									tokens[index + 3] = 'i';
									tokens[index + 4] = 'l';
									searchForWhile = false;
									
								} else {
									
									codeBlock.push("while");
								}
							}
						}
						
						if(charactersLeft >= 9) {
							
							if(tokens[index] == 'u' &&
							   tokens[index + 1] == 'n' &&
							   tokens[index + 2] == 'd' &&
							   tokens[index + 3] == 'e' &&
							   tokens[index + 4] == 'f' &&
							   tokens[index + 5] == 'i' &&
							   tokens[index + 6] == 'n' &&
							   tokens[index + 7] == 'e' &&
							   tokens[index + 8] == 'd') {
								
								tokens[index] = 'n';
								tokens[index + 1] = 'i';
								tokens[index + 2] = 'l';

								for(int i = 3; i < 9; i++) {
									
									tokens[index + i] = '\0';
								}
							}
						}
						
						if(charactersLeft >= 8) {
							
							if(tokens[index] == 'f' &&
							   tokens[index + 1] == 'u' &&
							   tokens[index + 2] == 'n' &&
							   tokens[index + 3] == 'c' &&
							   tokens[index + 4] == 't' &&
							   tokens[index + 5] == 'i' &&
							   tokens[index + 6] == 'o' &&
							   tokens[index + 7] == 'n') {
								
								codeBlock.push("function");
							}
						}
						
						if(charactersLeft >= 1) {
							
							if(tokens[index] == '}') {
								
								String block = codeBlock.pop();
								
								if(block.equals("function")) {
									
									tokens[index] = 'e';
									tokens = _insert(tokens, 'n', index + 1);
									tokens = _insert(tokens, 'd', index + 2);
									length = tokens.length;
									
								} else if(block.equals("repeat")) {
									
									tokens[index] = '\0';
									searchForWhile = true;
								}
								
							} else if(tokens[index] == '{') {
								
								String block = codeBlock.peek();
								
								if(block.equals("function") || block.equals("repeat")) {
									
									tokens[index] = '\0';
									
								} else if(block.equals("if")) {
									
									tokens[index] = 't';
									tokens = _insert(tokens, 'h', index + 1);
									tokens = _insert(tokens, 'e', index + 2);
									tokens = _insert(tokens, 'n', index + 3);
									length = tokens.length;
								}
							}
						}
					}
				}
				
				bWriter.write(new String(tokens).replace("\0", ""));
			}
			
			bWriter.flush();
			
		} catch(Exception exception) {
			
			Utils.handleException(exception);
		}
	}
	
	private static final char[] _insert(char[] array, char value, int index) {
		
		char[] returnValue = new char[array.length + 1];
		int i2 = 0;
		
		for(int i = 0; i < returnValue.length; i++) {
			
			if(i == index) {
				
				returnValue[i] = value;
				
			} else {
				
				returnValue[i] = array[i2];
				i2++;
			}
		}
		
		return returnValue;
	}
}
