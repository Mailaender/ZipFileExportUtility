/*******************************************************************************
 * Copyright (c) 2013 Vogella GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.vogella.website.generator.projectfiles;

/**
 * Class main
 * 
 * @author Matthias Mailänder
 */
public class main {

	/**
	 * @param Reads the arguments for the ZipFileExportUtility
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Exports Eclipse projects as ZIP files"
					+ " ignoring bin or target folders.");
			System.out.println("Usage: [TARGETDIR] [INPUTDIRS]...");
		} else {
			for (int i = 1; i < args.length; i++)
				new ZipFileExportUtility(args[0], args[i]);
		}
	}
}