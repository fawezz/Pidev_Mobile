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
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.pidev.entities.TestEntity;
import com.pidev.services.QuizzService;

/**
 *
 * @author zewaf
 */
public class ModifierTestForm extends Form {
    
    public ModifierTestForm(Resources res, TestEntity t){

        super(BoxLayout.y());
        QuizzService qs = QuizzService.getInstance();
        
        
        Container container= new Container(BoxLayout.y());
        
        TextField txtTitre = new TextField(t.getTitre(),"Titre");
        Label lTitre = new Label("Titre");
        TextField txtTentative = new TextField(t.getNbrTentative()+"","nombre Tentative");
        Label lTentative = new Label("Nombre de tentatives");
        TextField txtDuree = new TextField(t.getDuree()+"","Durée");
        Label lDuree = new Label("Duree");
        Container contInput= (FlowLayout.encloseIn(
                lTitre,
                txtTitre,
                lTentative,
                txtTentative,
                lDuree,
                txtDuree
        ));
        Button btnModifier=new Button("Modifier");
        contInput.add(btnModifier);
        
        btnModifier.addActionListener(evt -> {
            if(!"".equals(txtTitre.getText()) && !"".equals(txtDuree.getText()) && !"".equals(txtTentative.getText())){
                
                TestEntity quizz = t;
                quizz.setTitre(txtTitre.getText());
                try{
                    quizz.setDuree(Integer.valueOf(txtDuree.getText()));
                    quizz.setNbrTentative(Integer.valueOf(txtTentative.getText()));
                    quizz = qs.modifierTest(quizz);             
                    new MesQuizzForm(res).show();
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