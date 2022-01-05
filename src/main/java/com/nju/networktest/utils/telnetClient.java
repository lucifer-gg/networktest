package com.nju.networktest.utils;

import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Component;

import java.io.*;

public class telnetClient {
    private String prompt = "#";        //结束标识字符串,Windows中是>,Linux中是#
    private char promptChar = '>';        //结束标识字符
    private TelnetClient telnet;
    private InputStream in;                // 输入流,接收返回信息
    private PrintStream out;        // 向服务器写入 命令

    /**
     * @param termtype 协议类型：VT100、VT52、VT220、VTNT、ANSI
     * @param prompt   结果结束标识
     */
    public telnetClient(String termtype, String prompt) {
        telnet = new TelnetClient(termtype);
        setPrompt(prompt);
    }

    public telnetClient(String termtype) {
        telnet = new TelnetClient(termtype);
    }

    public telnetClient() {
        telnet = new TelnetClient("VT220");
    }

    /**
     * 登录到目标主机
     *
     * @param ip
     * @param port
     * @param password
     */
    public Boolean login(String ip, int port, String password) {
        try {
//            telnet.setSoTimeout(Integer.MAX_VALUE);
//            telnet.setConnectTimeout(Integer.MAX_VALUE);
//            telnet.setDefaultTimeout(Integer.MAX_VALUE);
            telnet.connect(ip, port);
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
            readUntil("Password:");
            write(password);
            readUntil(">");
            write("enable");
            readUntil("Password:");
            write(password);
            if (readUntil("#") != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取分析结果
     *
     * @param pattern 匹配到该字符串时返回结果
     * @return
     */
    public String readUntil(String pattern) {
        StringBuffer sb = new StringBuffer();
        try {
            char lastChar = (char) -1;
            boolean flag = pattern != null && pattern.length() > 0;
            if (flag)
                lastChar = pattern.charAt(pattern.length() - 1);
            char ch;
            int code = -1;

            while ((code = in.read()) != -1) {
                ch = (char) code;
                sb.append(ch);
                //匹配到结束标识时返回结果
                if (flag) {
                    if (ch == lastChar && sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                } else {
                    //如果没指定结束标识,匹配到默认结束标识字符时返回结果
                    if (ch == promptChar)
                        return sb.toString();
                }


                if(sb.toString().contains("--More--")){
                    return sb.toString();
                }
                //登录失败时返回结果
                if (sb.toString().contains("Login Failed")) {
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 发送命令
     *
     * @param value
     */
    public void write(String value) {
        try {
            out.println(value);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送命令,返回执行结果
     *
     * @param command
     * @return
     */
    public String sendCommand(String command) {
        try {
            write(command);
            return readUntil(prompt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
//        System.out.println(command);
//        return command;
    }

    public String sendCommand(String command,String promptInput) {
        try {
            write(command);
            return readUntil(promptInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
//        System.out.println(command);
//        return command;
    }

    public boolean isConnected(){
        return telnet.isAvailable();
    }

    public void distinct(){
        try {
            if(telnet!=null&&!telnet.isConnected())
                telnet.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPrompt(String prompt) {
        if(prompt!=null){
            this.prompt = prompt;
            this.promptChar = prompt.charAt(prompt.length()-1);
        }
    }

    public static void main(String[] args) {
        telnetClient telnet = new telnetClient("VT220","#");        //Windows,用VT220,否则会乱码
        if(telnet.login("192.168.80.1", 23, "cisco")){
            System.out.println("login");

            String rs = telnet.sendCommand("copy running-config startup-config","?");
            System.out.println(rs);
            String s = telnet.sendCommand("\n");
            System.out.println(s);



            try {
                rs = new String(rs.getBytes("ISO-8859-1"),"GBK");        //转一下编码
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //System.out.println(rs);
        }
    }
}