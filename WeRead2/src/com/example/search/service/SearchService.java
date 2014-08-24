package com.example.search.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.example.search.adapter.SearchResultListViewAdapter;
import com.example.search.ontology.OntologyQuery;
import com.example.search.po.Book;
import com.example.search.util.LuceneConfigurationUtil;
import com.example.search.util.LuceneDocumentUtil;
import com.example.search.util.LuneceHighlighterUtil;
import com.example.search.util.MyUrl;
import com.example.search.util.UrlType;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SearchService {
	private final class AsyncTaskExtension extends AsyncTask {
		private String value;
		private String cur_book_dir_path;
		private String lucene_index_dir_path;
		private String ontology_file_path;
		private String info_dir_path;
		private int searchType;
        private ArrayList<Book> search_result = new ArrayList<Book>();
		
		public AsyncTaskExtension(String value, String cur_book_dir_path, int searchType) {
			super();
			this.value = value;
			this.cur_book_dir_path = cur_book_dir_path;
			this.searchType = searchType;

			this.lucene_index_dir_path = "file://" + this.cur_book_dir_path + "/lucene_index";
			this.ontology_file_path = "file://" + this.cur_book_dir_path + "/ontology/ontology.owl";
			this.info_dir_path = this.cur_book_dir_path + "/info";
		}

		private void SearchUsingOntology() {
			OntModel ontmodel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
			ontmodel.read(this.ontology_file_path);

			OntologyQuery ontologyQuery = new OntologyQuery(ontmodel, this.info_dir_path);
			search_result = ontologyQuery.query(value);
			ontmodel.close();
		}

		private void SearchUsingLucene() {
			IndexSearcher indexSearcher = null;
			QueryParser queryParser = null;
			Query query = null;

			try {
				// ͨ���������ȡindexSearcher
				File targetDir = new File(lucene_index_dir_path);
				// ��������
				Directory directory = FSDirectory.open(targetDir);
				indexSearcher = new IndexSearcher(directory);

				queryParser = new QueryParser(Version.LUCENE_30, "content", LuceneConfigurationUtil.getAnalyzer());
				// ����queryParser�������ݹ����ļ����ؼ��֣����Query����ķ�װ
				query = queryParser.parse(value);

				TopDocs topDocs = indexSearcher.search(query, null, 1000);

				// ScoreDoc �洢���ĵ����߼���ź��ĵ�����
				ScoreDoc[] scoreDocs = topDocs.scoreDocs;
				for (ScoreDoc temp : scoreDocs) {
					// ����doc��idȥ��ȡdoc�ĵ� ,������Document
					Document doc = indexSearcher.doc(temp.doc);

					// ͨ�����������ùؼ��ָ���
					doc.getField("name").setValue(
							LuneceHighlighterUtil.highlighterText(query,
									doc.get("name"), 10, value));

					doc.getField("content").setValue(
							LuneceHighlighterUtil.highlighterText(query,
									doc.get("content"), 30, value));

					search_result.add(LuceneDocumentUtil.DocumentToBook(doc));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					indexSearcher.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		@Override
		protected Object doInBackground(Object... params) {
			SearchUsingOntology();

			if (this.searchType == 1) {
				// SearchUsingLucene();
			}
			return null;
		}

		protected void onPostExecute(Object result) {
			// ����ҵ��㷽�������������ӵ�ListView
			addBookToListView(search_result);
		}
	}

	private Context context = null;
	private ListView bookListView = null;
	private String cur_book_dir_path;
	private ArrayList<MyUrl> url = null;

	public SearchService(Context context, ListView bookListView, String cur_book_dir_path) {
		super();
		this.context = context;
		this.bookListView = bookListView;
		this.cur_book_dir_path = cur_book_dir_path;
	}

	/**
	 * 
	 * @function:���鱾��Ϣ��ӵ�ListView
	 * @author:Jerry
	 * @date:2013-9-24
	 * @param books
	 */
	public void addBookToListView(ArrayList<Book> books) {
		int count = books.size();
		if (count <= 0) {
			Toast.makeText(this.context, "û�з������ݣ�", Toast.LENGTH_SHORT).show();
			return;
		}

		// listItem���ڴ��books
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		// ÿ���鱾��Ϣ��Ӧ����ϸ��Ϣ����
		this.url = new ArrayList<MyUrl>();
		// ѭ������books������ֵ�Ե���ʽ����HashMap
		for (Book book : books) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("name", book.getName());
			item.put("content", book.getContent());
			url.add(new MyUrl(book.getUrl(), book.getUrlType()));
			System.out.println("name: " + book.getName());
			System.out.println("content: " + book.getContent());
			System.out.println("url: " + book.getUrl());
			System.out.println("urlType: " + book.getUrlType());

			// ��item������ݼӵ�listItem��
			listItem.add(item);
		}

		// �½��Զ���ViewAdapter
		SearchResultListViewAdapter listViewAdapter = new SearchResultListViewAdapter(this.context, listItem);

		// ��������װ�ص�listView��
		this.bookListView.setAdapter(listViewAdapter);

		// Ϊÿ��item��ע�����¼�
		this.bookListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// ���û����ĳ��item��ʱ�������������ҳ���鿴����ϸ����Ϣ
				int cur_url_type = url.get(arg2).getType();
				String url_path = url.get(arg2).getPath();
				if (cur_url_type == UrlType.html_file_path) {
					String html_file_path = "file://" + cur_book_dir_path + "/" + url_path;
					Toast.makeText(context, "չʾ " + html_file_path + "��ҳ", Toast.LENGTH_LONG).show();
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setData(Uri.parse(html_file_path));
					System.out.println(cur_book_dir_path + "/" + url_path);
					intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
					context.startActivity(intent);
				} else if (cur_url_type ==  UrlType.info_file_path) {
					// ����Ǹ������ļ���չʾ����ļ�
					String info_file_path = "file://" + cur_book_dir_path + "/info/" + url_path;
					Toast.makeText(context, "չʾ " + info_file_path + "�ļ�����", Toast.LENGTH_LONG).show();
				} else if (cur_url_type ==  UrlType.ontology_class_name) {
					// ����Ǹ������е��࣬�������������
					queryBook(url_path, 2);
				} else {
					// ��֪��ʲô�������������һ�㲻�ᷢ��
					Toast.makeText(context, "�Ƿ��� URL", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	/**
	 * 
	 * @function:��ѯ�鱾��Ϣ
	 * @author:Jerry
	 * @date:2013-9-24
	 * @param key
	 * @param value
	 * @return ArrayList
	 */
	public void queryBook(String value, int searchType) {
		// searchType ���� 1  ��ʱ�򣬱�ʾ���塢lucene ��Ҫ����
		// searchType ���� 2  ��ʱ�򣬱�ʾֻ���б������
		new AsyncTaskExtension(value, cur_book_dir_path, searchType).execute();
	}

}
