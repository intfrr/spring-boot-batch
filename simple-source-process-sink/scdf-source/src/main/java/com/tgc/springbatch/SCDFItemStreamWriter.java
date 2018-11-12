/**
 * 
 */
package com.tgc.springbatch;

import java.io.File;
import java.util.List;

import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.stereotype.Component;

/**
 * @author rtalapaneni
 *
 */
@Component
public class SCDFItemStreamWriter extends AbstractItemStreamItemWriter<SimpleBean> {
	public static final String DIRECTORY = "C:\\Users\\rtalapaneni\\Documents\\SCDF\\source_output\\";
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List<? extends SimpleBean> items) throws Exception {
		if (items != null && !items.isEmpty()) {
			for(SimpleBean fileName : items) {
				File file = new File(DIRECTORY + fileName.getName());
				file.createNewFile();
			}
		}
	}

}
