package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Controller {

    @FXML
    TextField Text_loc;
    @FXML
    Button Text_btn;
    @FXML
    TextField QR_loc;
    @FXML
    Button QR_btn;
    @FXML
    Button Action_btn;

    FileChooser fc;
    DirectoryChooser dc;
    File f;
    Window w;


    public void Text_loc() {
        fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("QR코드 관리 리스트", new String[] { "*.txt" }));
        f = this.fc.showOpenDialog(this.w);
        if(f!=null){
            try {
                this.Text_loc.setText(this.f.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void QR_loc() {
        dc = new DirectoryChooser();
        f = this.dc.showDialog(this.w);
        if(f!=null){
            try {
                this.QR_loc.setText(this.f.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Action_btn(){

        Map<String,String> list=new HashMap<>();

        SimpleDateFormat time=new SimpleDateFormat("yyyy년-MM월-dd일");
        SimpleDateFormat detailtime=new SimpleDateFormat("HH시mm분ss초");
        Date date=new Date();
        String title=time.format(date)+" 출입명부 리스트.txt";
        String add_time=time.format(detailtime);

        f=new File(Text_loc.getText());
        try{

            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f),"euc-kr"));
            String line="";
            while((line=br.readLine())!=null){
                //temp[0]=코드,temp[1]=이름,temp[2]=생년월일,temp[3]=성별,temp[4]=직분,temp[5]=구역
                String[] temp=line.split("\t");
                list.put(temp[0],temp[1]+"\t"+temp[2]+"\t"+temp[3]+"\t"+temp[4]+temp[5]);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String text_loc=QR_loc.getText()+"/"+title;
        f=new File(text_loc);
        if(f.isFile()){
            in_file(add_time,list);
        }else {
            out_File(title,add_time,list);
        }
    }

    public void in_file(String add_time,Map list){

        try{
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"euc-kr"));
            PrintWriter pw=new PrintWriter(bw,true);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void out_File(String title,String add_time,Map list){
            try {
                BufferedWriter bf=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"euc-kr"));
                bf.write("");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    }






