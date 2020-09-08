package com.boomerdroid.remotehelper.ModelData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecentContent {

    public static final List<RecentItem> ITEMS = new ArrayList<RecentItem>();


    static {
        // Add some sample items.
        addItem(new RecentItem("1","SMS","7398611662","19 May at 11:39PM","Granted","To +91-9936286948"));
        addItem(new RecentItem("2","Contact","8299247882","19 May at 11:39PM","Rejected","Aman ShriIdea"));
        addItem(new RecentItem("3","Audio Profile","9956784223","19 May at 11:39PM","Granted","Silent"));
        addItem(new RecentItem("4","Audio Profile","7398611662","19 May at 11:39PM","Granted","Vibration"));

    }

    private static void addItem(RecentItem item) {
        ITEMS.add(item);
    }


    public static class RecentItem {
        public final String id;
        public final String request,requester,date,status;
        public final String details;

        public RecentItem(String id, String request,String requester,String date,String status, String details) {
            this.id = id;
            this.request = request;
            this.requester = requester;
            this.date = date;
            this.status = status;
            this.details = details;
        }

    }
}
