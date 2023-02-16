package main;

public class CellElement extends ContainerElement {
	protected RowElement row;

	public CellElement() {
		super(ElementType.CELL, ContentsType.CELL);
	}

	public RowElement getRow() {
		return row;
	}
	public void setRow(RowElement row) {
		this.row = row;
	}

	@Override
	public String getText() {
		StringBuilder sb = new StringBuilder();

		if(row.getSize() > 1) {
			for(int i=0; i<getElementCount(); i++) {
				sb.append("\t|")
						.append(getElements()[i].getText());
			}
		} else {
			for(int i=0; i<getElementCount(); i++) {
				sb.append("\t|")
						.append(getElements()[i].getText()+"\n");
			}
		}
		return sb.toString();
	}
}
