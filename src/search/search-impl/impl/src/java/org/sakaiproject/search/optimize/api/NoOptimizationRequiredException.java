/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/search/trunk/search-impl/impl/src/java/org/sakaiproject/search/optimize/api/NoOptimizationRequiredException.java $
 * $Id: NoOptimizationRequiredException.java 105078 2012-02-24 23:00:38Z ottenhoff@longsight.com $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2007, 2008 The Sakai Foundation
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

package org.sakaiproject.search.optimize.api;

import org.sakaiproject.search.indexer.api.IndexJournalException;

/**
 * No Optimization is required for the index at the moment, probably because
 * there are not enough transient indexes to make a merge worthwhile
 * 
 * @author ieb
 */
public class NoOptimizationRequiredException extends IndexJournalException
{

	/**
	 * 
	 */
	public NoOptimizationRequiredException()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public NoOptimizationRequiredException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public NoOptimizationRequiredException(Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public NoOptimizationRequiredException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
