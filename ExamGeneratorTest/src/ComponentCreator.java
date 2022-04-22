import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ComponentCreator {
	
	public JTextPane createTextPane(String text, Font font, boolean centered)
	{
		 JTextPane pane = new JTextPane();  
	     SimpleAttributeSet attributeSet = new SimpleAttributeSet();  
	     StyleConstants.setBold(attributeSet, true);  
	     
	     StyledDocument documentStyle = pane.getStyledDocument();
	     //SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
	     
	     
	     if (centered)
	    	 StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_CENTER);
	     documentStyle.setParagraphAttributes(0, documentStyle.getLength(), attributeSet, false);
	     
	     pane.setCharacterAttributes(attributeSet, true);  
	     
	     pane.setFont(font);
	     
	     //pane.setBounds( 0, 0, 100, 100 );
	     System.out.println(pane.getSize());
	     
	     pane.setText(text);  
	     
	     pane.setEditable(false);
	     
	     return pane;
	}

}
