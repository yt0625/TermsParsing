package main;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerElement extends AbstractElement {
	protected List<AbstractElement> elemList = new ArrayList<AbstractElement>();

	public ContainerElement(ElementType elementType, ContentsType contentsType) {
		super(elementType, contentsType);
	}

	public void addElement(AbstractElement elem) {
		addElement(elem, -1);
	}
	public void addElement(AbstractElement elem, int index) {
		if(index < 0) {
			elemList.add(elem);
		}
		else {
			elemList.add(index, elem);
		}
	}

	public AbstractElement[] getElements() {
		return elemList.toArray(new AbstractElement[elemList.size()]);
	}
	public int getElementCount() {
		return elemList.size();
	}
	public AbstractElement getElementAt(int index) {
		return elemList.get(index);
	}

	public abstract String getText();

}
