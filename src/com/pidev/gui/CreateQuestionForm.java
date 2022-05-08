/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pidev.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.pidev.entities.QuestionEntity;
import com.pidev.entities.TestEntity;
import com.pidev.services.QuestionService;
import com.pidev.services.QuizzService;

/**
 *
 * @author zewaf
 */
public class CreateQuestionForm extends Form {
    
    public CreateQuestionForm(Resources res, TestEntity createdTest){

        super(BoxLayout.y());
        QuestionService qs = QuestionService.getInstance();
        
        Container container= new Container(BoxLayout.y());
        Label lblHeader = new Label("Créer une nouvelle question");
        TextField txtEnonce = new TextField("","Enonce");
        TextField txtChoix1 = new TextField("","Choix 1");
        TextField txtChoix2 = new TextField("","Choix 2");
        TextField txtChoix3 = new TextField("","Choix 3");
        Container contInput= (FlowLayout.encloseIn(txtEnonce,
                lblHeader,
                txtChoix1,
                txtChoix2,
                txtChoix3
        ));
        Button btnCreer=new Button("Créer");
        contInput.add(btnCreer);
        btnCreer.addActionListener(evt -> {
            QuestionEntity q = new QuestionEntity();
            q.setEnonce(txtEnonce.getText());
            q.setTest(createdTest);
            
            qs.createQuestion(q, txtChoix1.getText(), txtChoix2.getText(), txtChoix3.getText());
        });
        add(contInput);
    }
}