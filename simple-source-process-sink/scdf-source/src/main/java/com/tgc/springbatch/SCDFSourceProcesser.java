/**
 * 
 */
package com.tgc.springbatch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author rtalapaneni
 *
 */
@Component
public class SCDFSourceProcesser implements ItemProcessor<SimpleBean, SimpleBean> {

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public SimpleBean process(SimpleBean item) throws Exception {
		return new SimpleBean(item.getName().toUpperCase());
	}

}
