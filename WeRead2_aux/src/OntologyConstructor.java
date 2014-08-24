
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;


public class OntologyConstructor {
	private static OntModel ontmodel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

	private static void construct_aux() {
		String uri = "http://com/fatty/ontology.owl#";
		OntClass meishi = ontmodel.createClass(uri + "��ʳ");
		OntClass hangbangcai = ontmodel.createClass(uri + "�����");
		OntClass xiaochi = ontmodel.createClass(uri + "С��");
		OntClass tese = ontmodel.createClass(uri + "��ɫ");
		OntClass gushi = ontmodel.createClass(uri + "����");

		meishi.addSubClass(hangbangcai);
		meishi.addSubClass(xiaochi);

		OntProperty has_tese = ontmodel.createDatatypeProperty(uri + "has_tese");
		OntProperty has_gushi = ontmodel.createDatatypeProperty(uri + "has_gushi");

		Individual dongporou = hangbangcai.createIndividual(uri + "������");
		Individual wushansuyoubing = xiaochi.createIndividual(uri + "��ɽ���ͱ�");

		Individual dongporou_tese = tese.createIndividual(uri + "������_��ɫ");
		Individual dongporou_gushi = gushi.createIndividual(uri + "������_����");

		Individual wushansuyoubing_tese = tese.createIndividual(uri + "��ɽ���ͱ�_��ɫ");
		Individual wushansuyoubing_gushi = gushi.createIndividual(uri + "��ɽ���ͱ�_����");

		dongporou.addProperty(has_tese, dongporou_tese);
		dongporou.addProperty(has_gushi, dongporou_gushi);

		wushansuyoubing.addProperty(has_tese, wushansuyoubing_tese);
		wushansuyoubing.addProperty(has_gushi, wushansuyoubing_gushi);
	}

	/**
	 * @param args
	 */
	public static void construct() {
		// TODO Auto-generated method stub
		// ����ʹ��OWL���Ե��ڴ�ģ�� 
		construct_aux();

        try {
        	File dir = new File("output/ontology/");
            if(!dir.exists()) {
            	dir.mkdirs();
            }

        	File file = new File("output/ontology/ontology.owl");
	        ontmodel.write(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
    		System.out.println("���幹����ɣ�");
        } catch(FileNotFoundException e) {
        	e.printStackTrace();
        } catch(UnsupportedEncodingException e) {
        	e.printStackTrace();
        }
	}

}
