/**
 * Copyright (C) 2011 ArtiVisi Intermedia <info@artivisi.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.artivisi.ppob.simulator.gateway.pln;

import java.util.Date;
import static org.junit.Assert.*;

import org.joda.time.format.DateTimeFormat;
import org.jpos.iso.ISOMsg;
import org.junit.Test;

import com.artivisi.biller.simulator.gateway.pln.constants.MTIConstants;
import com.artivisi.biller.simulator.gateway.pln.constants.ResponseCode;
import com.artivisi.biller.simulator.gateway.pln.jpos.PlnChannel;

public class InquiryPostpaidTest extends BaseTest {
	
	@Test
	public void testInquiryNormal() throws Exception {
		String idpel = "123456789011";
		String switcher = "ARTIVIS";
		String bank = "BANKABC";
		
		ISOMsg request = new ISOMsg();
		request.setMTI(MTIConstants.INQUIRY_REQUEST);
		request.set(2, "51501");
		request.set(11, "123456789012");
		request.set(12, DateTimeFormat.forPattern("yyyyMMddHHmmss").print(new Date().getTime()));
		request.set(26, "6012");
		request.set(32, bank);
		request.set(48, switcher + idpel);
		
		PlnChannel channel = createChannel();
		channel.connect();
		channel.send(request);
		ISOMsg response = channel.receive();
		channel.disconnect();
		assertEquals(ResponseCode.SUCCESSFUL, response.getString(39));
		
	}
	
	@Test
	public void testInquiryInvalidProduk() throws Exception {
		String idpel = "123456789011";
		String switcher = "ARTIVIS";
		String bank = "BANKABC";
		
		ISOMsg request = new ISOMsg();
		request.setMTI(MTIConstants.INQUIRY_REQUEST);
		request.set(2, "51999");
		request.set(11, "123456789012");
		request.set(12, DateTimeFormat.forPattern("yyyyMMddHHmmss").print(new Date().getTime()));
		request.set(26, "6012");
		request.set(32, bank);
		request.set(48, switcher + idpel);
		
		PlnChannel channel = createChannel();
		channel.connect();
		channel.send(request);
		ISOMsg response = channel.receive();
		channel.disconnect();
		assertEquals(ResponseCode.ERROR_UNREGISTERED_PRODUCT, response.getString(39));
		
	}
	
	@Test
	public void testInquiryIdpelTidakAda() throws Exception {
		String idpel = "999999999999";
		String switcher = "ARTIVIS";
		String bank = "BANKABC";
		
		ISOMsg request = new ISOMsg();
		request.setMTI(MTIConstants.INQUIRY_REQUEST);
		request.set(2, "51501");
		request.set(11, "123456789012");
		request.set(12, DateTimeFormat.forPattern("yyyyMMddHHmmss").print(new Date().getTime()));
		request.set(26, "6012");
		request.set(32, bank);
		request.set(48, switcher + idpel);
		
		PlnChannel channel = createChannel();
		channel.connect();
		channel.send(request);
		ISOMsg response = channel.receive();
		channel.disconnect();
		assertEquals(ResponseCode.ERROR_UNKNOWN_SUBSCRIBER, response.getString(39));
		
	}

	@Test
	public void testInquiryUnregisteredSwitcher() throws Exception {
		String idpel = "123456789011";
		String switcher = "JMI1234";
		String bank = "BANKABC";
		
		ISOMsg request = new ISOMsg();
		request.setMTI(MTIConstants.INQUIRY_REQUEST);
		request.set(2, "51501");
		request.set(11, "123456789012");
		request.set(12, DateTimeFormat.forPattern("yyyyMMddHHmmss").print(new Date().getTime()));
		request.set(26, "6012");
		request.set(32, bank);
		request.set(48, switcher + idpel);
		
		PlnChannel channel = createChannel();
		channel.connect();
		channel.send(request);
		ISOMsg response = channel.receive();
		channel.disconnect();
		assertEquals(ResponseCode.ERROR_UNREGISTERED_SWITCHING, response.getString(39));
		
	}

	@Test
	public void testInquiryUnregisteredBank() throws Exception {
		String idpel = "123456789011";
		String switcher = "ARTIVIS";
		String bank = "BANKBRI";
		
		ISOMsg request = new ISOMsg();
		request.setMTI(MTIConstants.INQUIRY_REQUEST);
		request.set(2, "51501");
		request.set(11, "123456789012");
		request.set(12, DateTimeFormat.forPattern("yyyyMMddHHmmss").print(new Date().getTime()));
		request.set(26, "6012");
		request.set(32, bank);
		request.set(48, switcher + idpel);
		
		PlnChannel channel = createChannel();
		channel.connect();
		channel.send(request);
		ISOMsg response = channel.receive();
		channel.disconnect();
		assertEquals(ResponseCode.ERROR_UNREGISTERED_BANK_CODE, response.getString(39));
		
	}
	
	@Test
	public void testInquiryCutoff() throws Exception {
		String idpel = "123456789013";
		String switcher = "ARTIVIS";
		String bank = "BANKABC";
		
		ISOMsg request = new ISOMsg();
		request.setMTI(MTIConstants.INQUIRY_REQUEST);
		request.set(2, "51501");
		request.set(11, "123456789012");
		request.set(12, DateTimeFormat.forPattern("yyyyMMddHHmmss").print(new Date().getTime()));
		request.set(26, "6012");
		request.set(32, bank);
		request.set(48, switcher + idpel);
		
		PlnChannel channel = createChannel();
		channel.connect();
		channel.send(request);
		ISOMsg response = channel.receive();
		channel.disconnect();
		assertEquals(ResponseCode.ERROR_CUTOFF_INPROGRESS, response.getString(39));
		
	}
	
	@Test
	public void testInquiryTimeout() throws Exception {
		String idpel = "123456789012";
		String switcher = "ARTIVIS";
		String bank = "BANKABC";
		
		ISOMsg request = new ISOMsg();
		request.setMTI(MTIConstants.INQUIRY_REQUEST);
		request.set(2, "51501");
		request.set(11, "123456789012");
		request.set(12, DateTimeFormat.forPattern("yyyyMMddHHmmss").print(new Date().getTime()));
		request.set(26, "6012");
		request.set(32, bank);
		request.set(48, switcher + idpel);
		
		PlnChannel channel = createChannel();
		channel.connect();
		channel.send(request);
		ISOMsg response = channel.receive();
		assertNull(response);
		channel.disconnect();
		
	}
	
}
