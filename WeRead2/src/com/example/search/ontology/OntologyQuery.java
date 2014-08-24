package com.example.search.ontology;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.example.search.po.Book;
import com.example.search.util.Type;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class OntologyQuery {
	private static String uri = "http://com/fatty/ontology.owl#";
	private OntModel ontmodel;
	private String info_dir_path;
	public OntologyQuery(OntModel ontmodel, String info_dir_path) {
		this.ontmodel = ontmodel;
		this.info_dir_path = info_dir_path;
	}

	private String getInfo(String name) {
		String path = this.info_dir_path + "/" + name + ".txt";
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String retString = reader.readLine();
			reader.close();
			return retString;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return "";
	}

	private boolean isIndividual(String name, OntModel ontmodel) {
		for (Iterator i = ontmodel.listIndividuals(); i.hasNext();){
			Individual ind = (Individual)i.next();
			if (ind.getLocalName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	private boolean isClass(String name, OntModel ontmodel) {
		for (Iterator i = ontmodel.listClasses(); i.hasNext();){
			OntClass cls = (OntClass)i.next();
			if (cls.getLocalName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Book> query(String value) {
		ArrayList<Book> retList = new ArrayList<Book>();
		if (isIndividual(value, ontmodel)) {
			System.out.println(value + " is individual");

			Individual res2 = ontmodel.getIndividual(uri + value);
			for(Iterator i = res2.listProperties(); i.hasNext();){
				Statement statement = (Statement)i.next();
				// 主语
				// statement.getSubject().getLocalName();
				// 谓语
				String predicate = statement.getPredicate().getLocalName();
				// 宾语
				String propertyName = statement.getResource().getLocalName();
				Book book = new Book();
				if (predicate.equals("type")) {
					book.setName("");
					book.setContent("属于" + propertyName);
					book.setUrl(propertyName);
					book.setUrlType(Type.UrlType.ontology_class_name);
				} else {
					String name = propertyName.replaceAll("_", "的");
					String content = getInfo(propertyName);
					book.setName(name);
					book.setContent(content);
					book.setUrl(propertyName + ".txt");
					book.setUrlType(Type.UrlType.info_file_path);
				}
				retList.add(book);
			}

			/*
			OntClass res = ontmodel.createClass(uri + value); 
			for(Iterator i = res.listDeclaredProperties(); i.hasNext();){
				OntProperty p = (OntProperty)i.next();
				for (Iterator j = res.listPropertyValues(p); j.hasNext();){
					Resource r = (Resource)j.next();
					String propertyName = r.getLocalName();
					String name = propertyName.replaceAll("_", "的");
					String content = getInfo(propertyName);
					Book book = new Book();
					book.setName(name);
					book.setContent(content);
					book.setUrl(propertyName + ".txt");
					book.setUrlType(UrlType.info_file_path);
					retList.add(book);
				}
			}
			*/
		} else if (isClass(value, ontmodel)) {
			System.out.println(value + " is class");
			OntClass res = ontmodel.getOntClass(uri + value); 

			for (Iterator it = res.listSubClasses(); it.hasNext();){
				OntClass oc = (OntClass) it.next();
				String name = oc.getLocalName();
				Book book = new Book();
				book.setName("");
				book.setContent(name);
				book.setUrl(name);
				book.setUrlType(Type.UrlType.ontology_class_name);
				retList.add(book);
			}
			for(Iterator i = ontmodel.listIndividuals(res); i.hasNext();){
				Individual id = (Individual)i.next();
				String name = id.getLocalName();
				Book book = new Book();
				book.setName("");
				book.setContent(name);
				book.setUrl(name);
				book.setUrlType(Type.UrlType.ontology_class_name);
				retList.add(book);
			}
		}
		return retList;
	}
}
