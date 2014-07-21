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
class QueryBookStatusSpec extends Specification{
	HTTPBuilder http = new HTTPBuilder( 'http://test.easyread.163.com' )
	def auth = "qatest103@163.com:qa1234".getBytes("utf-8").encodeBase64()

	public void testArticlePayInfo(){
		when:
		def result = ""
		http.request(POST, JSON) {
			url.path="/book/payinfo2.atom"
			headers."Authorization"=" Basic "+ auth
			JsonBuilder builder = new JsonBuilder()
			builder{
				id "nb_000BOMEC_4"
				updated 0
				sids(
						"003fb4b88c6d4e6e8b8b6b6b6740a235_4",
						"007015061812446c8ee06866ff52a07b_4"
						)
			}
			body = builder.toString()
			response.success = { resp,json->
				result=json
			}
			response.failure = { resp ->
				println resp.toString()
			}
		}
		then:
		def m = result.sids
		m.contains("003fb4b88c6d4e6e8b8b6b6b6740a235_4")
		m.contains("007015061812446c8ee06866ff52a07b_4")
	}
	
	
	public void testWholePayInfo(){
		when:
		def result = ""
		http.request(POST, JSON) {
			url.path="/book/payinfo2.atom"
			headers."Authorization"=" Basic "+ auth
			JsonBuilder builder = new JsonBuilder()
			builder{
				id "bd_01ee07ba-c177-4884-8435-c2fa9dcd30fb_4"
				updated 0
			}
			body = builder.toString()
			response.success = { resp,json->
				result=json
			}
			response.failure = { resp ->
				println resp.toString()
			}
		}
		then:
		result.id == "bd_01ee07ba-c177-4884-8435-c2fa9dcd30fb_4"
	}
	
	public void testQueryArticlePayInfo(){
		when:
		def result = ""
		http.request(POST, JSON) {
			url.path="/book/payinfo2.atom"
			headers."Authorization"=" Basic "+ auth
			JsonBuilder builder = new JsonBuilder()
			builder{
				id "bd_9638f325-824c-40d5-a39c-9404d15a5812_4"
				updated 0
			}
			body = builder.toString()
			response.success = { resp,json->
				print json
				result=json
			}
			response.failure = { resp ->
				println resp.toString()
			}
		}
		then:
		def m = result.sids
		m.size() == 40
		m.contains("yiqian_22274_469909_4")
		m.contains("yiqian_22274_585698_4")
	}
}