package com.crowdfund.demo.service;

import com.crowdfund.demo.model.*;
import com.crowdfund.demo.util.Logger;

public class NotificationService {

    public static void sendInviteForNewProject(Project project){
        Logger.log("Push the message to Invitation Worker");
    }

    public static void sendInviteForNewUser(User user){
        Logger.log("Push the message to Invitation Worker");
    }

    public static void sendInfoMailForDonation(Donation donation){
        Logger.log("Push the message to Invitation Worker regarding donation both project Admin and Donor");
    }
}
