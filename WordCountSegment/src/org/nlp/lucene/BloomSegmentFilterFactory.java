package org.nlp.lucene;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoaderAware;
import org.apache.lucene.analysis.util.TokenFilterFactory;
import org.nlp.impl.BloomSegmentImpl;

public class BloomSegmentFilterFactory extends TokenFilterFactory implements
		ResourceLoaderAware {

	String modelFile = "model";
	int mode = 0;

	@Override
	public void init(Map<String, String> args) {
		super.init(args);
		assureMatchVersion();
	}

	@Override
	public void inform(ResourceLoader loader) throws IOException {
		String modelFile = args.get("words");
		if (modelFile != null && !modelFile.isEmpty()) {
			this.modelFile = modelFile ;
		}
				
		String mode = args.get("mode");
		
		if (mode != null && !mode.isEmpty()) {
									
			this.mode = Integer.parseInt(mode);
		}
		
		BloomSegmentImpl.initDic(modelFile);
	}
	
	public TokenStream create(TokenStream input) {
			return new BloomSegmentFilter(input,mode);
	}
}
