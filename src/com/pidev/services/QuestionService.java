/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pidev.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.pidev.entities.QuestionEntity;
import com.pidev.entities.TestEntity;
import com.pidev.utils.Statics;

/**
 *
 * @author zewaf
 */
public class QuestionService {
    
    public static QuestionService instance = null;
    public boolean resultOK;
    //private URLConnection con ;
    private ConnectionRequest req ;

    private QuestionService() {
        req = new ConnectionRequest();
    }

    public static QuestionService getInstance() {
        if (instance == null) {
            instance = new QuestionService();
        }
        return instance;
    }
    
    QuestionEntity question;
    public boolean createQuestion (QuestionEntity q, String choix1, String choix2, String choix3){
        String url = Statics.BASE_URL+"newquestion/";
        url = url + q.getTest().getIdTest(); //passing testId in url
        
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("enonce", q.getEnonce());          //TOCHANGE
        req.addArgument("choix1", choix1);
        req.addArgument("choix2",choix2);
        req.addArgument("choix3",choix3);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);    
            }
            
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    /*public QuestionEntity parseQuestion(){
        
    }*/
}
