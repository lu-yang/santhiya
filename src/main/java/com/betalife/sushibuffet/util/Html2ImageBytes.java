package com.betalife.sushibuffet.util;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;

import org.apache.commons.io.FileUtils;

public class Html2ImageBytes {
	private JEditorPane editorPane;
	static final Dimension DEFAULT_SIZE = new Dimension(800, 800);

	public Html2ImageBytes() {
		editorPane = createJEditorPane();
	}

	public ComponentOrientation getOrientation() {
		return editorPane.getComponentOrientation();
	}

	public void setOrientation(ComponentOrientation orientation) {
		editorPane.setComponentOrientation(orientation);
	}

	public Dimension getSize() {
		return editorPane.getSize();
	}

	public void setSize(Dimension dimension) {
		editorPane.setSize(dimension);
	}

	public void loadHtml(String html) {
		editorPane.setText(html);
	}

	public Dimension getDefaultSize() {
		return DEFAULT_SIZE;
	}

	public BufferedImage getBufferedImage() {
		Dimension prefSize = editorPane.getPreferredSize();
		BufferedImage img = new BufferedImage(250, editorPane.getPreferredSize().height,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphics = img.getGraphics();
		editorPane.setSize(prefSize);
		editorPane.paint(graphics);
		return img;
	}

	public byte[] getBytes() throws IOException {
		BufferedImage bufferedImage = getBufferedImage();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "BMP", baos);
		baos.flush();
		byte[] bytes = baos.toByteArray();
		baos.close();
		return bytes;
	}

	protected JEditorPane createJEditorPane() {
		final JEditorPane editorPane = new JEditorPane();
		editorPane.setSize(getDefaultSize());
		editorPane.setEditable(false);
		final SynchronousHTMLEditorKit kit = new SynchronousHTMLEditorKit();
		editorPane.setEditorKitForContentType("text/html", kit);
		editorPane.setContentType("text/html");
		return editorPane;
	}

	public static void main(String[] args) throws IOException {
		String bmpFile = "c:\\Users\\mbp-bobhao\\Desktop\\java\\test.bmp";
		String htmlFile = "c:\\Users\\mbp-bobhao\\Desktop\\java\\OrderTemplate.html";
		Html2ImageBytes ut = new Html2ImageBytes();
		String html = FileUtils.readFileToString(new File(htmlFile), "utf-8");
		ut.loadHtml(html);

		BufferedImage bufferedImage = ut.getBufferedImage();
		ImageIO.write(bufferedImage, "BMP", new File(bmpFile));
	}
}
