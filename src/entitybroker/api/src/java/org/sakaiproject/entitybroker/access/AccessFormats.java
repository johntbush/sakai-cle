/**
 * $Id: AccessFormats.java 105077 2012-02-24 22:54:29Z ottenhoff@longsight.com $
 * $URL: https://source.sakaiproject.org/svn/entitybroker/trunk/api/src/java/org/sakaiproject/entitybroker/access/AccessFormats.java $
 * HTMLable.java - entity-broker - Apr 6, 2008 7:37:54 PM - azeckoski
 **************************************************************************
 * Copyright (c) 2008 The Sakai Foundation
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
 */

package org.sakaiproject.entitybroker.access;

import org.sakaiproject.entitybroker.entityprovider.extension.Formats;

/**
 * Indicates that entity requests can be handled for certain formats which are indicated<br/>
 * <br/>
 * <b>NOTE:</b> By default all entity view requests go through to the available access providers: 
 * {@link EntityViewAccessProvider} or {@link HttpServletAccessProvider} if nothing is specified here
 * or this interface is not implemented
 * 
 * @author Aaron Zeckoski (aaron@caret.cam.ac.uk)
 */
public interface AccessFormats extends EntityViewAccessProvider, Formats {

   /**
    * Defines the access format types (extensions) handled by this access provider<br/>
    * The default if this interface is not implemented is to pass through all requests to the
    * access provider that is defined
    * 
    * @return an array containing the format types (from {@link Formats}) handled <br/>
    * OR empty array to indicate all are handled (same as not implementing {@link AccessFormats}) <br/>
    * OR null to indicate none are handled (same as not implementing {@link EntityViewAccessProvider}) <br/>
    * NOTE: use the constants (example: {@link #HTML}) or feel free to make up your own if you like
    */
   public String[] getHandledAccessFormats();

}
