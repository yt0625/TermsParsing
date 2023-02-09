package main.parse;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractElement {
	protected ElementType elementType;
	protected ContentsType contentsType;

	protected int no = -1;
	protected String text;
	protected AbstractElement parent;
	protected String ab;
	protected String sql;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getAb() {
		return ab;
	}

	public void setAb(String ab) {
		this.ab = ab;
	}


	protected final List<AbstractElement> childList = new ArrayList<AbstractElement>();

	public AbstractElement(ElementType elementType, ContentsType contentsType) {
		this.elementType = elementType;
		this.contentsType = contentsType;
	}

	public ElementType getElementType() {
		return elementType;
	}

	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}

	public ContentsType getContentsType() {
		return contentsType;
	}

	public void setContentsType(ContentsType contentsType) {
		this.contentsType = contentsType;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public boolean hasNo() {
		return no > -1;
	}

	public boolean isNumberElement() {
		return hasNo();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void addChild(AbstractElement elem) {
		addChild(elem, -1);
	}

	public AbstractElement getParent() {
		return parent;
	}

	public void setParent(AbstractElement parent) {
		this.parent = parent;
	}

	public void addChild(AbstractElement elem, int index) {
		if (index < 0) {
			childList.add(elem);

		} else {
			childList.add(index, elem);

		}
		elem.setParent(this);
	}

	public boolean isGwanOrAttached() {
		return getContentsType() == ContentsType.GWAN || getContentsType() == ContentsType.ATTACHED;
	}

	public boolean isGwan() {
		return getContentsType() == ContentsType.GWAN;
	}

	public boolean isJo() {
		return getContentsType() == ContentsType.JO;
	}

	public boolean isAttached() {
		return getContentsType() == ContentsType.ATTACHED;
	}

	public boolean isContents() {
		return getContentsType() == ContentsType.CONTENTS;
	}
	public boolean isTable() {
		return getContentsType() == ContentsType.TABLE;
	}

	public AbstractElement[] getChildren() {
		return childList.toArray(new AbstractElement[childList.size()]);
	}

	public int getChildCount() {
		return childList.size();
	}

	public AbstractElement getChildAt(int index) {
		return childList.get(index);
	}

	protected String makeIndentString(int indent) {
		if (indent == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append("  ");
		}
		return sb.toString();
	}

	protected void extractText(AbstractElement parent, StringBuilder sb, int[] indent) {
		sb.append(makeIndentString(indent[0])).append(parent.getText()).append("\n");

		AbstractElement[] children = parent.getChildren();

		for (AbstractElement child : children) {
			indent[0]++;
			extractText(child, sb, indent);
			indent[0]--;
		}
	}

	public String print() {
		StringBuilder sb = new StringBuilder();
		int[] indent = new int[] {-1};
		extractText(this, sb, indent);

		return sb.toString().replace("\n", "</br>").replace(" ", "&nbsp");
	}


}
