package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class TermsParser {
	private ElementFactory factory;

	protected ElementFactory getFactory() {
		if(factory == null) {
			factory = new ElementFactory();
		}
		return factory;
	}

	public AbstractElement parse(String filename) throws Exception{
		AbstractElement root = new ParagraphElement(ElementType.ROOT);
		root.setText(filename);

		File f = new File(filename);
		if(!f.exists()) {
			throw new FileNotFoundException("File not found. " + f.getAbsolutePath());
		}
		FileInputStream fis = null;
		XWPFDocument doc = null;

		try {
			fis = new FileInputStream(f);
			doc = new XWPFDocument(fis);

			List<AbstractElement> list = new ArrayList<AbstractElement>();
			List<IBodyElement> rawList = doc.getBodyElements();

			for(IBodyElement raw : rawList) {
				AbstractElement elem = null;
				if(raw instanceof XWPFParagraph) {
					elem = createElement((XWPFParagraph)raw);
				}
				else if(raw instanceof XWPFTable) {
					elem = createElement((XWPFTable)raw);
				}
				else {
					System.out.println("Not supported type." + raw.getClass().getName());
					continue;
				}
				if(elem != null) {
					list.add(elem);
				}
			}

			makeHierarchy(root, list);

			return root;
		}finally {
			if(doc != null) {
				try {
					doc.close();
				}catch(Exception e) {}
			}
			if(fis != null) {
				try {
					fis.close();
				}catch(Exception e) {}
			}
		}
	}

	protected void makeHierarchy(AbstractElement root, List<AbstractElement> list) {
		Stack<AbstractElement> stack = new Stack<>();
		Stack<AbstractElement> stack2 = new Stack<>();

		for(AbstractElement abs : list) {
			if(stack.size() == 0) {
				stack.add(abs);
				root.addChild(abs);
				continue;
			}

			if(abs.isContents() || abs.isTable()) {
				if(abs.getText() == "\n" || abs.getText() == "") {
					continue;
				}
				stack.peek().addChild(abs);
				continue;
			}


			if(abs.isGwanOrAttached()) {
				while(true) {
					if(stack.size() <= 1) break;
					else if(stack.peek().isGwanOrAttached()) {
						while(!stack2.isEmpty()) {
							stack.peek().addChild(stack2.pop());
						}
						stack.elementAt(0).addChild(stack.pop());
						break;
					}

					stack2.add(stack.pop());

					if(stack2.peek().getNo() == 1) {
						while(true) {
							AbstractElement checkElem = stack2.pop();
							stack.peek().addChild(checkElem);
							if(stack2.isEmpty()) {
								break;
							}else if(!(checkElem.getContentsType() == stack2.peek().getContentsType() && checkElem.getNo() == stack2.peek().getNo()-1)) {
								break;
							}
						}

					}

				}

			}

			stack.add(abs);
		}

		// Last Data
		while(true) {
			if(stack.size() <= 1) break;
			else if(stack.peek().isGwanOrAttached()) {
				while(!stack2.isEmpty()) {
					stack.peek().addChild(stack2.pop());
				}
				stack.elementAt(0).addChild(stack.pop());
				break;
			}

			stack2.add(stack.pop());

			if(stack2.peek().getNo() == 1) {
				while(true) {
					AbstractElement checkElem = stack2.pop();
					stack.peek().addChild(checkElem);
					if(stack2.isEmpty()) {
						break;
					}else if(!(checkElem.getContentsType() == stack2.peek().getContentsType() && checkElem.getNo() == stack2.peek().getNo()-1)) {
						break;
					}
				}

			}

		}
	}

	protected AbstractElement createElement(XWPFParagraph ph) {
		String text = ph.getParagraphText();
		AbstractElement elem = getFactory().createParagraphElement(text);
		
		return elem;
	}

	protected TableElement createElement(XWPFTable table) {
		TableElement tableElem = getFactory().createTableElement();

		List<XWPFTableRow> rows = table.getRows();
		for(int i = 0; i < rows.size(); i++) {
			XWPFTableRow row = rows.get(i);
			RowElement rowElem = getFactory().createRowElement();
			tableElem.addRow(rowElem);

			List<XWPFTableCell> cells = row.getTableCells();

			for(int n = 0; n < cells.size(); n++) {
				XWPFTableCell cell = cells.get(n);
				CellElement cellElem = getFactory().createCellElement();
				rowElem.addCell(cellElem);

				List<IBodyElement> elems = cell.getBodyElements();

				for(int k = 0; k < elems.size(); k++) {
					IBodyElement elem = elems.get(k);

					AbstractElement obj = null;
					if(elem instanceof XWPFParagraph) {
						obj = createElement((XWPFParagraph)elem);
					}
					else if(elem instanceof XWPFTable) {
						obj = createElement((XWPFTable)elem);
					}
					else {
						System.out.println("Not supported type." + elem.getClass().getName());
						continue;
					}

					if(obj != null) {
						cellElem.addElement(obj);
					}
				}
			}
		}

		return tableElem;
	}
}
