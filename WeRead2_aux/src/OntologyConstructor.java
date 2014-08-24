
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
		OntClass meishi = ontmodel.createClass(uri + "美食");
		OntClass hangbangcai = ontmodel.createClass(uri + "杭帮菜");
		OntClass xiaochi = ontmodel.createClass(uri + "小吃");
		OntClass tese = ontmodel.createClass(uri + "特色");
		OntClass gushi = ontmodel.createClass(uri + "故事");

		meishi.addSubClass(hangbangcai);
		meishi.addSubClass(xiaochi);

		OntProperty has_tese = ontmodel.createDatatypeProperty(uri + "has_tese");
		OntProperty has_gushi = ontmodel.createDatatypeProperty(uri + "has_gushi");

		Individual dongporou = hangbangcai.createIndividual(uri + "东坡肉");
		Individual wushansuyoubing = xiaochi.createIndividual(uri + "吴山酥油饼");

		Individual dongporou_tese = tese.createIndividual(uri + "东坡肉_特色");
		Individual dongporou_gushi = gushi.createIndividual(uri + "东坡肉_故事");

		Individual wushansuyoubing_tese = tese.createIndividual(uri + "吴山酥油饼_特色");
		Individual wushansuyoubing_gushi = gushi.createIndividual(uri + "吴山酥油饼_故事");

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
		// 创建使用OWL语言的内存模型 
		construct_aux();

        try {
        	File dir = new File("output/ontology/");
            if(!dir.exists()) {
            	dir.mkdirs();
            }

        	File file = new File("output/ontology/ontology.owl");
	        ontmodel.write(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
    		System.out.println("本体构建完成！");
        } catch(FileNotFoundException e) {
        	e.printStackTrace();
        } catch(UnsupportedEncodingException e) {
        	e.printStackTrace();
        }
	}

}
