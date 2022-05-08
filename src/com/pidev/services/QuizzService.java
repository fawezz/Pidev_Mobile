package com.pidev.services;


import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.pidev.entities.ChoixEntity;
import com.pidev.entities.QuestionEntity;
import com.pidev.entities.TestEntity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.pidev.utils.Statics;



/**
 *
 * @author zewaf
 */
public class QuizzService {
    
    String test;
    TestEntity testEntity = new TestEntity();
    ArrayList<TestEntity> tests = new ArrayList<>();
    //ArrayList<QuestionEntity>  questions = new ArrayList<>();




    public static QuizzService instance = null;
    public boolean resultOK;
    //private URLConnection con ;
    private ConnectionRequest req ;

    private QuizzService() {
        req = new ConnectionRequest();
    }

    public static QuizzService getInstance() {
        if (instance == null) {
            instance = new QuizzService();
        }
        return instance;
    }

    public  ArrayList<TestEntity> getAllTests(){
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"alltest";

        req.setUrl(url);
        req.setPost(false);
        //req.addArgument("Sender", "mobile");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 tests = parseTests( new String(req.getResponseData()));

                req.removeResponseListener(this);
            }
        });


        NetworkManager.getInstance().addToQueueAndWait(req);
        return tests;
    }
    public  ArrayList<TestEntity> getAllCertifs(){
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"certifs";

        req.setUrl(url);
        req.setPost(false);
        //req.addArgument("Sender", "mobile");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 tests = parseTests( new String(req.getResponseData()));

                req.removeResponseListener(this);
            }
        });


        NetworkManager.getInstance().addToQueueAndWait(req);
        return tests;
    }

    TestEntity quizz = new TestEntity();

    public TestEntity createTest (TestEntity t){
        String url = Statics.BASE_URL+"newtest";
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("userId", "7");          //TOCHANGE
        req.addArgument("duree", String.valueOf(t.getDuree()));
        req.addArgument("nbrTent", String.valueOf(t.getNbrTentative()));
        req.addArgument("titre",t.getTitre());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                quizz = parseTest( new String(req.getResponseData()));
                req.removeResponseListener(this);    
            }
            
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return quizz;
    }
    
    
    public ArrayList<TestEntity> parseTests(String jsonText){
        ArrayList<TestEntity> testsList = new ArrayList<>();
        //ArrayList<QuestionEntity>   questionsList =new ArrayList<>();
        //ArrayList<ChoixEntity> choicesList =new ArrayList<>();
        try {

            JSONParser j = new JSONParser();
            Map<String,Object> testsListJson =j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)testsListJson.get("root");

            for(Map<String,Object> obj : list){
                ArrayList<QuestionEntity>   questionsList =new ArrayList<>();
                TestEntity t = new TestEntity();

                t.setIdTest((int) Double.parseDouble(obj.get("id").toString()));
                t.setTitre(obj.get("titre").toString());
                t.setType(obj.get("type").toString());
                t.setDuree((int) Double.parseDouble(obj.get("duree").toString()));
                t.setNbrTentative((int) Double.parseDouble(obj.get("nbrtentative").toString()));
                //t.setUserId((int) Double.parseDouble(obj.get("userId").toString()));

                
                List<Map<String,Object>> listQuestions = (List<Map<String,Object>>)obj.get("questions");
                    //System.out.println("aaaaaaaaaaaa" +listQuestions.toString());

                for(Map<String,Object> quest : listQuestions){
                    
                    ArrayList<ChoixEntity> choicesList =new ArrayList<>();
                    QuestionEntity q = new QuestionEntity();
                    q.setIdQuestion((int) Double.parseDouble(quest.get("id").toString()));
                    q.setEnonce(quest.get("enonce").toString());
                    q.setTest(t);
                    //System.out.println("bbbbbbbbbbb" +q.toString());
                    List<Map<String,Object>> listChoix = (List<Map<String,Object>>)quest.get("choices");
                    //System.out.println("ccccccccccc" +quest.toString());
                    for(Map<String,Object> choix : listChoix){
                        ChoixEntity ch = new ChoixEntity();
                        ch.setIdChoix((int) Double.parseDouble(choix.get("id").toString()));
                        ch.setContenu(choix.get("contenu").toString());
                        ch.setCorrect(Boolean.parseBoolean(choix.get("correct").toString()));
                        ch.setQuestion(q);
                        choicesList.add(ch);
                    }
                    q.setChoices(choicesList);
                    questionsList.add(q);
                }
                t.setQuestions(questionsList);
                testsList.add(t);
            }


        } catch (IOException ex) {

        }
        //System.out.println(testsList.toString());
        return testsList;
    }
    public TestEntity parseTest(String jsonText){
        TestEntity t = new TestEntity();
        try {

            JSONParser j = new JSONParser();
            Map<String,Object> testsListJson =j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            Map<String,Object> obj = (Map<String,Object>)testsListJson.get("root");


                t.setIdTest((int) Double.parseDouble(obj.get("id").toString()));
                t.setTitre(obj.get("titre").toString());
                t.setType(obj.get("type").toString());
                t.setDuree((int) Double.parseDouble(obj.get("duree").toString()));
                t.setNbrTentative((int) Double.parseDouble(obj.get("nbrtentative").toString()));
                //t.setUserId((int) Double.parseDouble(obj.get("userId").toString()));

        } catch (IOException ex) {

        }
        //System.out.println(testsList.toString());
        return t;
    }
}    
    
    /*
    public  TestEntity getProjectById(int id){

        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"project/myshow/"+id;
        System.out.println("url"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("Sender", "mobile");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                test =parseOneProject( new String(req.getResponseData()));

                req.removeResponseListener(this);
            }
        });


        NetworkManager.getInstance().addToQueueAndWait(req);
        return test;
    }*/
    

    /*public boolean addProject(TestEntity t) {

        String url = Statics.BASE_URL + "test/new";

        req.setUrl(url);
        req.addArgument("Sender", "mobile");
        req.addArgument("name", t.getName());

        req.addArgument("userid",""+t.getCreator().getId());
        req.addArgument("periode",t.getPeriode().toString());
        req.addArgument("price",""+t.getPrice());
        System.out.println(t);
        req.addArgument("category",""+t.getCategory().getId());
        req.addArgument("description",t.getDescription());
        req.addArgument("image64",t.getImage());



        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }*/


    /*public  void setcomplete(int id){
        //String url = Statics.BASE_URL+"/tasks/";
        req.setPost(true);
        String url = Statics.BASE_URL+"project/complete/"+id;

        req.setUrl(url);




        NetworkManager.getInstance().addToQueueAndWait(req);

    }*/

    /*public  void Join(int id,int userid){
        //String url = Statics.BASE_URL+"/tasks/";
        req.setPost(true);
        String url = Statics.BASE_URL+"project/join/"+id;
        req.addArgument("userid",userid+"");
        req.setUrl(url);




        NetworkManager.getInstance().addToQueueAndWait(req);

    }
    public  void block(int userid,int projectid){
        //String url = Statics.BASE_URL+"/tasks/"id;
        req.setPost(true);
        String url = Statics.BASE_URL+"project/block/"+userid;
        req.addArgument("projectid",projectid+"");
        req.setUrl(url);




        NetworkManager.getInstance().addToQueueAndWait(req);

    }*/

    

    /*public Project parseOneProject(String jsonText){

        ArrayList<User> userss = new ArrayList<>();
        try {


            JSONParser j = new JSONParser();
            Map<String,Object> obj =j.parseJSON(new CharArrayReader(jsonText.toCharArray()));


                Project p = new Project();

                int i = 0 ;

                p.setId( (int) Double.parseDouble(obj.get("id").toString()));


                p.setName(  obj.get("name").toString());
                p.setPeriode((int) Double.parseDouble(obj.get("periode").toString()));
                p.setPrice(Float.parseFloat(obj.get("price").toString()));
            p.setState((int) Double.parseDouble(obj.get("state").toString()));
                p.setImage(obj.get("image").toString());
                //  System.out.println("----"+obj.get("creator").toString());
//                List<User> ce = mapper.convertValue(
//                        obj.get("creator"),
//                        new TypeReference<List<User>>() { }
//                );
//                System.out.println(ce.toString());
                LinkedHashMap<String,Object> lc = (LinkedHashMap <String,Object>)obj.get("creator");
                User creator = new User();
                creator.setUsername(lc.get("username").toString());

                creator.setEmail(lc.get("email").toString());

                creator.setId((int) Double.parseDouble(lc.get("id").toString()));
                p.setCreator(creator);

                //    System.out.println( lc.get("id"));


                LinkedHashMap<String,Object> lcat = (LinkedHashMap <String,Object>)obj.get("category");
                Category category = new Category();
                category.setName(lcat.get("name").toString());
                category.setId((int) Double.parseDouble(lcat.get("id").toString()));
                p.setCategory(category);
                List<Map<String,Object>> listUsers = (List<Map<String,Object>>)obj.get("users");

                for(Map<String,Object> lu : listUsers){
                    User u = new User();
                    u.setId((int) Double.parseDouble(lu.get("id").toString()));
                    u.setUsername(lu.get("username").toString());
                    u.setEmail(lu.get("email").toString());
                    userss.add(u);
                }

                p.setUsers(userss);

                return p;




        } catch (IOException ex) {

        }
        return null ;
    }*/

    /*public  ArrayList<Category> getCategory(){
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"project/category/get";

        req.setUrl(url);
        req.setPost(false);
        req.addArgument("Sender", "mobile");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                categorys =parseCategory( new String(req.getResponseData()));

                req.removeResponseListener(this);
            }
        });


        NetworkManager.getInstance().addToQueueAndWait(req);
        return categorys;
    }*/
    /*
    public ArrayList<Category> parseCategory(String jsonText){
        ArrayList<Category>   cats=new ArrayList<>();

        try {


            JSONParser j = new JSONParser();
            Map<String,Object> CatsListJson =j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>)CatsListJson.get("root");

            for(Map<String,Object> obj : list){
               Category c = new Category();

                int i = 0 ;

                c.setId( (int) Double.parseDouble(obj.get("id").toString()));


                c.setName(  obj.get("name").toString());

                cats.add(c);
                System.out.println(cats);
            }


        } catch (IOException ex) {

        }
        return cats;
    }*/

