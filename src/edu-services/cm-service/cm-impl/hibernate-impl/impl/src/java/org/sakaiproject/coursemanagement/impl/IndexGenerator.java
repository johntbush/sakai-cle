/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/edu-services/trunk/cm-service/cm-impl/hibernate-impl/impl/src/java/org/sakaiproject/coursemanagement/impl/IndexGenerator.java $
 * $Id: IndexGenerator.java 105077 2012-02-24 22:54:29Z ottenhoff@longsight.com $
 ***********************************************************************************
 *
 * Copyright (c) 2007, 2008 The Sakai Foundation
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
package org.sakaiproject.coursemanagement.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.sakaiproject.db.api.SqlService;

/**
 * Hibernate doesn't generate all of the indexes we need, so we'll have to add them
 * manually here.
 * 
 * @author <a href="mailto:jholtzman@berkeley.edu">jholtzman@berkeley.edu</a>
 *
 */
public abstract class IndexGenerator {
	private static final Log log = LogFactory.getLog(IndexGenerator.class);
	
	public abstract SqlService sqlService();
	public abstract ServerConfigurationService scs();
	
	public void init() {
		if(log.isInfoEnabled()) log.info("init()");
		if(scs().getBoolean("auto.ddl", true)) {
			sqlService().ddl(this.getClass().getClassLoader(), "sakai_cm_index");
		}
	}
	
	public void destroy() {
		if(log.isInfoEnabled()) log.info("destroy()");
	}
}