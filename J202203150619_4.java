import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class J202203150619_4 extends JFrame {
    //****************************************************************************************************************//
    //��Ա����
    //****************************************************************************************************************//
    private static String title = "202203150619����������Java ��������ۺ�ʵ��";
    //      �˵������
    private static JMenuBar mb = new JMenuBar();
    private static JMenu mFile = new MyMenu("�ļ�(F)", KeyEvent.VK_F),
            mHomeWork = new MyMenu("Java�ϻ���Ŀ"),
            mPhone = new MyMenu("ͨѶ¼(C)", KeyEvent.VK_C);
    private static JMenuItem miFileNew = new JMenuItem("�½�(N)", KeyEvent.VK_N),
            miFileOpen = new JMenuItem("��(O)...", KeyEvent.VK_O),
            miFileSave = new JMenuItem("����(S)", KeyEvent.VK_S),
            miFileFont = new JMenuItem("��������ɫ(F)...", KeyEvent.VK_F),
            miFileBG = new JMenuItem("������ɫ(B)...", KeyEvent.VK_B),
            miFileQuit = new JMenuItem("�˳�(X)", KeyEvent.VK_X),
            miHWHuiwen = new JMenuItem("������"),
            miHWTrans = new JMenuItem("������Ӣ�Ļ���"),
            miHWCompute = new JMenuItem("ͳ��Ӣ������"),
            miHWPhoneCheck = new JMenuItem("�ֻ��źϷ����ж�"),
            miHWSum = new JMenuItem("�ı��ļ����"),
            miPhoneDisplay = new JMenuItem("ͨѶ¼ά��"),
            miPhoneSave = new JMenuItem(" ͨѶ¼�����ļ�����");

    //      �ı������
    private static JTextArea ta = new JTextArea();
    //�����������С
    private static String font = "����";
    private static int fontSize = 15;
    private static String fontColor = "black";
    private static String bgColor = "#ffffff";
    //��ʼ��Ϊ��ǰ�ļ���
    private static String filePath = System.getProperty("user.dir").replace("\\", "\\\\") + "\\\\",
            fileName = null;
    private static String textContent = "";
    //����ѡ�񴰿�
    private static MyFontDly myFontDly;

    {
        InitTextArea();
        myFontDly = new MyFontDly("����");
        myFontDly.hideFontDly();
        myFontDly.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        myFontDly.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                myFontDly.hideFontDly();
            }
        });
    }

    //      ͨѶ¼���
    //�����ļ�
    private static String dataFilePath = System.getProperty("user.dir").replace("\\", "\\\\") + "\\\\",
            dataFileName = "data.dat";

    private static ContactManager mana;

    //****************************************************************************************************************//
    //��ʼ��
    //****************************************************************************************************************//
    private J202203150619_4(String title) {
        super(title);

        //��Ӳ˵��� �� ������ʹ��
        addMenus();
        activeMenus();

        //��ȡͨѶ¼���ݴ���λ��
        readinDataPath();

        //��дsetSize�������Զ���Ӧ��ͬ�ֱ���
        setSize();
    }

    private void addMenus() {
        setJMenuBar(mb);

        //�ļ���ť���ѡ��
        mFile.add(miFileNew);
        mFile.add(miFileOpen);
        mFile.add(miFileSave);
        mFile.addSeparator();
        mFile.add(miFileFont);
        mFile.add(miFileBG);
        mFile.addSeparator();
        mFile.add(miFileQuit);

        mHomeWork.add(miHWHuiwen);
        mHomeWork.add(miHWTrans);
        mHomeWork.add(miHWCompute);
        mHomeWork.add(miHWPhoneCheck);
        mHomeWork.add(miHWSum);

        mPhone.add(miPhoneDisplay);
        mPhone.add(miPhoneSave);

        //�Ѱ�ť��ӽ��˵���
        mb.add(mFile);
        mb.add(mHomeWork);
        mb.add(mPhone);
    }

    private void activeMenus() {
        //�����ļ���ť

        //�½���ť
        miFileNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFile();
            }
        });
        //�򿪰�ť
        miFileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        //���水ť
        miFileSave.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK
        ));
        miFileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        //��������ɫ��ť
        miFileFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFont();
            }
        });
        //������ɫ��ť
        miFileBG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBGColor();
            }
        });
        //�˳���ť
        miFileQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkQuit();
            }
        });


        //����java�ϻ���Ŀ��ť
        //������
        miHWHuiwen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkHuiWen();
            }
        });
        //������Ӣ�Ļ���
        miHWTrans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transNumToEng();
            }
        });
        //ͳ��Ӣ������
        miHWCompute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computeWords();
            }
        });
        //�ֻ�����Ϸ����ж�
        miHWPhoneCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                phoneCheck();
            }
        });
        //�ı��ļ����
        miHWSum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textSum();
            }
        });

        //ͨѶ¼��ť
        //ͨѶ¼ά��
        miPhoneDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    displayData();
                } catch (Exception ex) {
                    System.out.println("displayData error");
                    throw new RuntimeException(ex);
                }
            }
        });
        //ͨѶ¼�����ļ�����
        miPhoneSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDataPath();
            }
        });
    }

    private void setSize() {
        //��������С
        Toolkit tk = getToolkit();
        Dimension dm = tk.getScreenSize();

        //��ʼ��Ϊ��Ļ�� 0.65���� ��Ϊ��Ļ�� 0.65��
        setSize((int) (dm.getWidth() * 0.65), (int) (dm.getHeight() * 0.65));
    }

    private void InitTextArea() {
        readinFont();
        //��ǰʹ�õ��������С
        ta.setFont(new Font(font, Font.PLAIN, fontSize));

        ta.setForeground(stringToColor(fontColor));
        ta.setBackground(Color.decode(bgColor));

        //ʹ�ı����ɹ���
        add(new JScrollPane(ta));
    }

    private void readinFont() {
        File fontConfig = new File("font.properties");
        if (!fontConfig.exists()) {
            try {
                fontConfig.createNewFile();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("font.properties"), "gbk"));
                writer.write("font=����\nfontSize=15\nfontColor=black\nbgColor=#ffffff");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("readinFont error");
                throw new RuntimeException(e);
            }
        }
        Properties fontProp = new Properties();
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(fontConfig), "gbk");
            fontProp.load(reader);
            font = fontProp.getProperty("font");
            fontSize = Integer.parseInt(fontProp.getProperty("fontSize"));
            fontColor = fontProp.getProperty("fontColor");
            bgColor = fontProp.getProperty("bgColor");
        } catch (IOException e) {
            System.out.println("readinFont error");
            throw new RuntimeException(e);
        }
    }

    private void readinDataPath() {
        File fontConfig = new File("datapath.properties");
        if (!fontConfig.exists()) {
            try {
                fontConfig.createNewFile();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("datapath.properties"), "gbk"));
                writer.write(String.format("dataFilePath=%s\ndataFileName=%s", dataFilePath.replace("\\", "\\\\"), dataFileName));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("readinFont error");
                throw new RuntimeException(e);
            }
        }
        Properties dataprop = new Properties();
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(fontConfig), "gbk");
            dataprop.load(reader);
            dataFilePath = dataprop.getProperty("dataFilePath");
            dataFileName = dataprop.getProperty("dataFileName");
        } catch (IOException e) {
            System.out.println("readinDataPath error");
            throw new RuntimeException(e);
        }
    }

    //****************************************************************************************************************//
    //�¼���ط���
    //****************************************************************************************************************//

    //�ļ����
    private void newFile() {
        //�����ļ��޸�
        if (!textContent.equals(ta.getText())) {
            int choose = JOptionPane.showConfirmDialog(null, "�Ƿ�Ҫ�����޸ģ�");
            switch (choose) {
                case JOptionPane.YES_OPTION:
                    saveFile();
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                case JOptionPane.CANCEL_OPTION:
                    return;
            }
        }

        if (!queryFileName()) return;

        try {
            File newfile = new File(filePath, fileName);
            newfile.createNewFile();
            textContent = "";
        } catch (IOException e) {
            System.out.println("newFile error");
            throw new RuntimeException(e);
        }
    }

    //ѡ��ɹ�����true ��֮false
    private boolean queryFileName() {
        String newfile = JOptionPane.showInputDialog(null, "�����ļ���", ".txt");
        if (newfile == null || "".equals(newfile))
            return false;
        else
            fileName = newfile;
        return true;
    }

    private void openFile() {
        //�����ļ��޸�
        if (!textContent.equals(ta.getText())) {
            int choose = JOptionPane.showConfirmDialog(null, "�Ƿ�Ҫ�����޸ģ�");
            switch (choose) {
                case JOptionPane.YES_OPTION:
                    saveFile();
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                case JOptionPane.CANCEL_OPTION:
                    return;
            }
        }
        //ѡ���ļ�
        JFileChooser fc = new JFileChooser(filePath);
        int ret = fc.showOpenDialog(this);
        //�ж�ѡ����
        switch (ret) {
            case JFileChooser.APPROVE_OPTION:
                readinFile(fc);
                setTitle(title + "��" + fileName);
                break;
            case JFileChooser.ERROR_OPTION:
                JOptionPane.showMessageDialog(this, "��ȡ����");
                break;
            case JFileChooser.CANCEL_OPTION:
                return;
        }

    }

    private void readinFile(JFileChooser fc) {
        StringBuilder text = new StringBuilder();
        char[] buf = new char[100000];
        int len;
        textContent = "";
        try {
            //��ȡ�ļ�����  ��ָ�����ݱ���Ϊgbk
            filePath = fc.getSelectedFile().getParent() + "\\";
            fileName = fc.getSelectedFile().getName();
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath + fileName), "gbk");
            BufferedReader in = new BufferedReader(reader, 100010);
            while ((len = in.read(buf)) != -1) {
                text.append(buf, 0, len);
                //��ֹtext�����Ƴ� ����4096���ַ������һ��
                textContent += text.toString();
                text.setLength(0);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("readinFile error");
            throw new RuntimeException(e);
        }
        ta.setText(textContent);
    }

    private void saveFile() {
        if (fileName == null && (ta.getText() == null || "".equals(ta.getText()))) return;

        if (fileName == null) {
            if (!queryFileName()) return;

            File newfile = new File(filePath, fileName);
            try {
                newfile.createNewFile();
            } catch (IOException e) {
                System.out.println("saveFile error");
                throw new RuntimeException(e);
            }
        }

        textContent = ta.getText();
        OutputStreamWriter writer;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(fileName), "gbk");
            writer.write(textContent);
            writer.close();
        } catch (IOException e) {
            System.out.println("saveFile error");
            throw new RuntimeException(e);
        }
    }

    private void setFont() {
        setPopWindowSize(myFontDly);
        myFontDly.showFontDly();
    }

    private void setBGColor() {
        Color color = JColorChooser.showDialog(null, "ѡ�񱳾���ɫ", Color.white);
        if (color == null)
            return;

        //getRGB�ĵ�һ���ֽ���͸���� ����Ҫ
        int colorMask = 0x00ffffff;
        bgColor = "#" + Integer.toHexString(color.getRGB() & colorMask);

        ta.setBackground(color);
    }

    private void checkQuit() {
        //�����ļ��޸�
        if (!textContent.equals(ta.getText())) {
            int choose = JOptionPane.showConfirmDialog(null, "�Ƿ�Ҫ�����޸ģ�");
            switch (choose) {
                case JOptionPane.YES_OPTION:
                    saveFile();
                    exit();
                    break;
                case JOptionPane.NO_OPTION:
                    exit();
                    break;
                case JOptionPane.CANCEL_OPTION:
                    return;
            }
        } else {
            int ret = JOptionPane.showConfirmDialog(this, "ȷ��Ҫ�˳�ϵͳ��", "Java��������ܺ�ʵ��", JOptionPane.YES_NO_OPTION);
            switch (ret) {
                case JOptionPane.YES_OPTION:
                    exit();
                    break;
                case JOptionPane.NO_OPTION:
                    break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Java�ϻ���Ŀ

    //������
    private void checkHuiWen() {
        JFrame fr = new JFrame("�жϻ�����");
        fr.setLayout(new GridLayout(3, 1, 5, 20));

        JLabel lbl = new JLabel("������1-99999֮���������");
        JTextField textField = new JTextField(5);
        textField.setHorizontalAlignment(JTextField.CENTER);
        JButton checkBtn = new JButton("�ж��Ƿ�Ϊ������");
        JButton cancelBtn = new JButton("ȡ��");
        JPanel panBtn = new JPanel(new BorderLayout());

        panBtn.add(checkBtn, BorderLayout.WEST);
        panBtn.add(cancelBtn, BorderLayout.EAST);

        fr.add(lbl);
        fr.add(textField);
        fr.add(panBtn);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cancelBtn) {
                    fr.dispose();
                    return;
                }

                String content = textField.getText();
                if (!isNumber(content) || content.length() > 5 || "".equals(content)) {
                    JOptionPane.showMessageDialog(null, "�������");
                    return;
                }

                boolean ok = true;
                for (int i = 0; i < content.length() / 2; ++i) {
                    if (content.charAt(i) != content.charAt(content.length() - 1 - i))
                        ok = false;
                }
                if (ok)
                    JOptionPane.showMessageDialog(null, "����һ��������");
                else
                    JOptionPane.showMessageDialog(null, "�ⲻ��һ��������");
            }
        };
        checkBtn.addActionListener(listener);
        cancelBtn.addActionListener(listener);

        setPopWindowSize(fr);
        fr.setVisible(true);
        fr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    //������Ӣ�Ļ���
    private void transNumToEng() {
        JFrame fr = new JFrame("������Ӣ�Ļ���");

        JLabel lbl = new JLabel("������1-99֮���������");
        JTextField inField = new JTextField(5);
        JTextField outField = new JTextField(5);
        JSplitPane textPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inField, outField);

        inField.setHorizontalAlignment(JTextField.CENTER);
        outField.setHorizontalAlignment(JTextField.CENTER);
        outField.setEditable(false);
        textPanel.setDividerSize(5);

        JButton checkBtn = new JButton("ȷ��");
        JButton cancelBtn = new JButton("ȡ��");
        JPanel panBtn = new JPanel(new BorderLayout());

        panBtn.add(checkBtn, BorderLayout.WEST);
        panBtn.add(cancelBtn, BorderLayout.EAST);

        fr.add(lbl, BorderLayout.NORTH);
        fr.add(textPanel, BorderLayout.CENTER);
        fr.add(panBtn, BorderLayout.SOUTH);

        String[] x = {"zero", "one", "two", "three", "four", "five",
                "six", "seven", "eight", "nine"};
        String[] y = {"ten", "eleven", "twelve", "thirteen", "fourteen",
                "fifteen", "sixteen", "seventeen", "eighteen",
                "nineteen"};
        String[] z = {"twenty", "thirty", "forty", "fifty",
                "sixty", "seventy", "eighty", "ninety"};
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cancelBtn) {
                    fr.dispose();
                    return;
                }


                String s = inField.getText();
                if (isNumber(s)) {
                    int num = Integer.parseInt(s);
                    if (num < 0 || num > 100) {//�жϷ�Χ
                        JOptionPane.showMessageDialog(null, "�������");
                        return;
                    }

                    if (num <= 9) {
                        outField.setText(x[num]);
                    } else if (num <= 19) {
                        outField.setText(y[num % 10]);
                    } else {
                        int fst = num / 10, sec = num % 10;
                        if (sec != 0)
                            outField.setText(z[fst - 2] + " " + x[sec]);
                        else
                            outField.setText(z[fst - 2]);
                    }
                } else if (isEnglish(s, x, y, z)) {
                    String[] split = s.split(" ");
                    int len = split.length;
                    if (len == 1) {
                        for (int i = 0; i < x.length; ++i) {
                            if (x[i].equals(split[0])) {
                                outField.setText(String.valueOf(i));
                                break;
                            }
                        }
                        for (int i = 0; i < y.length; ++i) {
                            if (y[i].equals(split[0])) {
                                outField.setText("1" + i);
                                break;
                            }
                        }
                    } else if (len == 2) {
                        String fst = split[0], sec = split[1];
                        //��isNumber���Ѿ��ж�secΪzero���������˴˴������ж�
                        String ret = "";
                        for (int i = 0; i < z.length; i++) {
                            if (z[i].equals(fst)) {
                                ret += i + 2;
                                break;
                            }
                        }
                        for (int i = 0; i < x.length; i++) {
                            if (x[i].equals(sec)) {
                                ret += i;
                                break;
                            }
                        }
                        outField.setText(ret);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "�������");
                }
            }
        };
        checkBtn.addActionListener(listener);
        cancelBtn.addActionListener(listener);

        setPopWindowSize(fr);
        fr.setVisible(true);
        fr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        textPanel.setDividerLocation(0.5);
    }

    //ͳ��Ӣ������
    private void computeWords() {
        //�Ȼ��ͳ������
        int[] cnt = new int[26];
        int containsOr = 0;
        int lengthIs3 = 0;

        String content = ta.getText();
        if ("".equals(content) || content == null) {
            JOptionPane.showMessageDialog(null, "���ı����ݿ���ͳ�ƣ�");
            return;
        }

        String[] split = content.split("\\W+");

        for (String s : split) {
            char first = s.charAt(0);
            if (!Character.isLetter(first)) continue;

            cnt[Character.toLowerCase(first) - 'a']++;
            if (s.contains("or"))
                containsOr++;
            if (s.length() == 3)
                lengthIs3++;
        }

        JFrame fr = new JFrame("ͳ��Ӣ������");

        JSplitPane mainPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);


        JPanel upperPanel = new JPanel(new GridLayout(2, 1, 0, 0));

        JLabel lblOr = new JLabel("��\"or\"�ַ����ĵ�����");
        JLabel lblOrNum = new JLabel(String.valueOf(containsOr));
        JPanel panOr = new JPanel();

        panOr.add(lblOr);
        panOr.add(lblOrNum);

        JLabel lblLen3 = new JLabel("����Ϊ3�ĵ�����");
        JLabel lblLen3Num = new JLabel(String.valueOf(lengthIs3));
        JPanel pan3 = new JPanel();

        pan3.add(lblLen3);
        pan3.add(lblLen3Num);

        upperPanel.add(panOr);
        upperPanel.add(pan3);


        JPanel lowerPanel;
        int col = 26 * 2 + 1;
        lowerPanel = new JPanel(new GridLayout(0, col, 0, 0));
        Color[] colors = new Color[]{Color.cyan.brighter(), Color.yellow.brighter(), Color.red.brighter(), Color.green.brighter()};

        int max = 0;
        for (int x : cnt) max = Math.max(max, x);
        //���ٻ�����
        int idx = Math.max(4, max);

        while (idx > 0) {
            lowerPanel.add(new JLabel(String.valueOf(idx)));

            for (int i = 0; i < cnt.length; i++) {
                lowerPanel.add(new JPanel());

                if (cnt[i] >= idx) {
                    JPanel tem = new JPanel();
                    tem.setBackground(colors[i % 4]);
                    lowerPanel.add(tem);
                } else {
                    lowerPanel.add(new JPanel());
                }
            }

            idx--;
        }
        lowerPanel.add(new JPanel());
        for (int i = 0; i < 26; ++i) {
            lowerPanel.add(new JLabel());
            lowerPanel.add(new JLabel(String.valueOf((char) (i + 'a'))));
        }

        mainPanel.add(upperPanel);
        mainPanel.add(lowerPanel);

        fr.add(mainPanel);

        setPopWindowSize(fr);
        fr.setVisible(true);
        fr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        mainPanel.setDividerLocation(0.1);
    }

    //�ֻ�����Ϸ����ж�
    private void phoneCheck() {
        JFrame fr = new JFrame("�ֻ�����Ϸ����ж�");
        fr.setLayout(new GridLayout(3, 1, 5, 20));

        JLabel lbl = new JLabel("������Ϸ����ֻ��ţ�");
        JTextField textField = new JTextField(5);
        textField.setHorizontalAlignment(JTextField.CENTER);
        JButton checkBtn = new JButton("�жϺϷ���");
        JButton cancelBtn = new JButton("ȡ��");
        JPanel panBtn = new JPanel(new BorderLayout());

        panBtn.add(checkBtn, BorderLayout.WEST);
        panBtn.add(cancelBtn, BorderLayout.EAST);

        fr.add(lbl);
        fr.add(textField);
        fr.add(panBtn);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cancelBtn) {
                    fr.dispose();
                    return;
                }

                String s = textField.getText();
                String[] split;
                if (s.startsWith("+"))
                    split = s.substring(1).split("-");
                else
                    split = s.split("-");
                //�жϳ���
                int len = 0;
                for (String sp : split)
                    len += sp.length();
                if (len != 13) {
                    JOptionPane.showMessageDialog(null, "���Ȳ��Ϸ�������1");
                    return;
                }
                //�жϰ���������
                boolean ok = true;
                for (String sp : split)
                    for (char c : sp.toCharArray())
                        if (c < '0' || c > '9') {
                            ok = false;
                            break;
                        }
                if (!ok) {
                    JOptionPane.showMessageDialog(null, "���������֣�����2");
                    return;
                }
                //����86��ͷ
                if (!("86".equals(s.substring(0, 2)) || "+86".equals(s.substring(0, 3)))) {
                    JOptionPane.showMessageDialog(null, "����86��ͷ������3");
                    return;
                }
                //��ʽ������
                //8613957177889����ֻ�г������� �������ж�
                //+8613957177889ͬ��
                //86-13957177889 �� +86-13957177889
                ok = true;
                if (!(s.length() == 13 || s.length() == 14 || s.length() == 15 || s.length() == 17))
                    ok = false;
                if (split.length == 2)
                    if (!(split[0].length() == 2 && split[1].length() == 11))
                        ok = false;
                if (split.length == 4)
                    if (!(split[0].length() == 2 && split[1].length() == 3 && split[2].length() == 4 && split[3].length() == 4))
                        ok = false;
                if (!ok) {
                    JOptionPane.showMessageDialog(null, "��ʽ���� ����4");
                    return;
                }
                //�Ϸ�
                JOptionPane.showMessageDialog(null, "����Ϸ�������0");
            }
        };
        checkBtn.addActionListener(listener);
        cancelBtn.addActionListener(listener);

        setPopWindowSize(fr);
        fr.setVisible(true);
        fr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void textSum() {
        JFrame fr = new JFrame("�ı��ļ����");

        JLabel lbl = new JLabel();

        JProgressBar bar = new JProgressBar(0, 100);
        bar.setStringPainted(true);
        bar.setBackground(Color.white);
        bar.setForeground(Color.blue);

        JButton btn = new JButton("��ʼ");
        JPanel pan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pan.add(btn);

        fr.add(lbl, BorderLayout.NORTH);
        fr.add(bar, BorderLayout.CENTER);
        fr.add(pan, BorderLayout.SOUTH);


        BarThread stepper = new BarThread(bar, lbl);
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("��ʼ".equals(btn.getText())) {
                    stepper.setContent(ta.getText());
                    stepper.start();
                    btn.setText("ȡ��");
                    return;
                }
                if ("ȡ��".equals(btn.getText())) {
                    stepper.stop(true);
                    fr.dispose();
                }
            }
        };

        btn.addActionListener(listener);

        setPopWindowSize(fr);
        fr.setVisible(true);
        fr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //ͨѶ¼
    //ͨѶ¼ά��
    private void displayData() throws Exception {
        JFrame fr = new JFrame("ͨѶ¼");

        Object[] colName = {"���", "����", "�Ա�", "�ƶ��绰����", "Email", "QQ��"};
        //��ȡ����
        Object[][] rowData;
        File file = new File(dataFilePath + dataFileName);
        if (file.exists()) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            mana = (ContactManager) (in.readObject());
        } else {
            mana = new ContactManager();
        }
        List<Contact> all = mana.getAllContacts();
        rowData = new Object[all.size()][6];
        for (int i = 0; i < all.size(); ++i) {
            Contact c = all.get(i);
            rowData[i][0] = c.getnId();
            rowData[i][1] = c.getsName();
            rowData[i][2] = c.getByteSex() == 1 ? "��" : "Ů";
            rowData[i][3] = c.getsCellPhone();
            rowData[i][4] = c.getsEmail();
            rowData[i][5] = c.getsInstantMessager();
        }

        JTable table = new JTable(rowData, colName);
        table.setRowSelectionAllowed(true);
        JScrollPane tablePan = new JScrollPane(table);

        JButton addBtn = new JButton("���");
        JButton delBtn = new JButton("ɾ��");
        JButton chaBtn = new JButton("�޸�");
        JButton queBtn = new JButton("��ѯ");
        JButton extBtn = new JButton("�ر�");
        JPanel btnPan = new JPanel();

        btnPan.add(addBtn);
        btnPan.add(delBtn);
        btnPan.add(chaBtn);
        btnPan.add(queBtn);
        btnPan.add(extBtn);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addBtn) {
                    addContact(table);
                }
                if (e.getSource() == delBtn) {
                    int row = table.getSelectedRow();
                    mana.removeContact((Integer) table.getValueAt(row, 0));
                    flushTable(table);
                }
                if (e.getSource() == chaBtn) {
                    changeContact(table.getSelectedRow(), table);
                }
                if (e.getSource() == queBtn) {
                    queryContact(table);
                }
                if (e.getSource() == extBtn) {
                    try {
                        saveData();
                    } catch (Exception ex) {
                        System.out.println("saveData error");
                        throw new RuntimeException(ex);
                    }
                    fr.dispose();
                }
            }
        };

        fr.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    saveData();
                } catch (Exception ex) {
                    System.out.println("windowClosing error");
                    throw new RuntimeException(ex);
                }
                fr.dispose();
            }
        });

       addBtn.addActionListener(listener);
       delBtn.addActionListener(listener);
       chaBtn.addActionListener(listener);
       queBtn.addActionListener(listener);
       extBtn.addActionListener(listener);

        fr.add(tablePan);
        fr.add(btnPan, BorderLayout.SOUTH);
        setPopWindowSize(fr);
        fr.setVisible(true);
        fr.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void addContact(JTable table) {
        JFrame fr = new JFrame("�����ϵ��");

        JLabel lbl1 = new JLabel("����");
        JTextField nameField = new JTextField(10);
        JPanel pan1 = new JPanel();
        pan1.add(lbl1);
        pan1.add(nameField);

        JLabel lbl2 = new JLabel("�Ա�");
        JComboBox<String> sexCom = new JComboBox<>();
        sexCom.addItem("��");
        sexCom.addItem("Ů");
        sexCom.setSelectedItem("��");
        JPanel pan2 = new JPanel();
        pan2.add(lbl2);
        pan2.add(sexCom);

        JLabel lbl3 = new JLabel("�ƶ��绰����");
        JTextField phoneFiled = new JTextField(10);
        JPanel pan3 = new JPanel();
        pan3.add(lbl3);
        pan3.add(phoneFiled);

        JLabel lbl4 = new JLabel("Email");
        JTextField emailField = new JTextField(10);
        JPanel pan4 = new JPanel();
        pan4.add(lbl4);
        pan4.add(emailField);

        JLabel lbl5 = new JLabel("qq");
        JTextField qqField = new JTextField(10);
        JPanel pan5 = new JPanel();
        pan5.add(lbl5);
        pan5.add(qqField);

        JButton yes = new JButton("ȷ��");
        JButton no = new JButton("ȡ��");
        JPanel pan6 = new JPanel();
        pan6.add(yes);
        pan6.add(no);

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String sex = (String) (sexCom.getSelectedItem());
                String phone = phoneFiled.getText();
                String email = emailField.getText();
                String qq = qqField.getText();
                if (name.isEmpty() || phone.isEmpty() || email.isEmpty()|| qq.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "��Ϣ��������");
                    return;
                }
                byte bytesex = (byte) ("��".equals(sex) ? 1 : 0);
                mana.addContact(new Contact(name, bytesex, phone, email, qq));
                fr.dispose();
                flushTable(table);
            }
        });
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.dispose();
            }
        });

        JPanel mainPan = new JPanel();
        mainPan.setLayout(new BoxLayout(mainPan, BoxLayout.Y_AXIS));
        mainPan.add(pan1);
        mainPan.add(pan2);
        mainPan.add(pan3);
        mainPan.add(pan4);
        mainPan.add(pan5);
        mainPan.add(pan6);
        fr.add(mainPan);

        setPopWindowSize(fr);
        fr.setVisible(true);
        fr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void changeContact(int oldid, JTable table) {
        JFrame fr = new JFrame("�޸���ϵ��");

        JLabel lbl1 = new JLabel("����");
        JTextField nameField = new JTextField(10);
        JPanel pan1 = new JPanel();
        pan1.add(lbl1);
        pan1.add(nameField);

        JLabel lbl2 = new JLabel("�Ա�");
        JComboBox<String> sexCom = new JComboBox<>();
        sexCom.addItem("��");
        sexCom.addItem("Ů");
        sexCom.setSelectedItem("��");
        JPanel pan2 = new JPanel();
        pan2.add(lbl2);
        pan2.add(sexCom);

        JLabel lbl3 = new JLabel("�ƶ��绰����");
        JTextField phoneFiled = new JTextField(10);
        JPanel pan3 = new JPanel();
        pan3.add(lbl3);
        pan3.add(phoneFiled);

        JLabel lbl4 = new JLabel("Email");
        JTextField emailField = new JTextField(10);
        JPanel pan4 = new JPanel();
        pan4.add(lbl4);
        pan4.add(emailField);

        JLabel lbl5 = new JLabel("qq");
        JTextField qqField = new JTextField(10);
        JPanel pan5 = new JPanel();
        pan5.add(lbl5);
        pan5.add(qqField);

        JButton yes = new JButton("�޸�");
        JButton no = new JButton("ȡ��");
        JPanel pan6 = new JPanel();
        pan6.add(yes);
        pan6.add(no);

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String sex = (String) (sexCom.getSelectedItem());
                String phone = phoneFiled.getText();
                String email = emailField.getText();
                String qq = qqField.getText();
                if (name.isEmpty() || phone.isEmpty() || email.isEmpty()|| qq.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "��Ϣ��������");
                    return;
                }
                byte bytesex = (byte) ("��".equals(sex) ? 1 : 0);
                mana.updateContact(oldid, new Contact(name, bytesex, phone, email, qq));
                fr.dispose();
                flushTable(table);
            }
        });
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.dispose();
            }
        });

        JPanel mainPan = new JPanel();
        mainPan.setLayout(new BoxLayout(mainPan, BoxLayout.Y_AXIS));
        mainPan.add(pan1);
        mainPan.add(pan2);
        mainPan.add(pan3);
        mainPan.add(pan4);
        mainPan.add(pan5);
        mainPan.add(pan6);
        fr.add(mainPan);

        setPopWindowSize(fr);
        fr.setVisible(true);
        fr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void queryContact(JTable table) {
        JFrame fr = new JFrame("��ѯ��ϵ��");

        JLabel lbl = new JLabel("����ؼ��֣�");
        JTextField txtField = new JTextField(10);
        JButton queBtn = new JButton("��ѯ");


        queBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame fr = new JFrame("��ѯ���");
                Object[] colName = {"���", "����", "�Ա�", "�ƶ��绰����", "Email", "QQ��"};

                List<Contact> all = mana.searchContacts(txtField.getText());
                Object[][] rowData = new Object[all.size()][6];
                for (int i = 0; i < all.size(); ++i) {
                    Contact c = all.get(i);
                    rowData[i][0] = c.getnId();
                    rowData[i][1] = c.getsName();
                    rowData[i][2] = c.getByteSex();
                    rowData[i][3] = c.getsCellPhone();
                    rowData[i][4] = c.getsEmail();
                    rowData[i][5] = c.getsInstantMessager();
                }
                JTable table = new JTable(rowData, colName);
                fr.add(table);

                setPopWindowSize(fr);
                fr.setVisible(true);
                fr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        });



        fr.add(lbl, BorderLayout.NORTH);
        fr.add(txtField, BorderLayout.CENTER);
        fr.add(queBtn, BorderLayout.SOUTH);

        setPopWindowSize(fr);
        fr.setVisible(true);
        fr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void flushTable(JTable table) {
        Object[] colName = {"���", "����", "�Ա�", "�ƶ��绰����", "Email", "QQ��"};

        List<Contact> all = mana.getAllContacts();
        Object[][] rowData = new Object[all.size()][6];
        for (int i = 0; i < all.size(); ++i) {
            Contact c = all.get(i);
            rowData[i][0] = c.getnId();
            rowData[i][1] = c.getsName();
            rowData[i][2] = c.getByteSex() == 1 ? "��" : "Ů";
            rowData[i][3] = c.getsCellPhone();
            rowData[i][4] = c.getsEmail();
            rowData[i][5] = c.getsInstantMessager();
        }

        table.setModel(new DefaultTableModel(rowData, colName));
    }

    private void saveData() throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataFilePath + dataFileName));
        out.writeObject(mana);
        out.flush();
        out.close();
    }

    //ͨѶ¼�����ļ�����
    private void setDataPath() {
        JFileChooser jf = new JFileChooser(filePath);
        jf.setSelectedFile(new File(dataFilePath, dataFileName));
        jf.showSaveDialog(null);
        dataFilePath = jf.getSelectedFile().getParent().replace("\\", "\\\\");
        dataFileName = jf.getSelectedFile().getName();
    }

    //****************************************************************************************************************//
    //ͨ�÷���
    //****************************************************************************************************************//
    private void exit() {
        //�������� �����С ������ɫ ������ɫ
        saveFont();
        //���������ļ�·��
        saveDataPath();
        //����������˳�
        myFontDly.dispose();
        dispose();
    }

    private void saveFont() {
        File fontConfig = new File("font.properties");
        String s = "";
        if (!fontConfig.exists()) {
            try {
                fontConfig.createNewFile();
            } catch (IOException e) {
                System.out.println("saveFont error");
                throw new RuntimeException(e);
            }
        }
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("font.properties"), "gbk"));
            s = String.format("font=%s\nfontSize=%d\nfontColor=%s\nbgColor=%s", font, fontSize, fontColor, bgColor);

            writer.write(s);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("saveFont error");
            throw new RuntimeException(e);
        }
    }

    private void saveDataPath() {
        File fontConfig = new File("datapath.properties");
        String s = "";
        if (!fontConfig.exists()) {
            try {
                fontConfig.createNewFile();
            } catch (IOException e) {
                System.out.println("saveFont error");
                throw new RuntimeException(e);
            }
        }
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("datapath.properties"), "gbk"));
            s = String.format("dataFilePath=%s\ndataFileName=%s", dataFilePath.replace("\\", "\\\\"), dataFileName);

            writer.write(s);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("saveDataPath error");
            throw new RuntimeException(e);
        }
    }

    //****************************************************************************************************************//
    //�������
    //****************************************************************************************************************//
    public static void main(String[] args) {
        J202203150619_4 fr = new J202203150619_4(title);

        //�����ϽǵĲΪ�Լ����˳�����
        fr.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        fr.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fr.checkQuit();
            }
        });
        centerWindow(fr);
        fr.setVisible(true);
    }

    //****************************************************************************************************************//
    //��������
    //****************************************************************************************************************//
    public static void centerWindow(Window f) {
        //��������С
        Toolkit tk = f.getToolkit();
        Dimension dm = tk.getScreenSize();
        //�ô��ھ�����ʾ
        f.setLocation((int) (dm.getWidth() - f.getWidth()) / 2, (int) (dm.getHeight() - f.getHeight()) / 2);
    }

    public static void setPopWindowSize(Window f) {
        //��������С
        Toolkit tk = f.getToolkit();
        Dimension dm = tk.getScreenSize();

        f.setSize((int) (dm.getWidth() * 0.25), (int) (dm.getHeight() * 0.25));
        centerWindow(f);
    }

    public static Color stringToColor(String s) {
        s = s.toLowerCase();
        Color ret = null;
        switch (s) {
            case "white":
                ret = Color.white;
                break;
            case "black":
                ret = Color.black;
                break;
            case "gray":
                ret = Color.gray;
                break;
            case "red":
                ret = Color.red;
                break;
            case "pink":
                ret = Color.pink;
                break;
            case "orange":
                ret = Color.orange;
                break;
            case "yellow":
                ret = Color.yellow;
                break;
            case "green":
                ret = Color.green;
                break;
            case "cyan":
                ret = Color.cyan;
                break;
            case "blue":
                ret = Color.blue;
                break;
            default:
                ret = Color.black;
        }
        return ret;
    }

    public boolean isNumber(String s) {
        for (byte c : s.getBytes())
            if (c < '0' || c > '9')
                return false;
        return true;
    }

    public static boolean isEnglish(String s, String[] x, String[] y, String[] z) {
        String[] split = s.split(" ");
        int len = split.length;
        if (len == 1) {
            return inArray(s, x) || inArray(s, y);
        } else if (len == 2) {
            String fst = split[0], sec = split[1];
            if ("zero".equals(sec)) { // ��ֹ thirty zero �����İ���ͨ��
                return false;
            }
            return inArray(fst, z) && inArray(sec, x);
        }
        return false;
    }

    private static boolean inArray(String s, String[] arr) {
        for (String num : arr) {
            if (num.equals(s)) {
                return true;
            }
        }
        return false;
    }

    //****************************************************************************************************************//
    //�Զ����ڲ���
    //****************************************************************************************************************//

    //�����Ƿ����캯���İ�ť
    static class MyMenu extends JMenu {
        private MyMenu(String label) {
            super(label);
        }

        private MyMenu(String label, int mnemonic) {
            super(label);
            setMnemonic(mnemonic);
        }
    }

    //ѡ����Ľ����
    class MyFontDly extends JFrame {
        //������ť
        JButton btnOk = new JButton("ȷ��");
        JButton btnCancel = new JButton("ȡ��");
        JPanel panButtons = new JPanel();

        //��������
        String _font;
        JLabel lblFont = new JLabel("�������ƣ�", JLabel.LEFT);
        JComboBox<String> cbFontFamily = new JComboBox<>();
        JPanel panFont = new JPanel();

        //�����С
        int _fontSize;
        JLabel lblFontSize = new JLabel("�����С��", JLabel.LEFT);
        JComboBox<String> cbFontSize = new JComboBox<>();
        JPanel panFontSize = new JPanel();

        //������ɫ
        String _fontColor;
        JLabel lblFontColor = new JLabel("������ɫ��", JLabel.LEFT);
        JComboBox<String> cbFontColor = new JComboBox<>();
        JPanel panFontColor = new JPanel();
        final String[] fontColors = {"white", "black", "gray", "red", "pink", "orange", "yellow", "cyan", "blue"};

        //����Ԥ��
        JLabel lblFontPreview = new JLabel("����Ԥ����", JLabel.LEFT);
        JLabel lblTextPreview = new JLabel("java ����Һô�Һ�", JLabel.CENTER);
        JPanel panFontPreView = new JPanel();

        public void showFontDly() {
            setVisible(true);
        }

        public void hideFontDly() {
            setVisible(false);
        }

        private MyFontDly(String title) {
            super(title);

            GridLayout gl = new GridLayout(5, 1, 5, 5);
            setLayout(gl);

            //��ʼ�������
            InitFonts();

            //��������
            _font = font;
            panFont.setLayout(new BorderLayout());
            panFont.add(lblFont, BorderLayout.WEST);
            panFont.add(cbFontFamily, BorderLayout.CENTER);

            cbFontFamily.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    fontNameItemStateChanged(e);
                }
            });
            add(panFont);

            //�����С
            _fontSize = fontSize;
            panFontSize.setLayout(new BorderLayout());
            panFontSize.add(lblFontSize, BorderLayout.WEST);
            panFontSize.add(cbFontSize, BorderLayout.CENTER);

            cbFontSize.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    fontSizeItemStateChanged(e);
                }
            });
            add(panFontSize);

            //������ɫ
            _fontColor = fontColor;
            panFontColor.setLayout(new BorderLayout());
            panFontColor.add(lblFontColor, BorderLayout.WEST);
            panFontColor.add(cbFontColor, BorderLayout.CENTER);

            cbFontColor.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    fontColorItemStateChanged(e);
                }
            });
            add(panFontColor);

            //����Ԥ��
            panFontPreView.setLayout(new BorderLayout());
            panFontPreView.add(lblFontPreview, BorderLayout.WEST);
            panFontPreView.add(lblTextPreview, BorderLayout.CENTER);
            lblTextPreview.setBorder(BorderFactory.createEtchedBorder());
            lblTextPreview.setOpaque(true);
            add(panFontPreView);

            //��ť����
            panButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
            panButtons.add(btnOk);
            panButtons.add(btnCancel);
            add(panButtons);

            //��ʼ��������
            cbFontFamily.setSelectedItem(_font);
            cbFontSize.setSelectedItem(Integer.toString(_fontSize));
            cbFontColor.setSelectedItem(_fontColor);

            //ע���¼�
            btnOk.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnActionPerformed(e);
                }
            });
            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnActionPerformed(e);
                }
            });
            //��ʼ������Ԥ��
            lblTextPreview.setFont(new Font(_font, Font.PLAIN, _fontSize));
            lblTextPreview.setForeground(stringToColor(_fontColor));
        }

        private void btnActionPerformed(ActionEvent e) {
            if (e.getSource() == btnOk) {
                font = _font;
                fontSize = _fontSize;
                fontColor = _fontColor;
                ta.setFont(new Font(font, Font.PLAIN, fontSize));
                ta.setForeground(stringToColor(fontColor));
            }
            if (e.getSource() == btnCancel) {
                _font = font;
                _fontSize = fontSize;
                _fontColor = fontColor;
            }

            //���ش���
            hideFontDly();
        }

        public void fontNameItemStateChanged(ItemEvent e) {
            _font = cbFontFamily.getSelectedItem().toString();
            lblTextPreview.setFont(new Font(_font, Font.PLAIN, _fontSize));
            lblTextPreview.setForeground(stringToColor(_fontColor));
        }

        public void fontSizeItemStateChanged(ItemEvent e) {
            _fontSize = Integer.parseInt(cbFontSize.getSelectedItem().toString());
            lblTextPreview.setFont(new Font(_font, Font.PLAIN, _fontSize));
            lblTextPreview.setForeground(stringToColor(_fontColor));
        }

        public void fontColorItemStateChanged(ItemEvent e) {
            _fontColor = cbFontColor.getSelectedItem().toString();
            lblTextPreview.setFont(new Font(_font, Font.PLAIN, _fontSize));
            lblTextPreview.setForeground(stringToColor(_fontColor));
        }

        public void InitFonts() {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontList = ge.getAvailableFontFamilyNames();
            for (int i = fontList.length - 1; i >= 0; --i)
                cbFontFamily.addItem(fontList[i]);

            for (int i = 9; i <= 72; ++i)
                cbFontSize.addItem(Integer.toString(i));

            for (int i = 0; i < fontColors.length; ++i)
                cbFontColor.addItem(fontColors[i]);
        }
    }

    //���̵߳Ľ�����
    class BarThread extends Thread {
        String content;
        JLabel lbl;
        JProgressBar progressBar;
        boolean m_bStopped;

        //���췽��
        public BarThread(JProgressBar bar, JLabel lbl) {
            progressBar = bar;
            this.lbl = lbl;
            m_bStopped = false;
        }

        //�߳���
        public void run() {
            String[] split = content.split("\\r\\n|\\n|\\r");
            long size = split.length;
            double sum = 0;

            long startTime = System.currentTimeMillis();
            for (int i = 0; i < size; ++i) {
                if (m_bStopped) break;

                String s = split[i];
                int idx = s.indexOf('=');

                sum += Double.parseDouble(s.substring(idx + 1));

                double nowPercent = 1.0 * i / size;
                long passTime = System.currentTimeMillis() - startTime;
                long needTime = passTime * size / (i + 1) - passTime;

                lbl.setText(String.format("��ǰ��Ϊ%f,���ڼ������%s(%d/%d),��Լ��ʣ��%d��", sum, s.substring(0, idx), i + 1, size, needTime / 1000));
                progressBar.setValue((int) Math.ceil(nowPercent * 100));
            }
        }

        public void setContent(String content) {
            this.content = content;
        }

        //����ֹͣ
        public void stop(boolean bStopped) {
            m_bStopped = bStopped;
        }
    }
}

