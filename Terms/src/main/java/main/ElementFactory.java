package main;

public class ElementFactory {
	static final char[] MOK = "가나다라마바사아자차카타파하".toCharArray();
	static final char[] HANG = "①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮".toCharArray();
	static final char[] RANDOM = "ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩ".toCharArray();
	public static final ElementFactory[] childList = null;

	public ParagraphElement createParagraphElement(String paragraphText) {

		ParagraphElement elem = new ParagraphElement(ElementType.TEXT);

		paragraphText = paragraphText.replace("\n", "");
		String replaceText = paragraphText.trim().replace(" ", "");
		String number = "";

		try {
			for (int i = 0; i < replaceText.length(); i++) {
				char ch = replaceText.charAt(i);
				if ((Character.isDigit(ch))) {
					number += ch;
					if (Character.isDigit(replaceText.charAt(i + 1))) {
						continue;
					} else if (replaceText.charAt(i + 1) == '.' && i < 5) {
						setType(elem, number, ContentsType.HO);
					} else if (replaceText.charAt(i + 1) == '관' && i < 3) {
						setType(elem, number, ContentsType.GWAN);
					} else if (replaceText.charAt(i + 1) == '조' && i < 3) {
						setType(elem, number, ContentsType.JO);
						if (paragraphText.charAt(i + 2) != ' ') {
							setType(elem, "-1", ContentsType.CONTENTS);
						}
					} else if (i < 3) {
						setType(elem, number, ContentsType.RANDOM_NUM);
						if (replaceText.charAt(i - 1) == '「') {
							setType(elem, "-1", ContentsType.CONTENTS);
						}
					} else if (replaceText.charAt(i - 3) == '【' && replaceText.charAt(i - 2) == '별'
							&& replaceText.charAt(i - 1) == '표' && replaceText.charAt(i + 1) == '】' && i < 5) {
						setType(elem, number, ContentsType.ATTACHED);
					}
					break;
				} else if (isCheck(ch, HANG)) {
					setType(elem, Integer.toString(setNo(ch, HANG)), ContentsType.HANG);
					break;
				} else if (isCheck(ch, MOK)) {
					if (replaceText.charAt(i + 1) == '.') {
						setType(elem, Integer.toString(setNo(ch, MOK)), ContentsType.MOK);
					} else if (replaceText.charAt(i + 1) == ')') {
						setType(elem, Integer.toString(setNo(ch, MOK)), ContentsType.RANDOM_WORD);
					}
					break;
				} else if (isCheck(ch, RANDOM)) {
					setType(elem, Integer.toString(setNo(ch, RANDOM)), ContentsType.RANDOM_SHAPE);
					break;
				}
			}
		} catch (StringIndexOutOfBoundsException e) {
		}

		elem.setText(paragraphText);

		return elem;
	}

	private void setType(ParagraphElement elem, String number, ContentsType c) {
		elem.setContentsType(c);
		elem.setNo(Integer.parseInt(number));
	}

	private int setNo(char ch, char[] sepChar) {
		for (int i = 0; i < sepChar.length; i++) {
			if (ch == sepChar[i])
				return i + 1;
		}
		return -1;
	}

	private boolean isCheck(char ch, char[] sepChar) {
		for (int i = 0; i < sepChar.length; i++) {
			if (ch == sepChar[i])
				return true;
		}

		return false;
	}

	public TableElement createTableElement() {
		return new TableElement();
	}

	public RowElement createRowElement() {
		return new RowElement();
	}

	public CellElement createCellElement() {
		return new CellElement();
	}

}
