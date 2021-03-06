import java.io.File

import javax.imageio.ImageIO

import java.awt.Graphics2D
import java.awt.Color
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.awt.geom.Line2D

import org.w3c.dom.Document
import org.w3c.dom.DOMImplementation

import org.apache.batik.svggen.SVGDescriptor
import org.apache.batik.svggen.SVGGraphics2D
import org.apache.batik.dom.GenericDOMImplementation

import at.no5.oikumene.projection.Projection
import at.no5.oikumene.projection.Ptolemys2nd

/**
 * Just a dummy script that renders the coordinate grid to SVG
 */
DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation()
String svgNS = "http://www.w3.org/2000/svg";
Document document = domImpl.createDocument(svgNS, "svg", null);

SVGGraphics2D g = new SVGGraphics2D(document);
g.setPaint(new Color(255, 255, 255));
g.fill(new Rectangle(640, 480));
g.setPaint(new Color(0, 0, 0));

def proj = new Ptolemys2nd(scale:3, offsetX: 320, offsetY: -200)

// Parallels
for (int j=60; j>=-10; j-=10) {
	for (int i=-90; i<=80; i+=10) {
		g.draw(new Line2D.Double(proj.toXY(j, i), proj.toXY(j, i + 10)));
	}
}
        
// Meridians
for (int i=-90; i<=90; i+=10) {
	for (int j=60; j>-10; j-=10) {
		g.draw(new Line2D.Double(proj.toXY(j, i), proj.toXY(j - 10, i)));
	}
}   

boolean useCSS = true; // we want to use CSS style attributes
Writer out = new OutputStreamWriter(System.out, "UTF-8");
g.stream(out, useCSS);

