package microUI.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public final class Clipboard {
	private static String localBuffer;
	private static boolean usingLocalBuffer;
	private static java.awt.datatransfer.Clipboard clip;
	
	static {
		usingLocalBuffer = false;
		clip = Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	
	public static final void set(String txt) {
		if(usingLocalBuffer) {
			Clipboard.localBuffer = txt;
		} else {
			clip.setContents(new StringSelection(txt),null);
		}
	}
	
	public static final String get() {
		if(usingLocalBuffer) {
		 return localBuffer;
		} else {
			try {
				return (String) clip.getData(DataFlavor.stringFlavor);
			} catch(IOException | UnsupportedFlavorException e) {
				e.printStackTrace();
				return "";
			}
		}

	}
	
	public static final void usingLocalBuffer() {
		usingLocalBuffer = true;
	}
	
	public static final void usingSystemBuffer() {
		usingLocalBuffer = false;
	}
	
	public static final String usingTypeOfBuffer() { return usingLocalBuffer ? "Local Buffer" : "OS Buffer"; }
}