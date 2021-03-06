/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/portal/trunk/portal-util/util/src/java/org/sakaiproject/portal/util/PortalSiteHelper.java $
 * $Id: PortalSiteHelper.java 21708 2007-02-18 21:59:28Z ian@caret.cam.ac.uk $
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
 
package org.sakaiproject.chat2.tool;

import org.sakaiproject.chat2.model.ChatChannel;

public class DecoratedChatChannel {

   private ChatChannel chatChannel;
   private ChatTool chatTool;
   private int filterParamPast;
   private int filterParamLast;
   private int filterParamNone = 0;
   private boolean directEdit = false;
   private boolean newChannel = false;

   public DecoratedChatChannel(ChatTool chatTool, ChatChannel chatChannel)
   {
      this.chatTool = chatTool;
      this.chatChannel = chatChannel;
   }
   
   public DecoratedChatChannel(ChatTool chatTool, ChatChannel chatChannel, boolean newChannel)
   {
      this.chatTool = chatTool;
      this.chatChannel = chatChannel;
      this.newChannel = newChannel;
   }
   
   public String processActionEnterRoom()
   {
      return chatTool.processActionEnterRoom(this);
   }
   
   /**
    * This method will edit the room from the page listing all the rooms
    * @return
    */
   public String processActionEditRoom()
   {
      directEdit = false;
      return chatTool.processActionEditRoom(this);
   }

   /**
    * This method will edit the room directly from the main chat page
    * @return
    */
   public String processActionEditRoomDirect()
   {
      //Setting this to false to the UI doesn't change too much
      //directEdit = true;
      directEdit = false;
      return chatTool.processActionEditRoom(this);
   }
   
   public String processActionDeleteRoom()
   {
      return chatTool.processActionDeleteRoomConfirm(this);
   }
   
   public String processActionDeleteRoomMessages()
   {
      return chatTool.processActionDeleteRoomMessagesConfirm(this);
   }
   
   public String processActionSetAsDefaultRoom() {
      return chatTool.processActionSetAsDefaultRoom(this);
   }
   
   public ChatChannel getChatChannel()
   {
      return chatChannel;
   }
   
   public ChatTool getChatTool()
   {
      return chatTool;
   }
   
   public boolean getCanDelete() {
      return chatTool.getCanRemoveChannel(chatChannel);
   }

   public boolean getCanDeleteMessages() {
      return chatTool.getCanRemoveChannelMessages(chatChannel);
   }
   
   public boolean getCanEdit() {
      return chatTool.getCanEditChannel(chatChannel);
   }

   public boolean getCanRead() {
      return chatTool.getCanRead(chatChannel);
   }
   
   /**
    * Returns the bundle message with key "enter_the_chat_room", inserting the chat channel's title
    * @return
    */
   public String getEnterChatText() {
      return chatTool.getMessageFromBundle("enter_the_chat_room", new Object[]{chatChannel.getTitle()});
   }
   
   public String getSetAsDefaultText() {
      return chatTool.getMessageFromBundle("set_as_default", new Object[]{chatChannel.getTitle()});
   }

   public int getFilterParamLast() {
      return filterParamLast;
   }

   public void setFilterParamLast(int filterParamLast) {
      this.filterParamLast = filterParamLast;
   }

   public int getFilterParamPast() {
      return filterParamPast;
   }

   public void setFilterParamPast(int filterParamPast) {
      this.filterParamPast = filterParamPast;
   }

   public int getFilterParamNone() {
      return filterParamNone;
   }

   public void setFilterParamNone(int filterParamNone) {
      this.filterParamNone = filterParamNone;
   }

   public boolean isDirectEdit() {
      return directEdit;
   }

   public void setDirectEdit(boolean directEdit) {
      this.directEdit = directEdit;
   }

   public boolean isNewChannel() {
      return newChannel;
   }

   public void setNewChannel(boolean newChannel) {
      this.newChannel = newChannel;
   }
   
   public int getNumberChannelMessages() {
      return chatTool.countChannelMessages(chatChannel);
   }
   
}
