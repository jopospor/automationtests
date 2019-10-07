package com.contaazul.auto.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class LineReader {

	public LineReader(InputStream inStream) {
		this.inStream = inStream;
		inByteBuf = new byte[8192];
	}

	public LineReader(Reader reader) {
		this.reader = reader;
		inCharBuf = new char[8192];
	}

	byte[] inByteBuf;
	char[] inCharBuf;
	public char[] lineBuffer = new char[1024];
	int inLimit = 0;
	int inCurrentChar = 0;
	InputStream inStream;
	Reader reader;

	public int getLineLen() throws IOException {
		int len = 0;
		char c = 0;

		boolean skipWhiteSpace = true;
		boolean isCommentLine = false;
		boolean isNewLine = true;
		boolean appendedLineBegin = false;
		boolean precedingBackslash = false;
		boolean skipLineFinish = false;

		while (true) {
			if (inCurrentChar >= inLimit) {
				inLimit = whatsTheInLimitNow();
				inCurrentChar = 0;
				if (isExceeded( inLimit, len, isCommentLine ))
					return -1;
				return len;
			}
			c = nextC();
			if (skipLineFinish) {
				skipLineFinish = false;
				if (c == '\n')
					continue;
			}
			if (skipWhiteSpace) {
				if (c == ' ' || c == '\t' || c == '\f')
					continue;
				if (!appendedLineBegin && (c == '\r' || c == '\n'))
					continue;
				skipWhiteSpace = false;
				appendedLineBegin = false;
			}
			if (isNewLine) {
				isNewLine = false;
				if (c == '#' || c == '!') {
					isCommentLine = true;
					continue;
				}
			}

			if (c != '\n' && c != '\r') {
				lineBuffer[len++] = c;
				updateLineBuffer( len, c );
				precedingBackslash = updatePrecedingBackslash( c, precedingBackslash );
			} else {
				if (isCommentLine || len == 0) {
					isCommentLine = false;
					isNewLine = true;
					skipWhiteSpace = true;
					len = 0;
					continue;
				}
				if (inCurrentChar >= inLimit) {
					inLimit = whatsTheInLimitNow();
					inCurrentChar = 0;
					if (inLimit <= 0)
						return len;
				}
				if (precedingBackslash) {
					len -= 1;
					skipWhiteSpace = true;
					appendedLineBegin = true;
					precedingBackslash = false;
					if (c == '\r')
						skipLineFinish = true;
				} else {
					return len;
				}
			}
		}
	}

	private char nextC() {
		char c;
		if (inStream != null)
			c = (char) (0xff & inByteBuf[inCurrentChar++]);// ISO8859-1
		else
			c = inCharBuf[inCurrentChar++];
		return c;
	}

	private boolean isExceeded(int inLimit, int len, boolean isCommentLine) {
		if (inLimit <= 0)
			if (len == 0 || isCommentLine)
				return true;
		return false;
	}

	private int whatsTheInLimitNow() throws IOException {
		return (inStream == null) ? reader.read( inCharBuf ) : inStream.read( inByteBuf );
	}

	private void updateLineBuffer(int len, char c) {
		if (len == lineBuffer.length) {
			int newLength = lineBuffer.length * 2;
			if (newLength < 0)
				newLength = Integer.MAX_VALUE;
			final char[] buf = new char[newLength];
			System.arraycopy( lineBuffer, 0, buf, 0, lineBuffer.length );
			lineBuffer = buf;
		}
	}

	private boolean updatePrecedingBackslash(char c, boolean precedingBackslash) {
		if (c == '\\')
			return !precedingBackslash;
		else
			return false;
	}

	public static String loadConvert(char[] in, int off, int len, char[] convtBuf) {
		if (convtBuf.length < len) {
			int newLen = len * 2;
			if (newLen < 0)
				newLen = Integer.MAX_VALUE;
			convtBuf = new char[newLen];
		}
		char aChar;
		char[] out = convtBuf;
		int outLen = 0;
		int end = off + len;

		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = in[off++];
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException( "Malformed \\uxxxx encoding." );
						}
					}
					out[outLen++] = (char) value;
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = (char) aChar;
			}
		}
		return new String( out, 0, outLen );
	}

	public boolean isCharLeadingWhiteSpace(char firstChar, boolean precedingBackslash) {
		return (firstChar == ' ' || firstChar == '\t' || firstChar == '\f') && !precedingBackslash;
	}

	public boolean isCharSeparator(char firstChar, boolean precedingBackslash) {
		return (firstChar == '=' || firstChar == ':') && !precedingBackslash;
	}

	public boolean hasSeparator(int valueStart, boolean hasSeparator) {
		char tempChar;
		tempChar = this.lineBuffer[valueStart];
		if (tempChar != ' ' && tempChar != '\t' && tempChar != '\f')
			if (!hasSeparator && (tempChar == '=' || tempChar == ':'))
				return true;
		return false;
	}

}