//��ϵ�˹����� �������
class ContactManager implements Serializable{
    static final long serialVersionUID = 1L;

    private List<Contact> contacts;
    private int idCnt;

    public ContactManager() {
        contacts = new ArrayList<>();
        idCnt = 0;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        idCnt = contacts.size();
        contact.setnId(idCnt);
    }

    public void removeContact(int id) {
        for (Contact c : contacts) {
            if (c.getnId() == id) {
                contacts.remove(c);
                return;
            }
        }
    }
    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public void updateContact(int oldid, Contact _new) {
        Contact _old = contacts.get(oldid);
        _old.setsName(_new.getsName());
        _old.setByteSex(_new.getByteSex());
        _old.setsCellPhone(_new.getsCellPhone());
        _old.setsEmail(_new.getsEmail());
        _old.setsInstantMessager(_new.getsInstantMessager());
    }

    public void updateContact(Contact _old, Contact _new) {
        contacts.remove(_old);
        contacts.add(_new);
    }

    public List<Contact> searchContacts(String keyword) {
        List<Contact> results = new ArrayList<>();
        for (Contact contact : contacts) {
            // ������Ҫ����ģ����ѯ
            // �ж�������������λ����ע�ֶ��Ƿ���� keyword
            if (contact.getsName().contains(keyword) ||
                    contact.getsCellPhone().contains(keyword) ||
                    contact.getsEmail().contains(keyword) ||
                    (String.valueOf(contact.getnId())).contains(keyword)) {
                results.add(contact);
            }
        }
        return results;
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }
}

