/*******************************************************************************
 * Copyright (c) 2013 Vogella GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.vogella.website.generator.projectfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Class ZipFileExportUtility
 * 
 * @author Matthias Mailänder
 */
public class ZipFileExportUtility {

	List<File> filesInDir = new ArrayList<File>();

	/**
	 * Constructor which represents the Object that creates the .zip files.
	 * 
	 * @param targetDir
	 *            the directory where the .zip is stored
	 * @param inputDir
	 *            the input directory with the source files
	 */
	public ZipFileExportUtility(String targetDir, String inputDir) {
		String targetZipFileName = targetDir + File.separator
				+ new File(inputDir).getName() + ".zip";
		zipDirectory(new File(inputDir), targetZipFileName);
	}

	/**
	 * Compresses the directory to a .zip file.
	 * 
	 * @param dirToZip
	 * @param outputZipFileName
	 */
	private void zipDirectory(File dirToZip, String outputZipFileName) {
		try {
			populateSourceFilesList(dirToZip);

			FileOutputStream fileOutStream = new FileOutputStream(
					outputZipFileName);
			ZipOutputStream zipOutStream = new ZipOutputStream(fileOutStream);

			for (File file : filesInDir) {
				System.out.println("Compressing " + file.getAbsolutePath());
				String relative = dirToZip.toURI().relativize(file.toURI())
						.getPath();
				ZipEntry entry = new ZipEntry(relative);
				System.out.println("as " + relative);
				zipOutStream.putNextEntry(entry);
				FileInputStream fileInStream = new FileInputStream(
						file.getAbsolutePath());

				byte[] buffer = new byte[1024];
				int length;
				while ((length = fileInStream.read(buffer)) > 0) {
					zipOutStream.write(buffer, 0, length);
				}

				zipOutStream.closeEntry();
				fileInStream.close();
			}

			zipOutStream.close();
			fileOutStream.close();
			System.out.println("Wrote " + outputZipFileName);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Populates all source files in a directory to a List by ignoring "target"
	 * and "bin" directories.
	 * 
	 * @param dir
	 * @throws IOException
	 */
	private void populateSourceFilesList(File dir) throws IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory() && !file.toString().contains("bin")
					&& !file.toString().contains("target")) {
				populateSourceFilesList(file);
			} else if (file.isFile()) {
				filesInDir.add(file);
			}
		}
	}
}
