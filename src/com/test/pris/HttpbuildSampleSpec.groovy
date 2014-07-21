package com.test.pris

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovyx.net.http.HTTPBuilder
import spock.lang.Specification

class HttpbuildSampleSpec extends Specification{
	HTTPBuilder http = new HTTPBuilder( 'http://m.weather.com.cn' )
	public void testRequestWeather(){
		when:
		def info =""
		http.request( GET, JSON ) {
			url.path = '/data/1012904011.html'
			headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'
			response.success = { resp, json ->
				info = json.weatherinfo.city
			}
			response.failure = { resp,json -> println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}" }
		}
		then:  "曲靖"==info
	}
}
