package com.test.pris

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper;
import groovyx.net.http.HTTPBuilder
import spock.lang.Specification
/**
 * 测试书籍的购买状态
 * 
 * @author zhaopingfei
 *
 */
class MobileErrorReportSpec extends Specification{
	HTTPBuilder http = new HTTPBuilder( 'http://test.easyread.163.com' )

	public void testArticlePayInfo(){
		when:
		def result = ""
		http.request(GET, JSON) {
			url.path="/mobile/errorReport.atom"
			url.query=["bid":"abcdefg","cid":"hellotest","msg":"error","code":"9999"]
			response.failure = { resp,json ->
				println resp.statusline
				result = json
			}
		}
		then:
		result.resDesc == "error"
		result.resCode == 9999
	}
}