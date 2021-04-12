package com.gxinyu.sliverpermission

import android.Manifest

const val FRAGMENT_TAG = "com.gxinyu.sliverpermission"
const val REQUEST_CODE_PERMISSION= 0x01 //权限请求码
const val REQUEST_CODE_SYSTEM = 0x02 //打开系统权限设置

//单个权限的
const val CAMERA = Manifest.permission.CAMERA //照相机（一般需要配合读写权限）
const val SENSORS = Manifest.permission.BODY_SENSORS //传感器
const val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO //录音

//读写
@kotlin.jvm.JvmField
var GROUP_STORAGE = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE)

//位置
@kotlin.jvm.JvmField
 var GROUP_LOCATION = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION)

//电话
@kotlin.jvm.JvmField
var GROUP_PHONE = arrayOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.WRITE_CALL_LOG,
        Manifest.permission.USE_SIP,
        Manifest.permission.PROCESS_OUTGOING_CALLS,
        Manifest.permission.ADD_VOICEMAIL)

//联系人
@kotlin.jvm.JvmField
var GROUP_CONTACTS = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.GET_ACCOUNTS)

//短信
@kotlin.jvm.JvmField
var GROUP_SMS = arrayOf(
        Manifest.permission.SEND_SMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_WAP_PUSH,
        Manifest.permission.RECEIVE_MMS)

//日历
@kotlin.jvm.JvmField
var GROUP_CALENDAR = arrayOf(
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR)

//所有权限集合
@kotlin.jvm.JvmField
var ALL_PERSSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.BODY_SENSORS,
        Manifest.permission.RECORD_AUDIO,  //读写权限组
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,  //位置权限组
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,  //日历权限组
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR,  //联系人权限组
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.GET_ACCOUNTS,  //短信权限组
        Manifest.permission.SEND_SMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_WAP_PUSH,
        Manifest.permission.RECEIVE_MMS,  //电话权限组
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.WRITE_CALL_LOG,
        Manifest.permission.USE_SIP,
        Manifest.permission.PROCESS_OUTGOING_CALLS,
        Manifest.permission.ADD_VOICEMAIL
)

