import java.util.List;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 创建索引
		List<Book> bookList = BookReader.loadBooks();
		LuceneService.createIndexFile(bookList);

		OntologyConstructor.construct();
		// 创建本体
		System.out.println("done");
	}

}