//��ϵ����
class Contact implements Serializable {
    static final long serialVersionUID = 1L;
    int nId;
    String sName; //�ǿ�
    byte byteSex; //1��ʾ�У�0��ʾŮ���ǿ�
    String sAddress;
    String sCompany;
    String sPostalCode;
    String sHomeTele;
    String sOfficeTele;
    String sFax;
    String sCellPhone;
    String sEmail;
    String sInstantMessager;
    Date dateBirthday;
    String sMemo;

    public Contact() {
    }

    public Contact(String sName, byte byteSex, String sCellPhone, String sEmail, String sInstantMessager) {
        this.sName = sName;
        this.byteSex = byteSex;
        this.sCellPhone = sCellPhone;
        this.sEmail = sEmail;
        this.sInstantMessager = sInstantMessager;
    }

    public Contact(String sName, byte byteSex, String sAddress, String sCompany, String sPostalCode, String sHomeTele, String sOfficeTele, String sFax, String sCellPhone, String sEmail, String sInstantMessager, Date dateBirthday, String sMemo) {
        this.sName = sName;
        this.byteSex = byteSex;
        this.sAddress = sAddress;
        this.sCompany = sCompany;
        this.sPostalCode = sPostalCode;
        this.sHomeTele = sHomeTele;
        this.sOfficeTele = sOfficeTele;
        this.sFax = sFax;
        this.sCellPhone = sCellPhone;
        this.sEmail = sEmail;
        this.sInstantMessager = sInstantMessager;
        this.dateBirthday = dateBirthday;
        this.sMemo = sMemo;
    }

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public byte getByteSex() {
        return byteSex;
    }

