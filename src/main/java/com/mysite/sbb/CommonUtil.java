package com.mysite.sbb;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;


@Component
public class CommonUtil {

	// Bean에 객체 등록 : 소문자 commonUtil(객체명)
	// @Component, @Controller, @Service, @ Repository

	public String markdown(String markdown) {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdown);
		HtmlRenderer render = HtmlRenderer.builder().build();
		return render.render(document);
	}
}
