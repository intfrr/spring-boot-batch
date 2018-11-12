/**
 * 
 */
package com.tgc.springbatch;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.stereotype.Component;

/**
 * @author rtalapaneni
 *
 */
@Component
public class SCDFSourceDirectoryReader extends AbstractItemCountingItemStreamItemReader<SimpleBean>{
	public static final Logger LOG = LoggerFactory.getLogger(SCDFSourceDirectoryReader.class);
	
	public static final String DIRECTORY = "C:\\Users\\rtalapaneni\\Documents\\SCDF\\source_input";
	private File[] files = null;
	private int currentIndex = -1;
	
	public SCDFSourceDirectoryReader() {
		setName("Item Reader");
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader#doRead()
	 */
	@Override
	protected SimpleBean doRead() throws Exception {
		LOG.info("Inside doRead method");
		if (files != null && files.length > 0 && files.length > currentIndex) {
			String name = files[currentIndex].getName();
			currentIndex++;
			LOG.info("Found file " + name);
			return new SimpleBean(name);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader#doOpen()
	 */
	@Override
	protected void doOpen() throws Exception {
		LOG.info("Inside doOpen method");
		File directory = new File(DIRECTORY);
		files = directory.listFiles();
		LOG.info("Files found - " + files);
		currentIndex = 0;
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader#doClose()
	 */
	@Override
	protected void doClose() throws Exception {
		LOG.info("inside doClose");
		currentIndex = -1;
		for (File file : files) {
			file.delete();
		}
	}
}
