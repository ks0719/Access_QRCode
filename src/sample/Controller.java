package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;
import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @FXML
    AnchorPane Main;
    @FXML
    AnchorPane Sub;

    @FXML
    TextField R_name;
    @FXML
    TextField R_birth;
    @FXML
    TextField R_gender;
    @FXML
    TextField R_status;
    @FXML
    TextField R_date;

    @FXML
    TextField Input_key;

    FileChooser fc;
    DirectoryChooser dc;
    File f;
    Window w;

    Map<String,String> list;

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
        if(Text_loc.getText().equals("")||QR_loc.getText().equals("")){
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("처리 오류!");
            a.setContentText("경로가 설정되어있지 않습니다. 경로가 올바른지 확인 바랍니다.");
            a.showAndWait();
        }else{

            Action_btn.setDisable(true);
            Action_btn.setText("인식 대기중....QR코드를 스캔해주세요.");

           list=new HashMap<>();

            f=new File(Text_loc.getText());
            try{

                BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8"));
                String line="";
                while((line=br.readLine())!=null){
                    //temp[0]=코드,temp[1]=이름,temp[2]=생년월일,temp[3]=성별,temp[4]=직분,temp[5]=구역
                    String[] temp=line.split("\t");
                    System.out.println(Arrays.toString(temp));
                    list.put(temp[0],temp[1]+"\t"+temp[2]+"\t"+temp[3]+"\t"+temp[4]+"\t"+temp[5]);
                }
                br.close();

                Main.setVisible(false);
                Sub.setVisible(true);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }


    public void check(KeyEvent e){
        String key=null;
        SimpleDateFormat time=new SimpleDateFormat("yyyy년 MM월 dd일");
        SimpleDateFormat daytime=new SimpleDateFormat("HH시mm분ss초");
        Date date=new Date();
        String title=time.format(date)+" 출입명부 리스트.txt";
        String add_time=time.format(date)+" "+daytime.format(date);

        boolean isenter=e.getCode().toString().equals("ENTER");

        if(isenter&&list.containsKey(Input_key.getText())){
           // System.out.println("있어요!");
            key=list.get(Input_key.getText());
            Input_key.setText("");
        }else if(isenter){
            //System.out.println("없어요!:"+Input_key.getText()+"/"+list.containsKey("Input"));
            Input_key.setText("");
            return;
        }


        String text_loc=QR_loc.getText()+"/"+title;
        f=new File(text_loc);
        if(f.isFile()&&isenter){
            yes_file(key,add_time);
        }else if(isenter) {
            no_File(key,title,add_time);
        }
        //temp[0]=이름,temp[1]=생년월일,temp[2]=성별,temp[3]=직분,temp[4]=구역
        if(isenter){
            String[] temp=key.split("\t");
            R_name.setText(temp[0]);
            R_birth.setText(temp[1]);
            R_gender.setText(temp[2]);
            R_status.setText(temp[4]+"("+temp[3]+")");
            R_date.setText(time.format(date)+" "+daytime.format(date));
        }





    }

    public void yes_file(String key,String add_time){
        System.out.println("파일이 있어요. 이어쓸게요.");
        try{
            BufferedWriter bw=new BufferedWriter(new FileWriter(f,true));
            PrintWriter pw=new PrintWriter(bw,true);
            //pw.write(key+"\t"+add_time);
            pw.println(key+"\t"+add_time);
            pw.flush();
            pw.close();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void no_File(String key,String title,String add_time){
        System.out.println("파일이 없어요. 새로 만들게요");
        try {
            BufferedWriter bf=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"UTF-8"));
            bf.write("이름\t생년월일\t성별\t직분\t구역\t출입시간");
            bf.newLine();
            bf.write(key+"\t"+add_time);
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }






