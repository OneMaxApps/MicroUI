package microUI.core;

import static processing.core.PApplet.constrain;

import microUI.util.Constants;

public abstract class TextController {
	protected final StringBuilder sb;
	private boolean validation,constrain;
	private static final String additionalSymbols = "!@#$%^&*()_-+=|\\/[]{}<>,. \'\";:№?*";
	private int validationMode,maxChars;
	
	public TextController(final String text) {
		sb = new StringBuilder(text);
		validation = true;
		maxChars = 10;
	}
	
	public TextController() {
		this("");
	}
	
	public final void set(final String text) {
		if(text == null) { return; }
		
		inSetting();
		
		if(isEmpty()) {
			insert(0,text);
		} else {
			clear();
			insert(0,text);
		}
		
	}
	
	public final void setValidationMode(int validationMode) {
		if(validationMode < 0 || validationMode > 3) { return; }
		this.validationMode = validationMode;
	}
	
	
	public final boolean isConstrain() {
		return constrain;
	}

	public final void setConstrain(boolean constrain) {
		this.constrain = constrain;
	}

	public final int getMaxChars() {
		return maxChars;
	}

	public final void setMaxChars(int maxChars) {
		if(maxChars <= 0) { return; }
		
		this.maxChars = maxChars;
	}

	public final void set(final TextController text) {
		set(text.get());
	}
	
	public final String get() {
		return sb.toString();
	}
	
	public final StringBuilder getStringBuilder() { return sb; }
	
	public final void insert(final int pos, final char ch) {
		if(pos < 0 || pos > length()) { return; }
		if(constrain && length() >= maxChars) { return; }
		
		if(validation) {
		  if(isValidChar(ch)) {
			  sb.insert(pos,ch);
			  inInserting();
		  }
		} else {
		  sb.insert(pos,ch);
		}
	}
	
	public final void insert(final int pos, final String text) {
		if(pos < 0 || pos > length()) { return; }
		sb.insert(pos,text);
	}
	
	public final void insert(final int pos, final int num) {
		if(pos < 0 || pos > length()) { return; }
		sb.insert(pos,num);
	}
	
	public final void clear() {
		if(!isEmpty()) {
			sb.setLength(0);
			sb.trimToSize();
		}
	}
	
	public final boolean isEmpty() {
		return length() == 0;
	}
	
	public final void removeCharAt(final int pos) {
		if(isEmpty()) { return; }
		sb.deleteCharAt(constrain(pos,0,length()-1));
	}
	
	public final void removeFirstChar() {
		removeCharAt(0);
	}
	
	public final void removeLastChar() {
		removeCharAt(length()-1);
	}
	
	public final int length() {
		return sb.length();
	}

	public final boolean isValidation() {
		return validation;
	}

	public final void setValidation(boolean validation) {
		this.validation = validation;
	}
	
	public final boolean isValidChar(final char ch) {
		
		switch(validationMode) {
			case Constants.VALIDATION_ALPHANUMERIC :
				return additionalSymbols.contains(String.valueOf(ch)) || Character.isLetterOrDigit(ch);
			case Constants.VALIDATION_ONLY_DIGITS :
				return Character.isDigit(ch);
			case Constants.VALIDATION_ONLY_LETTERS :
				return Character.isLetter(ch);
						
			default :
			return additionalSymbols.contains(String.valueOf(ch)) || Character.isLetterOrDigit(ch);
		}
		
		
	}
	
	protected void inInserting() {}
	
	protected void inSetting() {}
	
}