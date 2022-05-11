/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import com.codename1.components.MultiButton;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import models.offre;
import services.Offre_service;
/**
 *
 * @author Wassef
 */
public class Entreprise_offreForm  extends Form  {
Resources theme = UIManager.initFirstTheme("/theme");
    public Entreprise_offreForm(Form previous) {
        super( "Offre", BoxLayout.y());
            this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();
         

                for (offre c : new Offre_service().getAllOffres()) {

                    this.add(addIteam_offre(c));
                }
               

                this.revalidate();
            });
        });
        // rechherhchhe
        this.getToolbar().addSearchCommand(e -> {
    String text = (String)e.getSource();
    if(text == null || text.length() == 0) {
        // clear search
        for(Component cmp : this.getContentPane()) {
            cmp.setHidden(false);
            cmp.setVisible(true);
        }
        this.getContentPane().animateLayout(150);
    } else {
        text = text.toLowerCase();
        for(Component cmp : this.getContentPane()) {
            MultiButton mb = (MultiButton)cmp;
            String line1 = mb.getTextLine1();
            String line2 = mb.getTextLine2();
            boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                    line2 != null && line2.toLowerCase().indexOf(text) > -1;
            mb.setHidden(!show);
            mb.setVisible(show);
        }
        this.getContentPane().animateLayout(150);
    }
}, 4);
        
        
        
        
        
        
        
             this.getToolbar().addCommandToOverflowMenu("Add Offre", null, ev -> {
               Form ajout = new Form( "Add Offre", BoxLayout.y());
             
               TextField titre = new TextField("", "titre", 20, TextArea.ANY);
               TextField contenu = new TextField("", "Contenu", 20, TextArea.ANY);
               Button submit = new Button("Submit");
               //control saisir
               Validator val_titre = new Validator();
                 val_titre.addConstraint(titre, new LengthConstraint(8));
            String text_saisir_des_caracteres = "^[0-9]+$";
            val_titre.addConstraint(titre, new RegexConstraint(text_saisir_des_caracteres, ""));
    submit.addActionListener(aj
                    -> {
                   if (titre.getText().equals("")) {
                    Dialog.show("Erreur", "Champ vide de titre ", "OK", null);

                } else if (val_titre.isValid()) {
                    Dialog.show("Erreur titre !", "il faut saisir des caracteres  !", "OK", null);
                } else if (contenu.getText().equals("")) {
                    Dialog.show("Erreur", "Champ vide de contenu ", "OK", null);

                }  else {
                 new Offre_service().add_offre(titre.getText(),contenu.getText());
                new Entreprise_offreForm(this).show();
                }
                
            }
            );
ajout.add("titre").add(titre).add("contenu").add(contenu).add(submit);
            ajout.show();
             });
        
        
        
        
        
        
        
    }
        public MultiButton addIteam_offre(offre c) {
        MultiButton m = new MultiButton();
       
  
        m.setTextLine1(c.getTitre());
        m.setTextLine2(c.getDescription());
          
        m.setTextLine3(c.getType());
          
        m.setEmblem(theme.getImage("round.png"));
       
        m.addActionListener(l
                -> {

            Form f2 = new Form("DETAILS",BoxLayout.y());
                   f2.getToolbar().addCommandToLeftBar("back",null, (evt) -> {
                this.showBack();
            });
                   
                   Button Supprimer = new Button("Supprimer");
                   Button Modifier = new Button("Modifier");
                        Supprimer.addActionListener(ev
                    -> {
                new Offre_service().delete_offre(c.getId());
                new Entreprise_offreForm(this).showBack();
            }
            );
                        
                        
                        Modifier.addActionListener(mod
                    -> {
                            Form fmodifier = new Form("Modifer", BoxLayout.y());  
                             Button submit = new Button("Submit");
                              AutoCompleteTextField titre = new AutoCompleteTextField(c.getTitre());
                titre.setMinimumElementsShownInPopup(1);
                  AutoCompleteTextField contenu = new AutoCompleteTextField(c.getDescription());
                contenu.setMinimumElementsShownInPopup(1);
                fmodifier.add("titre").add(titre).add("description").add(contenu).add(submit);
                    submit.addActionListener(sub
                        -> {
                    new Offre_service().edit_offre(c.getId(), titre.getText(), contenu.getText());
                    new Entreprise_offreForm(this).showBack();

                }
                );

                fmodifier.show();
                        });
                        
                        
                        
              f2.add("titre").add(c.getTitre()).add("titre").add(c.getDescription()).add("Created at").add(c.getType()).add("max : ").add(c.getCity()).add("modifier").add(Modifier).add("supprimer").add(Supprimer);
f2.show();
            
        }
        );
        return m;
    }
}
