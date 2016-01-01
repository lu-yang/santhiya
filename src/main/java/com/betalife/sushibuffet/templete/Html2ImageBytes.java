package com.betalife.sushibuffet.templete;

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
		editorPane = new JEditorPane();
		editorPane.setSize(DEFAULT_SIZE);
		editorPane.setEditable(false);
		final SynchronousHTMLEditorKit kit = new SynchronousHTMLEditorKit();
		editorPane.setEditorKitForContentType("text/html", kit);
		editorPane.setContentType("text/html");
	}

	public void loadHtml(String html) {
		editorPane.setText(html);
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
		ImageIO.write(bufferedImage, "JPEG", baos);
		baos.flush();
		byte[] bytes = baos.toByteArray();
		baos.close();
		return bytes;
	}

	public static void main(String[] args) throws IOException {
		String bmpFile = "c:\\Users\\mbp-bobhao\\Desktop\\java\\test.jpeg";
		String htmlFile = "c:\\Users\\mbp-bobhao\\Desktop\\java\\OrderTemplate.html";
		Html2ImageBytes ut = new Html2ImageBytes();
		String html = FileUtils.readFileToString(new File(htmlFile), "utf-8");
		ut.loadHtml(html);

		BufferedImage bufferedImage = ut.getBufferedImage();
		ImageIO.write(bufferedImage, "JPEG", new File(bmpFile));
	}
}
