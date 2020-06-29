package gauss;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

class MyIntFilter extends DocumentFilter {

	JOptionPane pane = new JOptionPane();
	
	@Override
	public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr)
			throws BadLocationException {
		
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);
		
		if(test(sb.toString())) {
			super.insertString(fb, offset, string, attr);
		} else {
			new popup().main("Warning", "You can only use numbers");
		}
	}

	private boolean test(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs)
			throws BadLocationException {
		
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset+length, text);
		
		if (test(sb.toString())) {
			super.replace(fb, offset, length, text, attrs);
		} else {
			new popup().main("Warning", "You can only use numbers");
			//JOptionPane.showMessageDialog(null, );
		}
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset+length);
		
		if(sb.toString().length() == 0) {
			super.replace(fb, offset, length, "", null);
		} else if (test(sb.toString())) {
			super.remove(fb, offset, length);
		} else {
			new popup().main("Warning", "You can only use numbers");
		}
	}
	
}