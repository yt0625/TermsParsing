package main;

import java.util.ArrayList;
import java.util.List;

public class RowElement extends ContainerElement {
	protected List<CellElement> cells = new ArrayList<CellElement>();
	protected TableElement table;

	public RowElement() {
		super(ElementType.ROW, ContentsType.ROW);
	}

	@Override
	public void addElement(AbstractElement elem, int index) {
		if(elem instanceof CellElement) {
			throw new RuntimeException("Invalid type. " + CellElement.class.getName() + " is only");
		}
		addCell((CellElement)elem);
	}

	public void addCell(CellElement elem) {
		super.addElement(elem, -1);
		cells.add(elem);
		elem.setRow(this);
	}

	public CellElement[] getCells() {
		return cells.toArray(new CellElement[cells.size()]);
	}

	public TableElement getTable() {
		return table;
	}
	public void setTable(TableElement table) {
		this.table = table;
	}
	public int getSize() {
		return getElementCount();
	}

	@Override
	public String getText() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");

		for(int i=0; i<getElementCount(); i++) {
			sb.append(getElements()[i].getText());
		}
		return sb.toString();
	}


}
