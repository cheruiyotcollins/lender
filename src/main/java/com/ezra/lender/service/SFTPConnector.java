//package com.ezra.lender.service;
//
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.Session;
//
//
//public class SFTPConnector {
//    String host = "sftp.example.com";
//    String user = "username";
//    String password = "password";
//    int port = 1111;
//
//
//        JSch jsch = new JSch();
//        Session session = null;
//        try {
//            session = jsch.getSession(user, host, port);
//        } catch (JSchException ex) {
//            throw new RuntimeException(ex);
//        }
//        session.setPassword(password);
//        session.setConfig("StrictHostKeyChecking", "no");
//        try {
//            session.connect();
//        } catch (JSchException ex) {
//            throw new RuntimeException(ex);
//        }
//
//        // Perform SFTP operations here
//
//        session.disconnect();
//
//}
