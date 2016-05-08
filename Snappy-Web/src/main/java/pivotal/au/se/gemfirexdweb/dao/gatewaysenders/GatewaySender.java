/*
Copyright (C) MARCH-2014 Pivotal Software, Inc.

All rights reserved. This program and the accompanying materials
are made available under the terms of the under the Apache License,
Version 2.0 (the "License”); you may not use this file except in compliance
with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package pivotal.au.se.gemfirexdweb.dao.gatewaysenders;

public class GatewaySender 
{
	private String senderId;
	private String remoteDSId;
	private String serverGroup;
	private String isStarted;
	
	public GatewaySender(String senderId, String remoteDSId,
			String serverGroup, String isStarted) {
		super();
		this.senderId = senderId;
		this.remoteDSId = remoteDSId;
		this.serverGroup = serverGroup;
		this.isStarted = isStarted;
	}

	public GatewaySender() 
	{
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getRemoteDSId() {
		return remoteDSId;
	}

	public void setRemoteDSId(String remoteDSId) {
		this.remoteDSId = remoteDSId;
	}

	public String getServerGroup() {
		return serverGroup;
	}

	public void setServerGroup(String serverGroup) {
		this.serverGroup = serverGroup;
	}

	public String getIsStarted() {
		return isStarted;
	}

	public void setIsStarted(String isStarted) {
		this.isStarted = isStarted;
	}

	@Override
	public String toString() {
		return "GatewaySender [senderId=" + senderId + ", remoteDSId="
				+ remoteDSId + ", serverGroup=" + serverGroup + ", isStarted="
				+ isStarted + "]";
	}
  
	
  
}
