/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pidev.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.pidev.entities.TestEntity;
import com.pidev.entities.User;
import com.pidev.services.QuizzService;

/**
 *
 * @author zewaf
 */
public class CreateTestForm extends Form {
    
    public CreateTestForm(Resources res){

        super(BoxLayout.y());
        QuizzService qs = QuizzService.getInstance();
        
        Container container= new Container(BoxLayout.y());
        
        TextField txtTitre = new TextField("","Titre");
        TextField txtTentative = new TextField("","nombre Tentative");
        TextField txtDuree = new TextField("","Durée");
        Container contInput= (FlowLayout.encloseIn(
                txtTitre,
                txtTentative,
                txtDuree
        ));
        Button btnCreer=new Button("Créer");
        contInput.add(btnCreer);
        
        btnCreer.addActionListener(evt -> {
            if(!"".equals(txtTitre.getText()) && !"".equals(txtDuree.getText()) && !"".equals(txtTentative.getText())){
                
                TestEntity t = new TestEntity();
                t.setTitre(txtTitre.getText());
                try{
                    t.setDuree(Integer.valueOf(txtDuree.getText()));
                    t.setNbrTentative(Integer.valueOf(txtTentative.getText()));
                    //t = qs.createTest(t);             
                    new CreateQuestionForm(res, t).show();
                }catch(NumberFormatException e){
                    Dialog.show("ATTENTION", "La durée et le nombre de tentative doivent étre des entiers naturels", "OK", null);
                    txtDuree.setText("");
                    txtTentative.setText("");
                }
                

            }else{
                Dialog.show("Champs Obligatoires", "Tous les champs sont obligatoires", "OK", null);
            }
            
            
            
        });
        add(contInput);
    }
}