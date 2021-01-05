package com.gxinyu.sliverpermission;

import android.Manifest;

/**
 * 目前所有需要申请的动态权限
 * 一共是9组
 * 如果是一组权限，可以直接申请组权限
 */
public class Permission {

    //单个权限的
    public static String CAMERA = Manifest.permission.CAMERA;//照相机（一般需要配合读写权限）
    public static String SENSORS = Manifest.permission.BODY_SENSORS;//传感器
    public static String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;//录音

    //读写
    public static String GROUP_STORAGE[] = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    //位置
    public static String GROUP_LOCATION[] = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    //电话
    public static String GROUP_PHONE[] = {
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.ADD_VOICEMAIL};
    //联系人
    public static String GROUP_CONTACTS[] = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS};
    //短信
    public static String GROUP_SMS[] = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS};
    //日历
    public static String GROUP_CALENDAR[] = {
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR};

    //所有权限集合
    public static String ALL_PERSSIONS[] = {
            Manifest.permission.CAMERA,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.RECORD_AUDIO,
            //读写权限组
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            //位置权限组
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            //日历权限组
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,
            //联系人权限组
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS,
            //短信权限组
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
            //电话权限组
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.ADD_VOICEMAIL
    };
}
