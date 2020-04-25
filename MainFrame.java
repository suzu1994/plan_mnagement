import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class MainFrame extends Frame{
    private DataManagement data = new DataManagement();
    
    MainFrame(String title){
        setTitle(title);
        addWindowListener(
            new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    System.exit(0);
                }
            }
        );
        setDefaultFrameInformation();
        makeLabel();
        makeradioButtun();
        makeButton();
    }
    private void setDefaultFrameInformation(){
        setLocation(300,200);
        setSize(1500,1000);
        setBackground(Color.WHITE);
    }
    
    private void makeLabel(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日'");
        Label lb1 = new Label("現在 : "+ sdf.format(date),Label.CENTER);
        lb1.setFont(new Font("Arial",Font.PLAIN,70));
        add(lb1,BorderLayout.NORTH);
    }

    private void makeradioButtun(){
        CheckboxGroup chkgroup = new CheckboxGroup();
        List<Checkbox> chk_list = new ArrayList<Checkbox>();
        chk_list.add(new Checkbox("2つとも未達成",chkgroup,false));
        chk_list.add(new Checkbox("1つのみ達成",chkgroup,false));
        chk_list.add(new Checkbox("2つとも達成",chkgroup,false));
        
        for(Checkbox chk : chk_list){
            chk.setFont(new Font("Arial",Font.PLAIN,60));
        }

        for(EvaluatedState tmp_state : EvaluatedState.values()){
            chk_list.get(tmp_state.ordinal()).addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent arg0){
                    data.state = tmp_state;
                }
            });
        }

        Panel pnl1 = new Panel();
        pnl1.setLayout(new GridLayout(1,3));
        for(Checkbox chk : chk_list){
            pnl1.add(chk);
        }
        add(pnl1,BorderLayout.CENTER);
    }

    private void makeButton(){
        Button btn1 = new Button("データを登録");
        btn1.setFont(new Font("Arial",Font.PLAIN,60));
        
        Button btn2 = new Button("データをテキストファイルに出力する");
        btn2.setFont(new Font("Arial",Font.PLAIN,60));
        
        btn1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                data.setData();
            }
        });
        btn2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                data.outputResultToText();
            }
        });
        Panel pnl2 = new Panel();
        pnl2.setLayout(new GridLayout(2,1));
        pnl2.add(btn1);
        pnl2.add(btn2);
        add(pnl2,BorderLayout.SOUTH);
    }
}