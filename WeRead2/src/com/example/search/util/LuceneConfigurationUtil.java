package com.example.search.util;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import android.os.Environment;

/**
 * 工具类，用于管理Analyzer和Directory
 */
public class LuceneConfigurationUtil {
	private static Analyzer analyzer; // 分词器

	public static Analyzer getAnalyzer() {
		return analyzer;
	}

	static {
		// 新建一个分词器
		analyzer = new IKAnalyzer(true);
	}
}