    public void setByteSex(byte byteSex) {
        this.byteSex = byteSex;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getsCompany() {
        return sCompany;
    }

    public void setsCompany(String sCompany) {
        this.sCompany = sCompany;
    }

    public String getsPostalCode() {
        return sPostalCode;
    }

    public void setsPostalCode(String sPostalCode) {
        this.sPostalCode = sPostalCode;
    }

    public String getsHomeTele() {
        return sHomeTele;
    }

    public void setsHomeTele(String sHomeTele) {
        this.sHomeTele = sHomeTele;
    }

    public String getsOfficeTele() {
        return sOfficeTele;
    }

    public void setsOfficeTele(String sOfficeTele) {
        this.sOfficeTele = sOfficeTele;
    }

    public String getsFax() {
        return sFax;
    }

    public void setsFax(String sFax) {
        this.sFax = sFax;
    }

    public String getsCellPhone() {
        return sCellPhone;
    }

    public void setsCellPhone(String sCellPhone) {
        this.sCellPhone = sCellPhone;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsInstantMessager() {
        return sInstantMessager;
    }

    public void setsInstantMessager(String sInstantMessager) {
        this.sInstantMessager = sInstantMessager;
    }

    public Date getDateBirthday() {
        return dateBirthday;
    }

    public void setDateBirthday(Date dateBirthday) {
        this.dateBirthday = dateBirthday;
    }

    public String getsMemo() {
        return sMemo;
    }

    public void setsMemo(String sMemo) {
        this.sMemo = sMemo;
    }
}
