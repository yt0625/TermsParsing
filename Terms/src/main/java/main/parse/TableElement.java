package main.parse;

import java.util.ArrayList;
import java.util.List;

public class TableElement extends ContainerElement {
	private List<RowElement> rows = new ArrayList<RowElement>();

	public TableElement() {
		super(ElementType.TABLE, ContentsType.TABLE);
	}

	@Override
	public void addElement(AbstractElement elem, int index) {
		if(!(elem instanceof RowElement)) {
			throw new RuntimeException("Invalid type. " + RowElement.class.getName() + " is only");
		}
		addRow((RowElement)elem);
	}

	public void addRow(RowElement elem) {
		super.addElement(elem, -1);
		rows.add(elem);
		elem.setTable(this);
	}

	public RowElement[] getRows() {
		return rows.toArray(new RowElement[rows.size()]);
	}

	@Override
	public String getText() {
		StringBuilder sb = new StringBuilder();

		sb.append("--Table--");

		for(int i=0; i<getElementCount(); i++) {
			sb.append(getElements()[i].getText());
		}
		return sb.toString();
	}
}
