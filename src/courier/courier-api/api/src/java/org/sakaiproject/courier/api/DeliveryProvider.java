/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/courier/trunk/courier-api/api/src/java/org/sakaiproject/courier/api/DeliveryProvider.java $
 * $Id: DeliveryProvider.java 105079 2012-02-24 23:08:11Z ottenhoff@longsight.com $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.courier.api;

import java.util.List;

/**
 * 
 *
 */
public interface DeliveryProvider {
	
	/**
	 * Access the Deliveries queued up for a particular session client window.
	 * 
	 * @param address
	 *        The address of client window.
	 * @return a List of Delivery objects addressed to this session client window.
	 */
	public List<Delivery> getDeliveries(String sessionId, String placementId);

}
