package Iamreporter.ServicePack;

import Iamreporter.DB.FriendRelationDB;
import Iamreporter.Model.FriendRelation;
import Iamreporter.Model.User;

public class FriendAction {

    FriendRelationDB friendRelationDB = new FriendRelationDB();

    public int startFriendRelation(User userWhoAdd, User userWhomAdd){
        String userWhoAddUUID = userWhoAdd.getPrivateUUID();
        String userWhomAddUUID = userWhomAdd.getPrivateUUID();
        FriendRelation friendRelation = friendRelationDB.getFriendRelation(userWhoAddUUID,userWhomAddUUID);
        if(friendRelation==null){
            friendRelation = new FriendRelation();
            friendRelation.setUserWhoAddUUID(userWhoAddUUID);
            friendRelation.setUserWhomAddUUID(userWhomAddUUID);
            friendRelation.setStatus("subscribe");
            friendRelationDB.saveFriendRelation(friendRelation);
        }else{
            friendRelation.changeStatus();
            friendRelationDB.update(friendRelation);
        }
        return friendRelation.getDigitalStatus();
    }



}
