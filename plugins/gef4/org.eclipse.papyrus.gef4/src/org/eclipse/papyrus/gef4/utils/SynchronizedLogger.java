package org.eclipse.papyrus.gef4.utils;

import org.eclipse.swt.widgets.Display;

public class SynchronizedLogger {

	static String[] messages = new String[100];

	static int i = 0;

	public static synchronized void log(String message) {
		if (i == 100) {
			return;
		}
		if (Display.getCurrent() == Display.getDefault()) {
			messages[i++] = "[UI] " + message;
		} else {
			messages[i++] = "[Job] " + message;
		}
	}

	public static synchronized void print() {
		i = 0;

		for (int i = 0; i < messages.length; i++) {
			if (messages[i] == null) {
				break;
			}

			System.out.println(messages[i]);
			messages[i] = null;
		}
	}

	public static synchronized void flush() {
		i = 0;

		for (int i = 0; i < messages.length; i++) {
			if (messages[i] == null) {
				return;
			}

			messages[i] = null;
		}
	}
}
