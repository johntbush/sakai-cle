/**
 * $Id: Order.java 105077 2012-02-24 22:54:29Z ottenhoff@longsight.com $
 * $URL: https://source.sakaiproject.org/svn/entitybroker/trunk/api/src/java/org/sakaiproject/entitybroker/entityprovider/search/Order.java $
 * Order.java - genericdao - Aug 3, 2008 12:43:54 PM - azeckoski
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

package org.sakaiproject.entitybroker.entityprovider.search;

/**
 * A simple bean which defines the order to return the results of a search<br/>
 * Example usage:<br/>
 * <code>Order ota = new Order("title"); // order by title ascending</code><br/>
 * <code>Order otd = new Order("title", false); // order by title descending</code><br/>
 *
 * @author Aaron Zeckoski (azeckoski@gmail.com)
 */
public class Order {

   /**
    * the name of the field (property) in the persisted object
    */
   public String property;
   /**
    * the name of the field (property) in the persisted object
    */
   public String getProperty() {
      return property;
   }
   public void setProperty(String property) {
      this.property = property;
   }

   /**
    * if true then the return order is ascending,
    * if false then return order is descending
    */
   public boolean ascending = true;
   /**
    * if true then the return order is ascending,
    * if false then return order is descending
    */
   public boolean isAscending() {
      return ascending;
   }
   public void setAscending(boolean ascending) {
      this.ascending = ascending;
   }

   /**
    * a simple order for a property which is ascending
    * @param property the name of the field (property) in the persisted object
    */
   public Order(String property) {
      this.property = property;
      this.ascending = true;
   }

   /**
    * define an order for a property
    * @param property the name of the field (property) in the persisted object
    * @param ascending if true then the return order is ascending,
    * if false then return order is descending
    */
   public Order(String property, boolean ascending) {
      this.property = property;
      this.ascending = ascending;
   }

   @Override
   public boolean equals(Object obj) {
      if (null == obj)
         return false;
      if (!(obj instanceof Order))
         return false;
      else {
         Order castObj = (Order) obj;
         boolean eq = (this.property == null ? castObj.property == null : this.property.equals(castObj.property))
               && this.ascending == castObj.ascending;
         return eq;
      }
   }

   @Override
   public int hashCode() {
      String hashStr = this.getClass().getName() + ":" + this.property + ":" + this.ascending;
      return hashStr.hashCode();
   }

   @Override
   public String toString() {
      return "order::prop:" + property + ",asc:" + ascending;
   }

}
