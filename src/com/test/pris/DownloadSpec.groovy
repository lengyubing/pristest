package com.test.pris

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovyx.net.http.HTTPBuilder

import org.apache.http.conn.EofSensorInputStream
import org.spockframework.util.IoUtil

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import spock.lang.Specification

class DownloadSpec extends Specification{
	HTTPBuilder http = new HTTPBuilder( 'http://reader.163.com' )
	public void testRequestWeather(){
		when:
		def info =""
		http.request( GET, BINARY) {
			url.path = '/book/download2.atom'
			url.query=[id:"07e84576-5212-4bac-af47-17fe812796ac_4"]
			headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'
			headers.'Cookie' = 'JSESSIONID=18f7nzfqoaiy41hnf4m9nlnyby;YUEDU_LOGIN=1645556287'
			response.success = { resp, EofSensorInputStream binary ->
				def bytes = []
				int i = binary.read()
				while(i!=-1){
					if(i == 0x24){
						break;
					}
					bytes << i
					i = binary.read();
				}
				IoUtil.copyStream(binary, new FileOutputStream("d:/mmmmm1.zip"))
				def mmm= new String(bytes as byte[],"utf-8")
				String str = new String(Base64.decode(mmm))
				println str
			}
			response.failure = { resp -> println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"  }
		}
		then:  1==1
	}
}
