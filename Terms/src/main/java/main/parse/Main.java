package main.parse;

public class Main {


	public AbstractElement go(String fileName) throws Exception {

		AbstractElement root = new TermsParser().parse(fileName);
		root.print();
		new Sql_Connect().input(root);
		return root;
	}
}
