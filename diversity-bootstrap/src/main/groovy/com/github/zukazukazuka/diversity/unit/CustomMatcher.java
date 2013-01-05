package com.github.zukazukazuka.diversity.unit;

import java.io.File;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CustomMatcher {

	public static FileNameMatcher sameFileName(String expected) {
		return new FileNameMatcher(expected);
	}

	public static FileNamesMatcher containsFileName(String expected) {
		return new FileNamesMatcher(expected);
	}

	public static FileMatcher sameFile(String path) {
		return new FileMatcher(path);
	}

	protected static class FileNameMatcher extends TypeSafeMatcher<File> {

		private final String expected;

		public FileNameMatcher(String expected) {
			this.expected = expected;
		}

		public void describeTo(Description description) {
			description.appendText("is contained");
		}

		@Override
		public boolean matchesSafely(File file) {
			return null != file && expected != null
					&& file.getName().equals(expected);
		}
	}

	protected static class FileNamesMatcher extends TypeSafeMatcher<List<File>> {

		private String expected;

		public FileNamesMatcher(String expected) {
			this.expected = expected;
		}

		public void describeTo(Description description) {
			description.appendText("is not contained");
		}

		@Override
		public boolean matchesSafely(List<File> files) {
			if (files == null || files.isEmpty()) {
				return false;
			}

			for (File file : files) {
				if (file.getName().equals(expected)) {
					return true;
				}
			}
			return false;
		}
	}

	protected static class FileMatcher extends TypeSafeMatcher<File> {

		private final File expected;

		public FileMatcher(String expected) {
			this.expected = new File(expected);
		}

		public void describeTo(Description description) {
			try {
				description.appendText("not same "
						+ expected.getCanonicalPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public boolean matchesSafely(File file) {
			if (file == null || !file.exists()) {
				return false;
			}
			if (expected == null && expected.exists()) {
				return false;
			}
			try {
				return file.getCanonicalPath().equals(
						expected.getCanonicalPath());
			} catch (Exception ignore) {

			}
			return false;
		}
	}

}
